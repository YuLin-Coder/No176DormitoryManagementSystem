package com.silk.service;

import com.silk.mapper.ExchangeMapper;
import com.silk.entity.Exchange;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author LindaSilk
 * @date 2021年3月16日, 周二
 */
@Service
public class ExchangeService {

    @Autowired
    private ExchangeMapper exchangeMapper;

    public int create(Exchange exchange) {
        return exchangeMapper.create(exchange);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                exchangeMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return exchangeMapper.delete(id);
    }

    public int update(Exchange exchange) {
        return exchangeMapper.update(exchange);
    }

    public int updateSelective(Exchange exchange) {
        return exchangeMapper.updateSelective(exchange);
    }

    public PageInfo<Exchange> query(Exchange exchange) {
        if(exchange != null && exchange.getPage() != null){
            PageHelper.startPage(exchange.getPage(),exchange.getLimit());
        }
        return new PageInfo<Exchange>(exchangeMapper.query(exchange));
    }

    public Exchange detail(Integer id) {
        return exchangeMapper.detail(id);
    }

    public int count(Exchange exchange) {
        return exchangeMapper.count(exchange);
    }
}
