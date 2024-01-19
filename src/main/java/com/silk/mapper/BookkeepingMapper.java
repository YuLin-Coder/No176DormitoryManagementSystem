package com.silk.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.silk.entity.Bookkeeping;
import com.silk.entity.Notification;


public interface BookkeepingMapper {

	public int create(Bookkeeping bookkeeping);

	public int delete(Integer id);

	public int update(Bookkeeping bookkeeping);

	public int updateSelective(Bookkeeping bookkeeping);

	public List<Bookkeeping> query(Bookkeeping bookkeeping);

	public Bookkeeping detail(Integer id);

	public int count(Bookkeeping bookkeeping);

	public List<Bookkeeping> queryMyRoomExpenditure(Integer userId);

	public List<Bookkeeping> queryMyRoom(Bookkeeping bookkeeping);

	public List<Integer> queryMyRoomEachClassificationAmount(Integer userId);


	public Date queryMaxDate(Integer roomId);

	public double queryMonthlyExpenditureByClassification(Integer roomId, Integer year, Integer month, Integer classification);

}