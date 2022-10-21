package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.entity.Dealrecord;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.entity.Moneyrecord;
import com.paipai.paipai.mapper.AuctionMapper;
import com.paipai.paipai.mapper.DealrecordMapper;
import com.paipai.paipai.mapper.HuiyuanMapper;
import com.paipai.paipai.mapper.MoneyrecordMapper;
import com.paipai.paipai.service.IDealrecordService;
import com.paipai.paipai.util.Constant;
import com.paipai.paipai.util.JavaMailSenderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Service
public class DealrecordServiceImpl extends ServiceImpl<DealrecordMapper, Dealrecord> implements IDealrecordService {

    @Resource
    private DealrecordMapper dealrecordMapper;
    @Resource
    private HuiyuanMapper huiyuanMapper;
    @Resource
    private AuctionMapper auctionMapper;
    @Resource
    private MoneyrecordMapper moneyrecordMapper;

    @Override
    public void pay(Integer id) {
        //修改交易记录
        Dealrecord dealrecord = dealrecordMapper.selectById(id);
        Huiyuan huiyuan = huiyuanMapper.selectById(dealrecord.getHid());
        Auction auction = auctionMapper.selectById(dealrecord.getAid());
        UpdateWrapper<Dealrecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("cjid", id);
        updateWrapper.set("did", Constant.DEAL_STATE_PAYIED);
        dealrecordMapper.update(null, updateWrapper);
        //修改余额和冻结的金额
        //从冻结金额里边扣去商品的保证金
        //成交后的余额 = 成交前的余额 + 保证金 - 成交价格
        UpdateWrapper<Huiyuan> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("hid", dealrecord.getHid());
        updateWrapper1.setSql("hyue = hyue + " + auction.getAbmoney() + " - " + dealrecord.getCjmoney());
        updateWrapper1.setSql("hicemoney = hicemoney - " + auction.getAbmoney());
        huiyuanMapper.update(null, updateWrapper1);
        //插入一条资金记录
        Moneyrecord moneyrecord = new Moneyrecord();
        moneyrecord.setHid(huiyuan.getHid());
        moneyrecord.setCjmoney(dealrecord.getCjmoney());
        moneyrecord.setHname(huiyuan.getHname());
        moneyrecord.setMtype(Constant.USE_MONEY);
        moneyrecord.setMbackup("竞拍付款");
        moneyrecordMapper.insert(moneyrecord);
    }

    @Override
    public void beakAuction(List<Dealrecord> list) throws MessagingException {
        for (Dealrecord d : list) {
            //修改违约
            d.setDid(Constant.DEAL_STATE_DEFAULT);
            dealrecordMapper.updateById(d);
            //修改会员的冻结金额
            UpdateWrapper<Huiyuan> huiyuanUpdateWrapper = new UpdateWrapper<>();
            Integer abmoney = auctionMapper.selectById(d.getAid()).getAbmoney();
            huiyuanUpdateWrapper.setSql("hicemoney = hicemoney - " + abmoney);
            huiyuanUpdateWrapper.eq("hid",d.getHid());
            huiyuanMapper.update(null, huiyuanUpdateWrapper);
            //添加资金记录
            Moneyrecord moneyrecord = new Moneyrecord();
            moneyrecord.setHid(d.getHid());
            moneyrecord.setMtype(Constant.BreakAuction);
            moneyrecord.setHname(d.getHname());
            moneyrecord.setCjmoney(d.getCjmoney());
            moneyrecord.setMbackup("违约金额");
            moneyrecordMapper.insert(moneyrecord);
            String to = huiyuanMapper.selectById(d.getHid()).getHemail();
            String subject = "卓越拍卖提醒你";
            String text = "您没在三天之内付款，现在将扣除你的冻结金额，请进入查看<a href='http://localhost:63342/paimaisystem/paimaiclient/login.html'>卓越拍卖</a>";
            JavaMailSenderUtil javaMailSenderUtil = new JavaMailSenderUtil();
            javaMailSenderUtil.send(to,subject,text);
        }


    }
}
