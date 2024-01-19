package com.silk.service;

import com.silk.mapper.NotificationMapper;
import com.silk.entity.Notification;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author LindaSilk
 * @date 2021年3月19日, 周五
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public int create(Notification notification) {
        return notificationMapper.create(notification);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                notificationMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return notificationMapper.delete(id);
    }

    public int update(Notification notification) {
        return notificationMapper.update(notification);
    }

    public int updateSelective(Notification notification) {
        return notificationMapper.updateSelective(notification);
    }

    public PageInfo<Notification> query(Notification notification) {
        if(notification != null && notification.getPage() != null){
            PageHelper.startPage(notification.getPage(),notification.getLimit());
        }
        return new PageInfo<Notification>(notificationMapper.query(notification));
    }

    public Notification detail(Integer id) {
        return notificationMapper.detail(id);
    }

    public int count(Notification notification) {
        return notificationMapper.count(notification);
    }


    public PageInfo<Notification> queryMyBuilding(Integer buildingId){
        return new PageInfo<Notification>(notificationMapper.queryMyBuilding(buildingId));
    }

    public PageInfo<Notification> queryMyRoom(Integer userId){
        return new PageInfo<Notification>(notificationMapper.queryMyRoom(userId));
    }

}
