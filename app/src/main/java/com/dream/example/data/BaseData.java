package com.dream.example.data;

import org.yapp.core.data.entity.DataEntity;
import org.yapp.db.annotation.Column;
import org.yapp.db.annotation.Table;

/**
 * Description: 默认响应数据格式. <br>
 * Date: 2016/4/1 18:09 <br>
 * Author: ysj
 */
@Table(name = "lmc_response")
public class BaseData<T> extends DataEntity<BaseData> {

    @Column(name = "status")
    public String status;

    @Column(name = "errorCode")
    public String errorCode;

    @Column(name = "errorMsg")
    public String errorMsg;

    @Column(name = "data")
    public T data;
}
