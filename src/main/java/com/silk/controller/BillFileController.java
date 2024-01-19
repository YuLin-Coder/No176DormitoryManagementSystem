package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.BillFile;
import com.silk.service.BillFileService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
@RestController
@RequestMapping("/billFile")
public class BillFileController {

    @Autowired
    private BillFileService billFileService;

    @PostMapping("create")
    public Result create(@RequestBody BillFile billFile){
        int flag = billFileService.create(billFile);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = billFileService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody BillFile billFile){
        int flag = billFileService.update(billFile);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public BillFile detail(Integer id){
        return billFileService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  BillFile billFile){
        PageInfo<BillFile> pageInfo = billFileService.query(billFile);
        return Result.ok(pageInfo);
    }

}
