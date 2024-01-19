package com.silk.mapper;

import java.util.List;
import java.util.Map;

import com.silk.entity.Notification;


public interface NotificationMapper {

	public int create(Notification notification);

	public int delete(Integer id);

	public int update(Notification notification);

	public int updateSelective(Notification notification);

	public List<Notification> query(Notification notification);

	public Notification detail(Integer id);

	public int count(Notification notification);

	public List<Notification> queryMyBuilding(Integer buildingId);

	public List<Notification> queryMyRoom(Integer userId);

}