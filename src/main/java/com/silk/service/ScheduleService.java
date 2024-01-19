package com.silk.service;

import com.silk.mapper.ScheduleMapper;
import com.silk.entity.Schedule;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author LindaSilk
 * @date 2021年3月20日, 周六
 */
@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    public int create(Schedule schedule) {
        return scheduleMapper.create(schedule);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                scheduleMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return scheduleMapper.delete(id);
    }

    public int update(Schedule schedule) {
        return scheduleMapper.update(schedule);
    }

    public int updateSelective(Schedule schedule) {
        return scheduleMapper.updateSelective(schedule);
    }

    public PageInfo<Schedule> query(Schedule schedule) {
        if(schedule != null && schedule.getPage() != null){
            PageHelper.startPage(schedule.getPage(),schedule.getLimit());
        }
        return new PageInfo<Schedule>(scheduleMapper.query(schedule));
    }

    public Schedule detail(Integer id) {
        return scheduleMapper.detail(id);
    }

    public int count(Schedule schedule) {
        return scheduleMapper.count(schedule);
    }


    public int waitForCompletionAmount(String todayTime, Integer userId){
        return scheduleMapper.waitForCompletionAmount(todayTime, userId);
    }

    public int alreadyFinishedAmount(String todayTime, Integer userId){
        return scheduleMapper.alreadyFinishedAmount(todayTime, userId);
    }


    public PageInfo<Schedule> querySchedule(Schedule schedule) {
        if(schedule != null && schedule.getPage() != null){
            PageHelper.startPage(schedule.getPage(),schedule.getLimit());
        }
        return new PageInfo<Schedule>(scheduleMapper.querySchedule(schedule));
    }


}
