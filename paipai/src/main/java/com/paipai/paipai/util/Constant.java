package com.paipai.paipai.util;

public class Constant {
    //充值
    public static final Integer CHARGE_MONEY = 0;
    //消费
    public static final Integer USE_MONEY = 1;
    //体现
    public static final Integer EXTRACT_MONEY = 2;
    //拍品状态等待审核
    public static final Integer AUCTION_STATE_WAIT = 1;
    //拍品状态等待审核
    public static final Integer AUCTION_STATE_AUDIT = 2;
    //拍品状态等待审核
    public static final Integer AUCTION_STATE_REFUSE = 3;
    //拍品状态 竞拍中
    public static final Integer AUCTION_STATE_AUCTIONING = 4;
    //拍品状态 竞拍结束
    public static final Integer AUCTION_STATE_AUCTIONED = 5;
    //交易状态
    //未支付
    public static final Integer DEAL_STATE_UNPAIED = 1;
    //流拍
    public static final Integer DEAL_STATE_FLOW = 2;
    //支付成功
    public static final Integer DEAL_STATE_PAYIED = 3;
    //违约
    public static final Integer DEAL_STATE_DEFAULT = 4;


}
