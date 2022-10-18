package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.entity.Jingpai;
import com.paipai.paipai.mapper.AuctionMapper;
import com.paipai.paipai.mapper.HuiyuanMapper;
import com.paipai.paipai.mapper.JingpaiMapper;
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

    @Resource
    private JingpaiMapper jingpaiMapperl;
    @Override
    public void auctionning(Auction auction) throws Exception {
        //1. 修改当前竞拍者的余额和冻结金额；
        Huiyuan nowHuiyuan = huiyuanMapper.selectById(auction.getHid());
        Auction nowAuction = auctionMapper.selectById(auction.getAid());
        UpdateWrapper<Huiyuan> huiyuanUpdateWrapper = new UpdateWrapper<>();
        huiyuanUpdateWrapper.eq("hid",nowHuiyuan.getHid());
        huiyuanUpdateWrapper.setSql("hyue = hyue - " + auction.getAbmoney());
        huiyuanUpdateWrapper.setSql("hicemoney = hicemoney + " + auction.getAbmoney());
        huiyuanMapper.update(null,huiyuanUpdateWrapper);
        //2. 检查是否有上一个竞拍者。如果有的话，退还保证金；
        if (!nowAuction.getHid().isEmpty()) {
            Huiyuan beforeHuiyuan = huiyuanMapper.selectById(nowAuction.getHid());
            UpdateWrapper<Huiyuan> huiyuanUpdateWrapper1 = new UpdateWrapper<>();
            huiyuanUpdateWrapper1.eq("hid",beforeHuiyuan.getHid());
            huiyuanUpdateWrapper1.set("hyue",beforeHuiyuan.getHyue() + nowAuction.getAbmoney());
            huiyuanUpdateWrapper1.set("hicemoney",beforeHuiyuan.getHicemoney() - nowAuction.getAbmoney());
            huiyuanMapper.update(null,huiyuanUpdateWrapper1);
        }
        //3.修改当前拍品的竞拍者和竞拍价------同步锁；
        UpdateWrapper<Auction> wrapper = new UpdateWrapper<>();
        wrapper.eq("aid",auction.getAid());
        wrapper.eq("nprice",nowAuction.getNprice());
        wrapper.set("hid",nowHuiyuan.getHid());
        wrapper.set("nprice",auction.getNprice());
        int result = auctionMapper.update(null,wrapper);
        if (result == 0) {
            throw new Exception("手慢了，请重新竞拍");
        }
        //4.插入一条竞拍记录；
        Jingpai jingpai = new Jingpai();
        jingpai.setAid(auction.getAid());
        jingpai.setHid(auction.getHid());
        jingpai.setJprice(auction.getNprice());
        jingpaiMapperl.insert(jingpai);
    }
}
