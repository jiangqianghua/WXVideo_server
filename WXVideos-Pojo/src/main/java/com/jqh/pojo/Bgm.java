package com.jqh.pojo;

import javax.persistence.Id;

public class Bgm {
    /**
     * 全局唯一id
     */
    @Id
    private String id;

    /**
     * 背景创建者
     */
    private String author;

    /**
     * 歌曲的名称
     */
    private String name;

    /**
     * 歌曲的播放路径
     */
    private String path;

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
     * 获取背景创建者
     *
     * @return author - 背景创建者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置背景创建者
     *
     * @param author 背景创建者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取歌曲的名称
     *
     * @return name - 歌曲的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置歌曲的名称
     *
     * @param name 歌曲的名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取歌曲的播放路径
     *
     * @return path - 歌曲的播放路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置歌曲的播放路径
     *
     * @param path 歌曲的播放路径
     */
    public void setPath(String path) {
        this.path = path;
    }
}