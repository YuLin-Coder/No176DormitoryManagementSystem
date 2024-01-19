package com.silk.framework.jwt;

import com.silk.entity.User;
import com.silk.framework.exception.MyException;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author LindaSilk
 * @date 2021年3月10日, 周三
 * @description JWT小工具
 */
public class JwtUtil {

    public static String token = "token";
    public static String jwt_secret="linda_silk_#@k93i*Opw";        // 秘钥
    public static long jwt_expr = 3600*24*1000;                     // 过期时长。单位ms

    // 生成token
    public static String sign(User user){

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;   // 指定签名的时候使用的签名算法
        long nowMillis = System.currentTimeMillis();                        // 生成签发时间
        Date date = new Date(nowMillis);

        Map<String,Object> claims = new HashMap<>();        // 创建playLoad的私有声明
        claims.put("id",user.getId());
        claims.put("userName",user.getUserName());

        String subject = user.getUserName();                // 生成签发人

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(date)
                .setSubject(subject)
                .signWith(signatureAlgorithm,jwt_secret);

        Date exprDate = new Date(nowMillis + jwt_expr);     //设置过期时间
        builder.setExpiration(exprDate);
        return builder.compact();
    }

    // 验证token
    public static boolean verify(String token){
        try {
            if(StringUtils.isEmpty(token)){
                return false;
            }
            Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户信息
    public static User getUser(String token){
        try {
            if(StringUtils.isEmpty(token)){
                throw new MyException("token不能为空");
            }
            if(verify(token)){
                Claims claims = Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(token).getBody();
                User user = new User();
                user.setId(Integer.parseInt(claims.get("id")+""));
                user.setUserName(claims.get("userName")+"");
                return user;
            }else{
                throw new MyException("超时或不合法token");
            }
        } catch (Exception e) {
            throw new MyException("超时或不合法token");
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(10);
        user.setUserName("admin");
        System.out.println(sign(user));
    }

}
