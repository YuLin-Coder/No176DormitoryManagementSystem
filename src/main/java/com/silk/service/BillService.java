package com.silk.service;

import com.silk.mapper.BillMapper;
import com.silk.entity.Bill;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
@Service
public class BillService {

    @Autowired
    private BillMapper billMapper;

    public int create(Bill bill) {
        return billMapper.create(bill);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                billMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return billMapper.delete(id);
    }

    public int update(Bill bill) {
        return billMapper.update(bill);
    }

    public int updateSelective(Bill bill) {
        return billMapper.updateSelective(bill);
    }

    public PageInfo<Bill> query(Bill bill) {
        if(bill != null && bill.getPage() != null){
            PageHelper.startPage(bill.getPage(),bill.getLimit());
        }
        return new PageInfo<Bill>(billMapper.query(bill));
    }

    public Bill detail(Integer id) {
        return billMapper.detail(id);
    }

    public int count(Bill bill) {
        return billMapper.count(bill);
    }

    // 某栋楼没有缴费的房间数量
    public int notPaidAmount(Integer buildingId){
        return billMapper.notPaidAmount(buildingId);
    }

    // 某栋楼已经缴费的房间数量
    public int havePaidAmount(Integer buildingId){
        return billMapper.havePaidAmount(buildingId);
    }

    // 某栋楼拖欠水电费的房间数量
    public int delayPaidAmount(Integer buildingId){
        return billMapper.delayPaidAmount(buildingId);
    }


    public double totalWaterUsed(Integer buildingId){
        return billMapper.totalWaterUsed(buildingId);
    }

    public double totalWaterFee(Integer buildingId){
        return billMapper.totalWaterFee(buildingId);

    }

    public double totalEnergyUsed(Integer buildingId){
        return billMapper.totalEnergyUsed(buildingId);

    }
    public double totalEnergyFee(Integer buildingId){
        return billMapper.totalEnergyFee(buildingId);

    }

    public double totalFee(Integer buildingId){
        return billMapper.totalFee(buildingId);

    }


    public int queryLatestMonth(){
        return billMapper.queryLatestMonth();
    }

    public int queryLatestYear(){
        return billMapper.queryLatestYear();
    }

    public double historyWaterUsed(Integer buildingId, Integer billYear, Integer billMonth){
        return billMapper.historyWaterUsed(buildingId, billYear, billMonth);
    }

    public double historyWaterFee(Integer buildingId, Integer billYear, Integer billMonth){
        return billMapper.historyWaterFee(buildingId, billYear, billMonth);
    }

    public double historyEnergyUsed(Integer buildingId, Integer billYear, Integer billMonth){
        return billMapper.historyEnergyUsed(buildingId, billYear, billMonth);
    }

    public double historyEnergyFee(Integer buildingId, Integer billYear, Integer billMonth){
        return billMapper.historyEnergyFee(buildingId, billYear, billMonth);
    }

    public double historyTotalUsed(Integer buildingId, Integer billYear, Integer billMonth){
        return billMapper.historyTotalUsed(buildingId, billYear, billMonth);
    }



    public int addToDatabase(MultipartFile file) throws Exception{
        int result = 0;
        List<Bill> billList = new ArrayList<>();                    // 创建billList

        String fileName = file.getOriginalFilename();                           // 获取上传的文件名
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);    // 获取上传文件后缀

        System.out.println("文件后缀为：" + suffix);
        InputStream inputStream = file.getInputStream();            // 输入流读取文件
        Workbook workbook = null;                                   // 1. 新建工作簿

        if(suffix.equals("xlsx")){
            workbook = new XSSFWorkbook(inputStream);               // Excel2007版本及以后 用XSSF(即Poi-ooxml)处理
        } else {
            workbook = new HSSFWorkbook(inputStream);               // Excel2003版本 用HSSF(即Poi)处理
        }

        Sheet sheet = workbook.getSheetAt(0);                     // 2. 获取当前工作表

        if(sheet != null){
            for (int i = 1; i <= sheet.getLastRowNum(); i++){       // 3. 从第二行开始遍历工作表的每一行
                Row row = sheet.getRow(i);
                if (row != null){
                    List<String> list = new ArrayList<>();
                    for (Cell cell : row){                          // 4. 遍历每一个单元格
                        if (cell != null) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);        // 将单元格值转化为字符串
                            String cellValue = cell.getStringCellValue();   // 获取字符串形式的值
                            list.add(cellValue);                            // 将值加入数组
                        }
                    }

                    // 值的转化
                    Integer id = Integer.parseInt(list.get(0));
                    Integer billYear = Integer.parseInt(list.get(1));
                    Integer billMonth = Integer.parseInt(list.get(2));
                    Integer buildingId = Integer.parseInt(list.get(3));
                    Integer roomId = Integer.parseInt(list.get(4));
                    Double waterUsed = Double.parseDouble(list.get(5));
                    Double waterFee = Double.parseDouble(list.get(6));
                    Double energyUsed = Double.parseDouble(list.get(7));
                    Double energyFee = Double.parseDouble(list.get(8));
                    Double totalFee = Double.parseDouble(list.get(9));
                    Integer paid = Integer.parseInt(list.get(10));

                    // 构造一个账单对象，并将从个单元格获取的值赋给它
                    Bill bill = new Bill(id, billYear, billMonth, buildingId, roomId,
                            waterUsed, waterFee, energyUsed, energyFee, totalFee, paid);
                    System.out.println("获取到第"+ i +"条账单信息：");
                    System.out.println("----------");
                    System.out.println(bill);
                    billList.add(bill);                                 // 将新的一条账单加入billList
                }
            }

            for (Bill bill: billList){
                result = billMapper.addBillExcelFileToDatabase(bill);      // 将每一条账单插入数据库
            }
        }
        return result;
    }

}
