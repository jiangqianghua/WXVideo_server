package com.jqh.mapper;

import com.jqh.pojo.SearchRecords;
import com.jqh.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    public List<String> getHotWords();
}