package com.jqh.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "search_records")
public class SearchRecords {
    /**
     * 全局唯一id
     */
    @Id
    private String id;

    /**
     * 搜索词汇
     */
    private String conent;

    /**
     * 获取全局唯一id
     *
     * @return id - 全局唯一id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置全局唯一id
     *
     * @param id 全局唯一id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取搜索词汇
     *
     * @return conent - 搜索词汇
     */
    public String getConent() {
        return conent;
    }

    /**
     * 设置搜索词汇
     *
     * @param conent 搜索词汇
     */
    public void setConent(String conent) {
        this.conent = conent;
    }
}