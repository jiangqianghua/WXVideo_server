package com.jqh.controller;

import com.jqh.config.BaseConfig;
import com.jqh.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    public RedisOperator redis ;

    @Autowired
    public BaseConfig baseConfig ;

    public static final String USER_REDIS_SESSION = "user-redis-session";
}
