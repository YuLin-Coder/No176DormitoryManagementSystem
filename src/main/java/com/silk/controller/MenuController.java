package com.silk.controller;

import com.silk.entity.Menu;
import com.silk.entity.User;
import com.silk.service.MenuService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LindaSilk
 * @date 2021年3月10日, 周三
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/query")
    public Result query(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return Result.ok(menuService.query(user.getId()));

//        List<Menu> menus = menuService.query(user.getId());
//        List<Menu> firstLevelMenuList = new ArrayList<>();
//
//        // 一级菜单
//        for (Menu menu : menus) {
//            if (menu.getParentId() == 0){
//                firstLevelMenuList.add(menu);
//            }
//        }
//
//        // 二级菜单
//        for (Menu parent : firstLevelMenuList) {
//            List<Menu> secondLevelMenuList = new ArrayList<>();
//            for (Menu entity : menus){
//                if (parent.getId() == entity.getParentId()){
//                    secondLevelMenuList.add(entity);
//                }
//            }
//            parent.setChild(secondLevelMenuList);       // 二级菜单放入一级菜单下
//        }
//
//        return Result.ok(firstLevelMenuList);
    }
}
