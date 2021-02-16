package com.nineya.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * jwt处理类
 *
 * @author 殇雪话诀别
 * 2020/11/29
 */
@Component
public class UserTokenUtil {
    /**
     * jwt 加密算法
     */
    private Algorithm algorithm;
    private JWTVerifier verifier;
    /**
     * token有效期时间
     */
    private static final int USEFUL_LIFE = 1800;

    public UserTokenUtil() {
        algorithm = Algorithm.HMAC256("secret");
        verifier = JWT.require(algorithm).build();
    }

    /**
     * 创建用户token，并将token创建时间存入
     *
     * @param username 用户名称
     * @return token字符串
     */
    public String createToken(String username) {
        Date expireTime = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(expireTime)
                .sign(algorithm);
    }

    /**
     * 校验token合法性
     *
     * @param token
     * @return
     */
    public DecodedJWT verifyToken(String token) {
        try {
            return verifier.verify(token);
        } catch (Exception e) {
            throw new TokenExpiredException("token 解析失败");
        }
    }

    /**
     * 取得用户名
     *
     * @param token
     * @return
     */
    public String getUserName(String token) {
        DecodedJWT jwt = verifyToken(token);
        return jwt.getClaim("username").asString();
    }
}
