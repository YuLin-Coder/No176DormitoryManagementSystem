package com.silk.mapper;

import java.util.List;
import java.util.Map;

import com.silk.entity.Bill;
import com.silk.entity.Intention;


public interface IntentionMapper {

	public int create(Intention intention);

	public int delete(Integer id);

	public int update(Intention intention);

	public int updateSelective(Intention intention);

	public List<Intention> query(Intention intention);

	public Intention detail(Integer id);

	public int count(Intention intention);

	// 将Excel数据添加到数据库
	int addIntentionExcelFileToDatabase(Intention intention);

	public int queryMaleAmount();

	public int queryFemaleAmount();

	public List<Integer> queryMaleIdByType();


}