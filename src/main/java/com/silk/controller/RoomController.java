package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.Room;
import com.silk.entity.User;
import com.silk.service.RoomService;
import com.silk.service.UserService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @PostMapping("create")
    public Result create(@RequestBody Room room){
        int flag = roomService.create(room);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = roomService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody Room room){
        int flag = roomService.update(room);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public Room detail(Integer id){
        return roomService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  Room room){
        PageInfo<Room> pageInfo = roomService.query(room);
        return Result.ok(pageInfo);
    }

    // 住在当前房间的人数
    @GetMapping("query_liver_amount")
    public Result queryLiverAmount(Integer id){
        int liverAmount = userService.queryLiverAmount(id);
        return Result.ok(liverAmount);
    }


    // 为房间增加一个床位
    @GetMapping("capacity_plus_one")
    public Result capacityPlusOne(Integer id){
        int flag = roomService.capacityPlusOne(id);
        if (flag>0){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    // 为房间减少一个床位
    @GetMapping("capacity_minus_one")
    public Result capacityMinusOne(Integer id){
        int flag = roomService.capacityMinusOne(id);
        if (flag>0){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    // 当前楼栋入住率
    @GetMapping("occupancy_rate")
    public Result occupancyRate(Integer buildingId){
        int buildingTotalStudentBedAmount = roomService.buildingTotalStudentBedAmount(buildingId);
        int buildingActualStudentAmount = userService.buildingActualStudentAmount(buildingId);

        Double occupancyRate = (double) (buildingActualStudentAmount / buildingTotalStudentBedAmount) * 100;

        BigDecimal bd1 = new BigDecimal(occupancyRate);
        occupancyRate = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println(buildingId + "栋入住率: " + occupancyRate + "%");

        return Result.ok(occupancyRate);

    }



    @GetMapping("/query_room_balance")
    public Result paidInfo(HttpServletRequest request){
        User param = (User)request.getAttribute("user");                // 获取发起当前操作的学生信息
        User user1 = userService.detail(param.getId());                     // 从数据库获取该学生的更多信息

        // 将数据处理后四舍五入保留两位小数
        Double myRoomBalance = roomService.queryRoomBalance(user1.getRoomId());
        BigDecimal bd1 = new BigDecimal(myRoomBalance);
        myRoomBalance = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 测试
        System.out.println("myRoomBalance");
        System.out.println("----------");
        System.out.println(myRoomBalance);

        return Result.ok(myRoomBalance);
    }

}
