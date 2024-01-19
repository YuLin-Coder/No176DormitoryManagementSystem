package com.silk.service;

import com.silk.entity.Menu;
import com.silk.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LindaSilk
 * @date 2021年3月10日, 周三
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    // 根据用户的角色查询菜单
    public List<Menu> query(Integer userId){
        return menuMapper.query(userId);
    }

}
