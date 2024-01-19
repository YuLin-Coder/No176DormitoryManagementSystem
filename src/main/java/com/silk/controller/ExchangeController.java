package com.silk.controller;

import com.github.pagehelper.PageInfo;
import com.silk.entity.Exchange;
import com.silk.entity.Repair;
import com.silk.entity.Room;
import com.silk.entity.User;
import com.silk.service.BuildingService;
import com.silk.service.ExchangeService;
import com.silk.service.RoomService;
import com.silk.service.UserService;
import com.silk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author LindaSilk
 * @date 2021年3月16日, 周二
 */
@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private RoomService roomService;

    @PostMapping("create")
    public Result create(@RequestBody Exchange exchange){
        int flag = exchangeService.create(exchange);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("delete")
    public Result delete(String ids){
        int flag = exchangeService.delete(ids);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody Exchange exchange){
        int flag = exchangeService.updateSelective(exchange);
        if(flag>0){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @GetMapping("detail")
    public Exchange detail(Integer id){
        return exchangeService.detail(id);
    }

    @PostMapping("query")
    public Map<String,Object> query(@RequestBody  Exchange exchange){
        PageInfo<Exchange> pageInfo = exchangeService.query(exchange);
        pageInfo.getList().forEach(entity->{
            entity.setUser(userService.detail(entity.getStuId()));
            entity.setBuilding(buildingService.detail(entity.getBuildingId()));
            entity.setRoom(roomService.detail(entity.getRoomId()));
        });

        return Result.ok(pageInfo);
    }


    @PostMapping("query_myself")
    public Map<String, Object> queryMyself(@RequestBody Exchange exchange, HttpServletRequest request){
        User param = (User)request.getAttribute("user");

        exchange.setStuId(param.getId());
        PageInfo<Exchange> pageInfo = exchangeService.query(exchange);
        pageInfo.getList().forEach(entity->{
            entity.setUser(userService.detail(entity.getStuId()));
            entity.setBuilding(buildingService.detail(entity.getBuildingId()));
            entity.setRoom(roomService.detail(entity.getRoomId()));
        });
        return Result.ok(pageInfo);
    }


    @PostMapping("stu_create")
    public Result stuCreate(@RequestBody Exchange exchange, HttpServletRequest request){
        User param = (User)request.getAttribute("user");                // 获取发起当前操作的用户信息

        User student = new User();
        student.setId(param.getId());                                      // 构造一个新的学生用户，并通过前端传入的用户信息为TA的ID赋值

        PageInfo<User> pageInfo = userService.query(student);              // 将查询到的该学生信息封装为pageInfo信息

        if (pageInfo.getList() != null && pageInfo.getList().size()>0){
            User user = pageInfo.getList().get(0);                         // 将pageInfo中数据赋值给user
            Room room = roomService.detail(user.getRoomId());              // 通过user房间ID获取room信息

            exchange.setBuildingId(room.getBuildingId());                    // 将“通过room获取到的楼栋id”赋值给exchange的楼栋id
            exchange.setRoomId(user.getRoomId());                            // 将“通过user获取到的房间id”赋值给exchange的房间id
            exchange.setStuId(param.getId());                                // 将“从前端获取到的用户id”赋值给exchange的学生id
            exchange.setStuName(user.getUserName());
            exchange.setGender(user.getGender());
            exchange.setExStatus(0);                                         // 将调换状态置0(等待审核)
            exchange.setExDate(new Date());                                 // 将调换申报时间设定为现在

            int flag = exchangeService.create(exchange);                       // 将上述信息打包后插入数据库
            if(flag>0){
                return Result.ok();
            }else{
                return Result.fail();
            }
        }else {
            return Result.fail("操作失败，缺少该学生相关宿舍信息");
        }
    }


}
