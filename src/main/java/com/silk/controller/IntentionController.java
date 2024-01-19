package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.Intention;
import com.silk.entity.User;
import com.silk.service.IntentionService;
import com.silk.service.RoomService;
import com.silk.service.UserService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author LindaSilk
 * @date 2021年3月19日, 周五
 */
@RestController
@RequestMapping("/intention")
public class IntentionController {

    @Autowired
    private IntentionService intentionService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @PostMapping("create")
    public Result create(@RequestBody Intention intention){
        int flag = intentionService.create(intention);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = intentionService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody Intention intention){
        int flag = intentionService.update(intention);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public Intention detail(Integer id){
        return intentionService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  Intention intention){
        PageInfo<Intention> pageInfo = intentionService.query(intention);
        return Result.ok(pageInfo);
    }



    @PostMapping("upload")
    @ResponseBody
    public Result excelImport(@RequestParam(value = "file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        int result = 0;

        try {
            result = intentionService.addToDatabase(file);
        } catch (Exception e){
            e.printStackTrace();
        }

        List<Integer> maleAndFemaleAmountList = new ArrayList<>();

        maleAndFemaleAmountList.add(intentionService.queryFemaleAmount());
        maleAndFemaleAmountList.add(intentionService.queryMaleAmount());

        System.out.println("意向文件中男女生数量分别为：" + maleAndFemaleAmountList);

        if (result > 0){
            System.out.println("Upload result:");
            System.out.println(result);
            return Result.ok(maleAndFemaleAmountList);
        }else {
            return Result.fail("害！导入失败了...");
        }
    }


    @GetMapping("query_male_and_female_amount")
    public Result queryMaleAndFemaleAmount(){
        List<Integer> maleAndFemaleAmountList = new ArrayList<>();

        maleAndFemaleAmountList.add(intentionService.queryMaleAmount());
        maleAndFemaleAmountList.add(intentionService.queryFemaleAmount());

        System.out.println("意向文件中男女生数量分别为：" + maleAndFemaleAmountList);

        return Result.ok(maleAndFemaleAmountList);
    }


    @PostMapping("arrange")
    public Result arrange(@RequestBody  Intention intention){
        System.out.println("Intention: " + intention);
        System.out.println("MaleBuilding: " + intention.getMaleBuilding());
        System.out.println("FemaleBuilding: " + intention.getFemaleBuilding());

        int maleBuildingId = intention.getMaleBuilding();           // 在页面上选择的男生宿舍楼ID
        int femaleBuildingId = intention.getFemaleBuilding();       // 在页面上选择的女生宿舍楼ID

        List<Integer> maleRoomList = roomService.queryEachStuRoomId(maleBuildingId);            // 有空床位的男生房间ID列表
        List<Integer> maleEachRoomFreeBedList = new ArrayList<>();                              // 空床位数量
        List<Integer> femaleRoomList = roomService.queryEachStuRoomId(femaleBuildingId);        // 有空床位的女生房间ID列表
        List<Integer> femaleEachRoomFreeBedList = new ArrayList<>();                            // 空床位数量

        List<User> maleList = new ArrayList<>();
        List<User> femaleList = new ArrayList<>();


        // 遍历房间列表，将与房间对应的空床位数量添加至List中
        for (int roomId : maleRoomList) {
            int roomFreeBedAmount = roomService.queryEachStuRoomFreeBedAmount(roomId);
            maleEachRoomFreeBedList.add(roomFreeBedAmount);
        }
        for (int roomId : femaleRoomList) {
            int roomFreeBedAmount = roomService.queryEachStuRoomFreeBedAmount(roomId);
            femaleEachRoomFreeBedList.add(roomFreeBedAmount);
        }


        /**
         * femaleRoomList: 女生房间列表
         * femaleEachRoomFreeBedList: 女生房间空位数量列表
         */
        Intention femaleIntention = new Intention();
        femaleIntention.setGender(0);                                                       // 设置性别为女
        PageInfo<Intention> femalePageInfo = intentionService.query(femaleIntention);       // 查询所有女生意向

        int femaleCount = 0;
        for (int i = 0; i < femaleRoomList.size(); i++) {
            if (femaleEachRoomFreeBedList.get(i) != 0){
                List<Intention> list0 = null;
                if (femaleCount > femalePageInfo.getList().size() || femaleCount + femaleEachRoomFreeBedList.get(i) > femalePageInfo.getList().size()){
                    list0 = femalePageInfo.getList().subList(femaleCount, femalePageInfo.getList().size());
                    for (Intention entity: list0) {
                        int userId = entity.getId();                // 意向表ID->用户学号
                        String userPwd = "123456";                  // 设置默认密码
                        String userName = entity.getStuName();      // 意向表学生姓名->用户名
                        int gender = entity.getGender();            // 意向表学生性别->用户性别
                        String email = userId + "@silku.edu.cn";    // 学生ID->用户邮箱
                        int roomId = femaleRoomList.get(i);         // 房间号
                        int userType = 0;                           // 用户类型置0

                        User tempFemaleUser = new User(userId, userPwd, userName, gender, email, roomId, userType);
                        femaleList.add(tempFemaleUser);
                        System.out.println("--------");
                        System.out.println(femaleRoomList.get(i) + "寝室");
                        System.out.println("学生：" + list0);
                        System.out.println("加上一共：" + (femaleCount + list0.size()));
                    }
                    break;
                }else {
                    list0 = femalePageInfo.getList().subList(femaleCount, femaleEachRoomFreeBedList.get(i) + femaleCount);
                    for (Intention entity: list0) {
                        int userId = entity.getId();                // 意向表ID->用户学号
                        String userPwd = "123456";                  // 设置默认密码
                        String userName = entity.getStuName();      // 意向表学生姓名->用户名
                        int gender = entity.getGender();            // 意向表学生性别->用户性别
                        String email = userId + "@silku.edu.cn";    // 学生ID->用户邮箱
                        int roomId = femaleRoomList.get(i);         // 房间号
                        int userType = 0;                           // 用户类型置0

                        User tempFemaleUser = new User(userId, userPwd, userName, gender, email, roomId, userType);
                        femaleList.add(tempFemaleUser);
                    }
                }
                System.out.println("--------");
                System.out.println(femaleRoomList.get(i) + "寝室");
                System.out.println("学生：" + list0);
                femaleCount += femaleEachRoomFreeBedList.get(i);
                System.out.println("加上共：" + femaleCount);
            }
        }


        /**
         * maleRoomList: 男生房间列表
         * maleEachRoomFreeBedList: 男生房间空位数量列表
         */
        Intention maleIntention = new Intention();
        maleIntention.setGender(1);                                                 // 设置性别为男
        PageInfo<Intention> pageInfo = intentionService.query(maleIntention);       // 查询所有男生意向

        int count = 0;
        for (int i = 0; i < maleRoomList.size(); i++) {
            if (maleEachRoomFreeBedList.get(i) != 0){
                List<Intention> list = null;
                if (count > pageInfo.getList().size() || count + maleEachRoomFreeBedList.get(i) > pageInfo.getList().size()){
                    list = pageInfo.getList().subList(count, pageInfo.getList().size());
                    break;
                }else {
                    list = pageInfo.getList().subList(count,maleEachRoomFreeBedList.get(i) + count);
                    for (Intention entity:list) {
                        int userId = entity.getId();                // 意向表ID->用户学号
                        String userPwd = "123456";                  // 设置默认密码
                        String userName = entity.getStuName();      // 意向表学生姓名->用户名
                        int gender = entity.getGender();            // 意向表学生性别->用户性别
                        String email = userId + "@silku.edu.cn";    // 学生ID->用户邮箱
                        int roomId = maleRoomList.get(i);           // 房间号
                        int userType = 0;                           // 用户类型置0

                        User tempUser = new User(userId, userPwd, userName, gender, email, roomId, userType);
                        maleList.add(tempUser);
                    }
                }

                System.out.println("--------");
                System.out.println(maleRoomList.get(i) + "寝室");
                System.out.println("学生：" + list);
                count += maleEachRoomFreeBedList.get(i);
                System.out.println("加上共：" + count);
            }
        }

        System.out.println("maleList大小：" + maleList.size());
        System.out.println("femaleList大小：" + femaleList.size());
        System.out.println("新结果：" + pageInfo.getList());        // 男生结果
        System.out.println("新结果：" + femalePageInfo.getList());  // 女生结果


        int maleToUserResult = 0;           // 分配宿舍等信息后成为系统用户的男生数量
        int femaleToUserResult = 0;         // 分配宿舍等信息后成为系统用户的女生数量
        for (User user: maleList){
            userService.create(user);
            maleToUserResult ++;
        }
        for (User user: femaleList){
            userService.create(user);
            femaleToUserResult ++;
        }


        System.out.println("共插入" + maleToUserResult + "条男生用户数据");
        System.out.println("共插入" + femaleToUserResult + "条女生用户数据");
        System.out.println("男生宿舍房间id列表：" + maleRoomList);
        System.out.println("男生宿舍房间空床位数量列表：" + maleEachRoomFreeBedList);
        System.out.println("女生宿舍房间id列表：" + femaleRoomList);
        System.out.println("女生宿舍房间空床位数量列表：" + femaleEachRoomFreeBedList);

        return Result.ok();
    }
}
