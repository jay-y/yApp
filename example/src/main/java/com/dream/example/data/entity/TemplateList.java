package com.dream.example.data.entity;

import java.util.List;

/**
 * Description: ActivityList. <br>
 * Date: 2016/4/22 18:51 <br>
 * Author: ysj
 */
public class TemplateList extends BaseList{
    public List<Activity> list;

    public class Activity{
        public String winningNo; //中奖编号
        public String winningTime; //中奖时间
        public String awardId; //奖品ID
        public String activityId; //活动ID
        public String activityName; //活动名称
        public String activityType; //活动类型
        public String awardName; //奖品名称
        public String awardType; //奖品类型
        public String awardPicUrl; //奖品图片链接
        public String rcvStatus; //领取状态

        public String factoryId; //企业编号
        public String factoryName; //企业名称
        public String giftId; //礼品ID
        public String giftName; //礼品名称
        public String giftType; //礼品类型
        public String overdue; //过期标志
        public String endDate; //有效期
        public String logoImgSmallUrl; //奖品图片链接
        public String scoreCost; //花费积分

        public String marketName; //企业名称
        public String productName; //产品名称
    }
}
