package com.silk.mapper;

import java.util.List;
import java.util.Map;

import com.silk.entity.Room;


public interface RoomMapper {

	public int create(Room room);

	public int delete(Integer id);

	public int update(Room room);

	public int updateSelective(Room room);

	public int capacityPlusOne(Integer id);

	public int capacityMinusOne(Integer id);

	public List<Room> query(Room room);

	public Room detail(Integer id);

	public int count(Room room);


	public int buildingTotalStudentBedAmount(Integer buildingId);

	public double queryRoomBalance(Integer roomId);

	public int changeBalance(Double newBalance, Integer roomId);

	public int queryTotalStuBed(Integer buildingId);

//	public int queryTotalStuRoom(Integer buildingId);

	public List<Integer> queryEachStuRoomId(Integer buildingId);

	public int queryEachStuRoomFreeBedAmount(Integer roomId);



}