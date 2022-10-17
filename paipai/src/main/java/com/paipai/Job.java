package com.paipai;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.service.IAuctionService;
import com.paipai.paipai.util.Constant;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Job {
    @Resource
    private IAuctionService service;

    @Scheduled(cron = "0 0 8 * * ?")
    public void StartAuction() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String now = sdf.format(new Date());
        UpdateWrapper<Auction> wrapper = new UpdateWrapper<>();
        wrapper.set("state", Constant.AUCTION_STATE_AUCTIONING);
        wrapper.eq("state", Constant.AUCTION_STATE_AUDIT);
        wrapper.eq("stime", now);
        service.update(wrapper);
    }


}
