package com.dream.example.data.entity;

import org.yapp.core.data.entity.DataEntity;

/**
 * Description: 响应实体基类(用于列表数据). <br>
 * Date: 2016/4/20 11:16 <br>
 * Author: ysj
 */
public abstract class BaseList extends DataEntity<BaseList> {
    public int count;
    public int pageNo;
    public int pageSize;
}
