package com.paipai;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.entity.Dealrecord;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.service.IAuctionService;
import com.paipai.paipai.service.IDealrecordService;
import com.paipai.paipai.service.IHuiyuanService;
import com.paipai.paipai.util.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Job {
    @Resource
    private IAuctionService service;
    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private IDealrecordService dealrecordService;
    @Resource
    private IHuiyuanService huiyuanService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void startAuction() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        UpdateWrapper<Auction> wrapper = new UpdateWrapper<>();
        wrapper.set("state", Constant.AUCTION_STATE_AUCTIONING);
        wrapper.eq("state", Constant.AUCTION_STATE_AUDIT);
        wrapper.eq("stime", now);
        service.update(wrapper);
    }

    @Scheduled(cron = "0 26 9 * * ?")
    public void endAuction() throws MessagingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        //查询今天要结束的拍品
        QueryWrapper<Auction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("etime", now);
        queryWrapper.eq("state", Constant.AUCTION_STATE_AUCTIONING);
        List<Auction> auctionList = service.list(queryWrapper);
        //修改拍品表，结束时间，状态
        UpdateWrapper<Auction> wrapper = new UpdateWrapper<>();
        wrapper.set("state", Constant.AUCTION_STATE_AUCTIONED);
        wrapper.eq("state", Constant.AUCTION_STATE_AUCTIONING);
        wrapper.eq("etime", now);
        service.update(wrapper);
        //插入交易记录
        for (Auction ac : auctionList) {
            Dealrecord dealrecord = new Dealrecord();
            dealrecord.setAid(ac.getAid());
            dealrecord.setCjmoney(ac.getNprice());
            if (ac.getHid() == null) {
                dealrecord.setDid(Constant.DEAL_STATE_FLOW);
                dealrecord.setDbackup("流派");
            } else {
                dealrecord.setDid(Constant.DEAL_STATE_UNPAIED);
                dealrecord.setDbackup("未付款");
            }
            dealrecord.setGname(ac.getGname());
            dealrecord.setHid(ac.getHid());
            Huiyuan huiyuan = huiyuanService.getById(ac.getHid());
            dealrecord.setHname(huiyuan.getHname());
            dealrecordService.save(dealrecord);
            //发送邮箱
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(huiyuan.getHemail());
            messageHelper.setSubject("恭喜您，成功竞拍了卓越拍拍上的商品");
            messageHelper.setText("您已经成功竞拍"+ "<a href='http://localhost:63342/paimaisystem/paimaiclient/login.html'>进入查看</a>" + "请于三天之内付款，否则违约",true);
            javaMailSender.send(mimeMessage);
        }
    }


}
