package com.silk.mapper;

import java.util.List;
import java.util.Map;

import com.silk.entity.Bill;


public interface BillMapper {

	public int create(Bill bill);

	public int delete(Integer id);

	public int update(Bill bill);

	public int updateSelective(Bill bill);

	public List<Bill> query(Bill bill);

	public Bill detail(Integer id);

	public int count(Bill bill);


	// 某栋楼没有缴费的房间数量
	int notPaidAmount(Integer buildingId);

	// 某栋楼已经缴费的房间数量
	int havePaidAmount(Integer buildingId);

	// 某栋楼拖欠水电费的房间数量
	int delayPaidAmount(Integer buildingId);


	double totalWaterUsed(Integer buildingId);
	double totalWaterFee(Integer buildingId);

	double totalEnergyUsed(Integer buildingId);
	double totalEnergyFee(Integer buildingId);

	double totalFee(Integer buildingId);


	int queryLatestMonth();
	int queryLatestYear();

	double historyWaterUsed(Integer buildingId, Integer billYear, Integer billMonth);
	double historyWaterFee(Integer buildingId, Integer billYear, Integer billMonth);
	double historyEnergyUsed(Integer buildingId, Integer billYear, Integer billMonth);
	double historyEnergyFee(Integer buildingId, Integer billYear, Integer billMonth);
	double historyTotalUsed(Integer buildingId, Integer billYear, Integer billMonth);


	// 将Excel数据添加到数据库
	int addBillExcelFileToDatabase(Bill bill);


}