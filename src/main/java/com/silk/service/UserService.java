package com.silk.service;

import com.silk.mapper.UserMapper;
import com.silk.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author LindaSilk
 * @date 2021年3月07日, 周日
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int create(User user) {
        return userMapper.create(user);
    }

    public int delete(String ids) {
        String[] arr = ids.split(",");              // 将前端删除时传递的多个id组成的字符串分开
        int row = 0;
        for (String s : arr) {
            if(!StringUtils.isEmpty(s)){
                userMapper.delete(Integer.parseInt(s));
            row++;
            }
        }
        return row;
    }

    public int delete(Integer id) {
        return userMapper.delete(id);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    public int updateSelective(User user) {
        return userMapper.updateSelective(user);
    }

    public PageInfo<User> query(User user) {
        if(user != null && user.getPage() != null){
            PageHelper.startPage(user.getPage(),user.getLimit());
        }
        return new PageInfo<User>(userMapper.query(user));
    }

    public User detail(Integer id) {
        return userMapper.detail(id);
    }

    public int count(User user) {
        return userMapper.count(user);
    }

    public User login(String userName, String userPwd, Integer userType){
        return userMapper.login(userName, userPwd, userType);
    }

    // 查询某个房间住了多少人
    public int queryLiverAmount(Integer roomId){
        return userMapper.queryLiverAmount(roomId);
    }

    // 查询某栋楼住了多少人
    public int buildingActualStudentAmount(Integer buildingId){
        return userMapper.buildingActualStudentAmount(buildingId);
    }


}
