package com.silk.service;

import com.silk.entity.Exchange;
import com.silk.entity.Notification;
import com.silk.mapper.BookkeepingMapper;
import com.silk.entity.Bookkeeping;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author LindaSilk
 * @date 2021年3月13日, 周六
 */
@Service
public class BookkeepingService {

    @Autowired
    private BookkeepingMapper bookkeepingMapper;

    public int create(Bookkeeping bookkeeping) {
        return bookkeepingMapper.create(bookkeeping);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                bookkeepingMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return bookkeepingMapper.delete(id);
    }

    public int update(Bookkeeping bookkeeping) {
        return bookkeepingMapper.update(bookkeeping);
    }

    public int updateSelective(Bookkeeping bookkeeping) {
        return bookkeepingMapper.updateSelective(bookkeeping);
    }

    public PageInfo<Bookkeeping> query(Bookkeeping bookkeeping) {
        if(bookkeeping != null && bookkeeping.getPage() != null){
            PageHelper.startPage(bookkeeping.getPage(),bookkeeping.getLimit());
        }
        return new PageInfo<Bookkeeping>(bookkeepingMapper.query(bookkeeping));
    }

    public Bookkeeping detail(Integer id) {
        return bookkeepingMapper.detail(id);
    }

    public int count(Bookkeeping bookkeeping) {
        return bookkeepingMapper.count(bookkeeping);
    }


    public PageInfo<Bookkeeping> queryMyRoomExpenditure(Integer userId){
        return new PageInfo<Bookkeeping>(bookkeepingMapper.queryMyRoomExpenditure(userId));
    }

    public List<Integer> queryMyRoomEachClassificationAmount(Integer userId){
        return bookkeepingMapper.queryMyRoomEachClassificationAmount(userId);
    }


    public Date queryMaxDate(Integer roomId){
        return bookkeepingMapper.queryMaxDate(roomId);
    }

    public double queryMonthlyExpenditureByClassification(Integer roomId, Integer year, Integer month, Integer classification){
        return bookkeepingMapper.queryMonthlyExpenditureByClassification(roomId, year, month, classification);
    }

    public PageInfo<Bookkeeping> queryMyRoom(Bookkeeping bookkeeping){
        if(bookkeeping != null && bookkeeping.getPage() != null){
            PageHelper.startPage(bookkeeping.getPage(),bookkeeping.getLimit());
        }
        return new PageInfo<Bookkeeping>(bookkeepingMapper.queryMyRoom(bookkeeping));
    }

}
