package com.jqh.controller;

import com.jqh.service.BgmService;
import com.jqh.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "bgm管理",tags = {"BGM管理controller"})
@RequestMapping("/bgm")
public class BgmController extends BaseController {

    //http://192.168.1.102/wxvideos/bgm/bgm1.mp3
    @Autowired
    private BgmService bgmService ;

    @ApiOperation(value = "bgm列表",notes = "bgm列表获取")
    @PostMapping("/list")
    public JSONResult list(){
        return JSONResult.ok(bgmService.queryBgmList());
    }

}
