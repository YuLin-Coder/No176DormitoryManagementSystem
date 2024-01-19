package com.silk.service;

import com.silk.mapper.BillFileMapper;
import com.silk.entity.BillFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
@Service
public class BillFileService {

    @Autowired
    private BillFileMapper billFileMapper;

    public int create(BillFile billFile) {
        return billFileMapper.create(billFile);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                billFileMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return billFileMapper.delete(id);
    }

    public int update(BillFile billFile) {
        return billFileMapper.update(billFile);
    }

    public int updateSelective(BillFile billFile) {
        return billFileMapper.updateSelective(billFile);
    }

    public PageInfo<BillFile> query(BillFile billFile) {
        if(billFile != null && billFile.getPage() != null){
            PageHelper.startPage(billFile.getPage(),billFile.getLimit());
        }
        return new PageInfo<BillFile>(billFileMapper.query(billFile));
    }

    public BillFile detail(Integer id) {
        return billFileMapper.detail(id);
    }

    public int count(BillFile billFile) {
        return billFileMapper.count(billFile);
    }
}
