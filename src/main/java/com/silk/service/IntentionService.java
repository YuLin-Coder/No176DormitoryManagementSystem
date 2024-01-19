package com.silk.service;

import com.silk.entity.Bill;
import com.silk.mapper.IntentionMapper;
import com.silk.entity.Intention;
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
 * @date 2021年3月19日, 周五
 */
@Service
public class IntentionService {

    @Autowired
    private IntentionMapper intentionMapper;

    public int create(Intention intention) {
        return intentionMapper.create(intention);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                intentionMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return intentionMapper.delete(id);
    }

    public int update(Intention intention) {
        return intentionMapper.update(intention);
    }

    public int updateSelective(Intention intention) {
        return intentionMapper.updateSelective(intention);
    }

    public PageInfo<Intention> query(Intention intention) {
        if(intention != null && intention.getPage() != null){
            PageHelper.startPage(intention.getPage(),intention.getLimit());
        }
        return new PageInfo<Intention>(intentionMapper.query(intention));
    }

    public Intention detail(Integer id) {
        return intentionMapper.detail(id);
    }

    public int count(Intention intention) {
        return intentionMapper.count(intention);
    }


    public int addToDatabase(MultipartFile file) throws Exception{
        int result = 0;
        List<Intention> intentionList = new ArrayList<>();                    // 创建billList

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
                    String stuName = list.get(1);
                    Integer gender = Integer.parseInt(list.get(2));
//                    Integer lateNNoise = Integer.parseInt(list.get(3));
                    Integer late = Integer.parseInt(list.get(3));
                    Integer noise = Integer.parseInt(list.get(4));

                    int lateNNoise = 0;
                    if (late == 0){
                        if (noise == 0) {
                            lateNNoise = 0;
                        } else {
                            lateNNoise = 1;
                        }
                    }else if (late == 1){
                        if (noise == 0) {
                            lateNNoise = 2;
                        } else {
                            lateNNoise = 3;
                        }
                    }

                    // 构造一个账单对象，并将从个单元格获取的值赋给它
                    Intention intention = new Intention(id, stuName, gender, late, noise, lateNNoise);
                    System.out.println("获取到第"+ i +"条账单信息：");
                    System.out.println("----------");
                    System.out.println(intention);
                    intentionList.add(intention);                                 // 将新的一条账单加入billList
                }
            }

            for (Intention intention: intentionList){
                result = intentionMapper.addIntentionExcelFileToDatabase(intention);      // 将每一条账单插入数据库
            }
        }
        return result;
    }



    public int queryMaleAmount(){
        return intentionMapper.queryMaleAmount();
    }

    public int queryFemaleAmount(){
        return intentionMapper.queryFemaleAmount();
    }


    public List<Integer> queryMaleIdByType(){
        return intentionMapper.queryMaleIdByType();
    }
}

