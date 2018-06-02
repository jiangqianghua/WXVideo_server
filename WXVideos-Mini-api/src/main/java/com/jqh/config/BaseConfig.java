package com.jqh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseConfig {

    //@Value("${com.jqh.config.res.basepath}")
    @Value("${spring.http.multipart.location}")
    private String resBasePath ;

    public String getResBasePath() {
        return resBasePath;
    }

}
