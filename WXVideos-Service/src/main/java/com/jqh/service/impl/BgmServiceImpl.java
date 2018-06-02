package com.jqh.service.impl;

import com.jqh.mapper.BgmMapper;
import com.jqh.pojo.Bgm;
import com.jqh.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper ;
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
