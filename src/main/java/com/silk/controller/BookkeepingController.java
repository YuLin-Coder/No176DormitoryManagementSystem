package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.Bookkeeping;
import com.silk.entity.Notification;
import com.silk.entity.Room;
import com.silk.entity.User;
import com.silk.service.BookkeepingService;
import com.silk.service.RoomService;
import com.silk.service.UserService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
@RestController
@RequestMapping("/bookkeeping")
public class BookkeepingController {

    @Autowired
    private BookkeepingService bookkeepingService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;


    // 创建新的房间账目，并修改房间余额
    @PostMapping("create")
    public Result create(@RequestBody Bookkeeping bookkeeping, HttpServletRequest request){
        User param = (User)request.getAttribute("user");
        int userId = param.getId();     // 1. 获取该用户Id

        User user = userService.detail(userId);
        int roomId = user.getRoomId();          // 获取房间ID

        bookkeeping.setRoomId(roomId);          // 设置记账的房间ID
        bookkeeping.setUserId(userId);
        bookkeeping.setBkTime(new Date());


        double newBalance = roomService.queryRoomBalance(roomId);   //查询当前房间余额
        System.out.println("原始余额：" + newBalance);
        if(bookkeeping.getBkType()==0){
            newBalance = newBalance - bookkeeping.getBkMoney();     // 支出
        }else {
            newBalance = newBalance + bookkeeping.getBkMoney();     // 收入
        }
        System.out.println("当前余额：" + newBalance);
        int flagger = roomService.changeBalance(newBalance, roomId);    // 修改余额
        System.out.println("余额修改状态：（1成功，0失败）" + flagger);

        int flag = bookkeepingService.create(bookkeeping);              // 创建账目
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = bookkeepingService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody Bookkeeping bookkeeping){
        int flag = bookkeepingService.update(bookkeeping);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public Bookkeeping detail(Integer id){
        return bookkeepingService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  Bookkeeping bookkeeping){
        PageInfo<Bookkeeping> pageInfo = bookkeepingService.query(bookkeeping);
        return Result.ok(pageInfo);
    }



     // 查询学生所在房间的所有账目（包括支出、收入）
    @PostMapping("query_my_room")
    public Map<String, Object> queryMyRoom(@RequestBody Bookkeeping bookkeeping ,HttpServletRequest request){
        User param = (User)request.getAttribute("user");
        int userId = param.getId();     // 1. 获取该用户Id

        User user = userService.detail(userId);
        int roomId = user.getRoomId();          // 获取房间ID

        bookkeeping.setRoomId(roomId);          // 设置记账的房间ID
        PageInfo<Bookkeeping> pageInfo = bookkeepingService.queryMyRoom(bookkeeping);  // 通过用户ID查询该寝室所有支出

        pageInfo.getList().forEach(entity->{
            entity.setUser(userService.detail(entity.getUserId()));
        });

        System.out.println("My Room Bookkeeping Details:");
        System.out.println(pageInfo);
        return Result.ok(pageInfo);
    }



    // 查询学生所在房间的所有支出
    // 旭日图中使用该数据
    @PostMapping("query_my_room_expenditure")
    public Map<String, Object> queryMyRoomExpenditure(HttpServletRequest request){
        User param = (User)request.getAttribute("user");
        int userId = param.getId();     // 1. 获取该用户Id

        PageInfo<Bookkeeping> pageInfo = bookkeepingService.queryMyRoomExpenditure(userId);  // 通过用户ID查询该寝室所有支出

        pageInfo.getList().forEach(entity->{


            entity.setUser(userService.detail(entity.getUserId()));
        });

        System.out.println("My Room Bookkeeping Expenditure Details:");
        System.out.println(pageInfo);
        return Result.ok(pageInfo);
    }


    @PostMapping("query_my_room_each_classification_amount")
    public Result queryMyRoomEachClassificationAmount(HttpServletRequest request){
        User param = (User)request.getAttribute("user");
        int userId = param.getId();     // 1. 获取该用户Id

        List<Integer> list = new ArrayList<>();
        list = bookkeepingService.queryMyRoomEachClassificationAmount(userId);

        System.out.println("该寝室每类支出的数量分别是：");
        System.out.println(list);
        return Result.ok(list);
    }



    // 查询本房间的近几个月的消费数据
    // 堆叠图中使用该数据
    @GetMapping("/query_my_room_each_month_bk_info")
    public Result queryMyRoomEachMonthBkInfo(HttpServletRequest request){
        User param = (User)request.getAttribute("user");                // 获取发起当前操作的用户信息
        User domer = userService.detail(param.getId());                     // 从数据库获取该学生的更多信息
        int roomId = domer.getRoomId();                                 // 获取该学生所在房间的信息

        System.out.println(roomId);

        Date latestDate = bookkeepingService.queryMaxDate(roomId);      // 获取房间记账最新月份的数据
        System.out.println("最新日期：");
        System.out.println(latestDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted=sdf.format(latestDate);
        System.out.println("格式化字符串：");
        System.out.println(dateFormatted);


        int latestYear = Integer.parseInt(dateFormatted.substring(0, 4));
        int latestMonth = Integer.parseInt(dateFormatted.substring(5, 7));
        System.out.println("年：月：");
        System.out.println(latestYear);
        System.out.println(latestMonth);

        List<Double> watergyBkList = new ArrayList<>();                     // 水电
        List<Double> foodBkList = new ArrayList<>();                        // 餐饮
        List<Double> bookBkList = new ArrayList<>();                        // 图书
        List<Double> entertainmentBkList = new ArrayList<>();               // 游玩
        List<Double> movieBkList = new ArrayList<>();                       // 电影
        List<Double> oerBkList = new ArrayList<>();                         // 其它
        List<Integer> monthList = new ArrayList<>();                        // 月


        // 近三个月的支出数据
        for(int i=latestMonth; i>latestMonth-4; i--){
            double watergy = bookkeepingService.queryMonthlyExpenditureByClassification(roomId, latestYear, i, 0);
            watergyBkList.add(watergy);

            double food = bookkeepingService.queryMonthlyExpenditureByClassification(roomId, latestYear, i, 1);
            foodBkList.add(food);

            double book = bookkeepingService.queryMonthlyExpenditureByClassification(roomId, latestYear, i, 2);
            bookBkList.add(book);

            double entertainment = bookkeepingService.queryMonthlyExpenditureByClassification(roomId, latestYear, i, 3);
            entertainmentBkList.add(entertainment);

            double movie = bookkeepingService.queryMonthlyExpenditureByClassification(roomId, latestYear, i, 4);
            movieBkList.add(movie);

            double oer = bookkeepingService.queryMonthlyExpenditureByClassification(roomId, latestYear, i, 5);
            oerBkList.add(oer);

            monthList.add(i);
        }


        System.out.println("每月每类支出列表：");
        System.out.println(watergyBkList);
        System.out.println(foodBkList);
        System.out.println(bookBkList);
        System.out.println(entertainmentBkList);
        System.out.println(movieBkList);
        System.out.println(oerBkList);

        List<Object> finalList = new ArrayList<>();
        finalList.add(watergyBkList);
        finalList.add(foodBkList);
        finalList.add(bookBkList);
        finalList.add(entertainmentBkList);
        finalList.add(movieBkList);
        finalList.add(oerBkList);
        finalList.add(monthList);


        System.out.println("FinalBookkeepingList: ");
        System.out.println(finalList);
        return Result.ok(finalList);
    }
}
