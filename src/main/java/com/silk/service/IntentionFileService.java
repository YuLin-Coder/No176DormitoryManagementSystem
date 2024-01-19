package com.silk.service;

import com.silk.mapper.IntentionFileMapper;
import com.silk.entity.IntentionFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author LindaSilk
 * @date 2021年3月19日, 周五
 */
@Service
public class IntentionFileService {

    @Autowired
    private IntentionFileMapper intentionFileMapper;

    public int create(IntentionFile intentionFile) {
        return intentionFileMapper.create(intentionFile);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                intentionFileMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return intentionFileMapper.delete(id);
    }

    public int update(IntentionFile intentionFile) {
        return intentionFileMapper.update(intentionFile);
    }

    public int updateSelective(IntentionFile intentionFile) {
        return intentionFileMapper.updateSelective(intentionFile);
    }

    public PageInfo<IntentionFile> query(IntentionFile intentionFile) {
        if(intentionFile != null && intentionFile.getPage() != null){
            PageHelper.startPage(intentionFile.getPage(),intentionFile.getLimit());
        }
        return new PageInfo<IntentionFile>(intentionFileMapper.query(intentionFile));
    }

    public IntentionFile detail(Integer id) {
        return intentionFileMapper.detail(id);
    }

    public int count(IntentionFile intentionFile) {
        return intentionFileMapper.count(intentionFile);
    }
}
