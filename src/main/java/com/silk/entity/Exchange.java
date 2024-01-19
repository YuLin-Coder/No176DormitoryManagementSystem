package com.silk.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.silk.utils.Entity;
import java.util.Date;


/**
 * @author LindaSilk
 * @date 2021年3月16日, 周二
 */
public class Exchange extends Entity{

	private Integer id;
	private Integer stuId;
	private String stuName;
	private Integer gender;		// 学生性别：女生=0；男生=1
	private Integer buildingId;
	private Integer roomId;
	private String exReason;
	private String exIntention;
	private Date exDate;
	private Integer exStatus;	// 调换状态：审核中=0，通过=1，未通过=2

	private User user;
	private Building building;
	private Room room;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getExReason() {
		return exReason;
	}

	public void setExReason(String exReason) {
		this.exReason = exReason;
	}

	public String getExIntention() {
		return exIntention;
	}

	public void setExIntention(String exIntention) {
		this.exIntention = exIntention;
	}

	public Date getExDate() {
		return exDate;
	}

	public void setExDate(Date exDate) {
		this.exDate = exDate;
	}

	public Integer getExStatus() {
		return exStatus;
	}

	public void setExStatus(Integer exStatus) {
		this.exStatus = exStatus;
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
}