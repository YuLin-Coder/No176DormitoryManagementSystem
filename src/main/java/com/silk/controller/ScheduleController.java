package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.Schedule;
import com.silk.entity.User;
import com.silk.service.ScheduleService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author LindaSilk
 * @date 2021年3月20日, 周六
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("create")
    public Result create(@RequestBody Schedule schedule, HttpServletRequest request){
//        Date date = sdf.parse( " 2008-07-10 19:20:00 " );
//
//
//        String originalStringDate =  schedule.getSchTime();

        User param = (User)request.getAttribute("user");
        int userId = param.getId();

        schedule.setUserId(userId);

        int flag = scheduleService.create(schedule);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = scheduleService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody Schedule schedule){
        int flag = scheduleService.update(schedule);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public Schedule detail(Integer id){
        return scheduleService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  Schedule schedule){
        PageInfo<Schedule> pageInfo = scheduleService.query(schedule);
        return Result.ok(pageInfo);
    }

    // 查询日程统计信息（待完成数量、已完成数量）
    @GetMapping("query_schedule_num_info")
    public Result queryScheduleNumInfo(HttpServletRequest request){
        User param = (User)request.getAttribute("user");
        int userId = param.getId();
        Date today = new Date();                                // 当前时间

        System.out.println("schedule userId: " + userId);
        System.out.println("today: " + today);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayTime=sdf.format(today);
        System.out.println("格式化字符串：");
        System.out.println(todayTime);

        int waitForCompletionAmount = scheduleService.waitForCompletionAmount(todayTime, userId);
        int alreadyFinishedAmount = scheduleService.alreadyFinishedAmount(todayTime, userId);

        System.out.println("waitForCompletionAmount" + waitForCompletionAmount);
        System.out.println("alreadyFinishedAmount" + alreadyFinishedAmount);

        List<Integer> numInfo = new ArrayList<>();
        numInfo.add(waitForCompletionAmount);
        numInfo.add(alreadyFinishedAmount);

        return Result.ok(numInfo);
    }


    // 查询所有还未进行的日程
    @PostMapping("query_schedule")
    public Map<String,Object> querySchedule(@RequestBody  Schedule schedule, HttpServletRequest request){
        User param = (User)request.getAttribute("user");
        int userId = param.getId();
        Date today = new Date();

        System.out.println("schedule userId: " + userId);
        System.out.println("today: " + today);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String todayTime=sdf.format(today);
//        System.out.println("格式化字符串：");
//        System.out.println(todayTime);

        schedule.setUserId(userId);
        schedule.setSchTime(today);
        PageInfo<Schedule> pageInfo = scheduleService.querySchedule(schedule);
        System.out.println("日程列表：");
        System.out.println(pageInfo);

        return Result.ok(pageInfo);
    }

}
