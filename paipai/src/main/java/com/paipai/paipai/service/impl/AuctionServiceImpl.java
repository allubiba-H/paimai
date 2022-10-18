package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.mapper.AuctionMapper;
import com.paipai.paipai.mapper.HuiyuanMapper;
import com.paipai.paipai.service.IAuctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Service
public class AuctionServiceImpl extends ServiceImpl<AuctionMapper, Auction> implements IAuctionService {

    @Resource
    private HuiyuanMapper huiyuanMapper;
    @Resource
    private AuctionMapper auctionMapper;
    @Override
    public void auctionning(Auction auction) {
        //1. 修改当前竞拍者的余额和冻结金额；
        String hid = auction.getHid();
        Integer abmoney = auction.getAbmoney();
        UpdateWrapper<Huiyuan> huiyuanUpdateWrapper = new UpdateWrapper<>();
        huiyuanUpdateWrapper.eq("hid",hid);
        huiyuanUpdateWrapper.setSql("hyue = hyue - " + abmoney);
        huiyuanUpdateWrapper.setSql("hicemoney = hicemoney + " + abmoney);
        huiyuanMapper.update(null,huiyuanUpdateWrapper);
        //2. 检查是否有上一个竞拍者。如果有的话，退还保证金；
        if (hid.isEmpty()) {
            UpdateWrapper<Huiyuan> huiyuanUpdateWrapper1 = new UpdateWrapper<>();
            Huiyuan huiyuan = huiyuanMapper.selectById(auction.getHid());

        }
        //3.修改当前拍品的竞拍者和竞拍价------同步锁；
        //4.插入一条竞拍记录；


    }
}
