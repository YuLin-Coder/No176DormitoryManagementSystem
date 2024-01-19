package com.silk.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.silk.utils.Entity;
import java.util.Date;


/**
 * @author LindaSilk
 * @date 2021年3月19日, 周五
 */
public class Intention extends Entity{


	private Integer id;
	private String stuName;
	private Integer gender;

	/**
	 * 是否晚睡：
	 * 不晚睡 = 0
	 * 晚睡 = 1
	 * */
	private Integer late;
	/**
	 * 是否吵闹：
	 * 不吵闹 = 0
	 * 吵闹 = 1
	 * */
	private Integer noise;

	/**
	 *	行为习惯及意向：
	 *	不吵闹不晚睡 = 0
	 *	不吵闹晚睡 = 1
	 *	吵闹不晚睡 = 2
	 *	吵闹晚睡 = 3
	 */
	private Integer lateNNoise;

	/**
	 * 适用于宿舍分配时选择分配的楼栋
	 */
	private Integer maleBuilding;
	private Integer femaleBuilding;


	public Intention() {
	}

	public Intention(Integer id, String stuName, Integer gender, Integer late, Integer noise, Integer lateNNoise) {
		this.id = id;
		this.stuName = stuName;
		this.gender = gender;
		this.late = late;
		this.noise = noise;
		this.lateNNoise = lateNNoise;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getLate() {
		return late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}

	public Integer getNoise() {
		return noise;
	}

	public void setNoise(Integer noise) {
		this.noise = noise;
	}

	public Integer getLateNNoise() {
		return lateNNoise;
	}

	public void setLateNNoise(Integer lateNNoise) {
		this.lateNNoise = lateNNoise;
	}

	public Integer getMaleBuilding() {
		return maleBuilding;
	}

	public void setMaleBuilding(Integer maleBuilding) {
		this.maleBuilding = maleBuilding;
	}

	public Integer getFemaleBuilding() {
		return femaleBuilding;
	}

	public void setFemaleBuilding(Integer femaleBuilding) {
		this.femaleBuilding = femaleBuilding;
	}
}