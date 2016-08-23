package com.dream.example.data.entity;

import org.yapp.core.data.entity.DataEntity;
import org.yapp.db.annotation.Column;
import org.yapp.db.annotation.Table;

import java.util.Date;

/**
 * Description: 干货. <br>
 * Date: 2016/3/14 16:57 <br>
 * Author: ysj
 */
@Table(name = "gank")
public class Gank extends DataEntity<Gank> {

    @Column(name = "used")
    private boolean used;

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    @Column(name = "who")
    private String who;

    @Column(name = "desc")
    private String desc;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "publishedAt")
    private Date publishedAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}