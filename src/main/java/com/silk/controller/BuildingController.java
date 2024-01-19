package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.Building;
import com.silk.entity.User;
import com.silk.service.BuildingService;
import com.silk.service.RoomService;
import com.silk.service.UserService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LindaSilk
 * @date 2021年3月12日, 周五
 */
@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;


    @PostMapping("create")
    public Result create(@RequestBody Building building){
        int flag = buildingService.create(building);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = buildingService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody Building building){
        int flag = buildingService.update(building);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public Building detail(Integer id){
        return buildingService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  Building building){
        PageInfo<Building> pageInfo = buildingService.query(building);

        /*
         * @imp 获取用户信息
         * @silkTag 楼栋->根据楼栋表管理员ID，查用户表对应的管理员名字
         */
        pageInfo.getList().forEach(entity->{
            User user = userService.detail(entity.getManagerId());
            entity.setUser(user);
        });

        return Result.ok(pageInfo);
    }


    // 楼栋的楼层数
    @GetMapping("query_floor_num")
    public Result queryFloorNum(Integer id){
        return Result.ok(buildingService.detail(id));
    }


    // 楼栋的学生入住率（学生人数/学生房间床位总数）
    @GetMapping("occupancy_rate_and_gender")
    public Result occupancyRateAndGender(Integer buildingId){
        int buildingTotalStudentBedAmount = roomService.buildingTotalStudentBedAmount(buildingId);
        int buildingActualStudentAmount = userService.buildingActualStudentAmount(buildingId);

        int buildingLiverGender = buildingService.queryGender(buildingId);

        Double occupancyRate = (double) buildingActualStudentAmount / buildingTotalStudentBedAmount;
        System.out.println("occupancyRate: " + occupancyRate);

        double rateTimesOneHundred = occupancyRate * 100;
        System.out.println("midFigure: " + rateTimesOneHundred);
        BigDecimal bd1 = new BigDecimal(rateTimesOneHundred);
        rateTimesOneHundred = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


        System.out.println(buildingId + "栋入住率: " + rateTimesOneHundred + "%");

        List<Object> occupancyRateAndGenderList = new ArrayList<>();
        occupancyRateAndGenderList.add(rateTimesOneHundred);
        occupancyRateAndGenderList.add(buildingLiverGender);

        System.out.println("occupancyRateAndGenderList: " + occupancyRateAndGenderList);
        return Result.ok(occupancyRateAndGenderList);

    }


    @PostMapping("query_male_building")
    public Map<String,Object> queryMaleBuilding(@RequestBody Building building){            // @RequestBody:将json格式的数据转为java对象
        PageInfo<Building> pageInfo = buildingService.query(building);

        pageInfo.getList().forEach(entity->{
            int buildingId = entity.getId();
            int totalStuBed = roomService.buildingTotalStudentBedAmount(buildingId);             // 某栋楼的学生床位总数
            int totalStuLiver = userService.buildingActualStudentAmount(buildingId);            // 某栋楼的现住学生总数
            entity.setFreeStuBed(totalStuBed - totalStuLiver);                                  // 空床位数量
            System.out.println("空床位数量" + entity.getFreeStuBed());
        });

        return Result.ok(pageInfo);
    }


    @PostMapping("query_female_building")
    public Map<String,Object> queryFemaleBuilding(@RequestBody Building building){            // @RequestBody:将json格式的数据转为java对象
        PageInfo<Building> pageInfo = buildingService.query(building);

        pageInfo.getList().forEach(entity->{
            int buildingId = entity.getId();
            int totalStuBed = roomService.buildingTotalStudentBedAmount(buildingId);             // 某栋楼的学生床位总数
            int totalStuLiver = userService.buildingActualStudentAmount(buildingId);            // 某栋楼的现住学生总数
            entity.setFreeStuBed(totalStuBed - totalStuLiver);                                  // 空床位数量
            System.out.println("空床位数量" + entity.getFreeStuBed());
        });

        return Result.ok(pageInfo);
    }


}
