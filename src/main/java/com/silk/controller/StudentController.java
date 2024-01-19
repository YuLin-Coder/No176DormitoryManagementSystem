package com.silk.controller;

import com.silk.entity.Building;
import com.silk.entity.User;
import com.silk.service.BuildingService;
import com.silk.service.RoomService;
import com.silk.service.UserService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author LindaSilk
 * @date 2021年3月15日, 周一
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        User param = (User)request.getAttribute("user");        // 获取前端发起请求的用户信息
        System.out.println(param);
        User student = userService.detail(param.getId());
        student.setRoom(roomService.detail(student.getRoomId()));
        student.setBuilding(buildingService.detail(student.getRoomId()));
        System.out.println(student);
        return Result.ok(student);
    }

}
