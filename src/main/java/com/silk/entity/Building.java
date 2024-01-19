package com.silk.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.silk.utils.Entity;
import java.util.Date;


/**
 * @author LindaSilk
 * @date 2021年3月12日, 周五
 * @description 楼栋信息
 */
public class Building extends Entity{

	private Integer id;
	private String buildingName;		// 楼栋名
	private Integer floorNum;			// 楼层数
	private Integer liverGender;		// 此栋楼居住学生性别：女生=0；男生=1
	private Integer managerId;			// 管理员ID
	private String managerName;			// 管理员姓名

	/**
	 * @imp 关联到用户实体
	 * @silkTag 楼栋->根据楼栋表管理员ID，查用户表对应的管理员名字
	 */
	private User user;


	private Integer freeStuBed;			// 空床位数量（学生床）


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public Integer getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}

	public Integer getLiverGender() {
		return liverGender;
	}

	public void setLiverGender(Integer liverGender) {
		this.liverGender = liverGender;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Integer getFreeStuBed() {
		return freeStuBed;
	}

	public void setFreeStuBed(Integer freeStuBed) {
		this.freeStuBed = freeStuBed;
	}
}