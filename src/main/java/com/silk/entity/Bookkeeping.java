package com.silk.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.silk.utils.Entity;
import java.util.Date;


/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
public class Bookkeeping extends Entity{


	private Integer id;
	private Integer roomId;
	private Double balance;
	private Integer userId;
	private Double bkMoney;
	private Integer bkType;			// 账目的类型：支出=0；收入=1

	/**
	 * 分类：
	 * 1. 对于支出：水电费=0；餐饮=1；图书=2；游玩=3；电影=4；其它=5。
	 * 2. 对于收入：舍费=0；红包=1
	 */
	private Integer classification;

	@Length(max = 0)
	private String remark;
	private Date bkTime;



	private User user;
	private Room room;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getBkMoney() {
		return bkMoney;
	}

	public void setBkMoney(Double bkMoney) {
		this.bkMoney = bkMoney;
	}

	public Integer getBkType() {
		return bkType;
	}

	public void setBkType(Integer bkType) {
		this.bkType = bkType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBkTime() {
		return bkTime;
	}

	public void setBkTime(Date bkTime) {
		this.bkTime = bkTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}