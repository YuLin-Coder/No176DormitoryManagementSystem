package com.silk.framework.mvc;

import com.silk.entity.User;
import com.silk.framework.exception.MyException;
import com.silk.framework.jwt.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LindaSilk
 * @date 2021年3月10日, 周三
 * @description Token拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(JwtUtil.token);
        User user = JwtUtil.getUser(token);             // 根据token获取用户对象
        if (user == null) {
            throw new MyException("超时或不合法的Token！");
        }
        String newToken = JwtUtil.sign(user);           // 生成一个新的token
        response.setHeader(JwtUtil.token, newToken);
        response.setHeader("Access-Control-Expose-Headers", JwtUtil.token); // 将token暴露出来(用于跨域)
        request.setAttribute("user", user);          // 设置用户信息
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
