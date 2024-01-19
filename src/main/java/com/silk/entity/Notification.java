package com.silk.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.silk.utils.Entity;
import java.util.Date;


/**
 * @author LindaSilk
 * @date 2021年3月19日, 周五
 */
public class Notification extends Entity{


	private Integer id;

	private Integer userId;

	@Length(max = 0)
	private String notiHead;

	@Length(max = 0)
	private String notiContent;

	/**
	 * 通知范围
	 * 对于宿管创建的通知：范围自动设定为宿管所在的楼栋ID
	 * 对于学生创建的通知：范围自动设置为0
	 */
	private Integer notiRange;

	/**
	 * 通知类型
	 * 一般=0，重要=1，过期=2
	 */
	private Integer notiType;

	private Date notiDate;


	public User user;
	public Building building;
	public Room room;

	/**
	 * 	该条通知是否属于自己
	 * 	用于查询学生所在寝室全部通知
 	 */
	public Integer isMyNotification;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNotiHead() {
		return notiHead;
	}
	public void setNotiHead(String notiHead) {
		this.notiHead = notiHead;
	}
	public String getNotiContent() {
		return notiContent;
	}
	public void setNotiContent(String notiContent) {
		this.notiContent = notiContent;
	}
	public Integer getNotiRange() {
		return notiRange;
	}
	public void setNotiRange(Integer notiRange) {
		this.notiRange = notiRange;
	}
	public Integer getNotiType() {
		return notiType;
	}
	public void setNotiType(Integer notiType) {
		this.notiType = notiType;
	}
	public Date getNotiDate() {
		return notiDate;
	}
	public void setNotiDate(Date notiDate) {
		this.notiDate = notiDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Integer getIsMyNotification() {
		return isMyNotification;
	}

	public void setIsMyNotification(Integer isMyNotification) {
		this.isMyNotification = isMyNotification;
	}
}