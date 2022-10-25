package com.paipai.paipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.entity.Dealrecord;
import com.paipai.paipai.service.IAuctionService;
import com.paipai.paipai.service.IDealrecordService;
import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@RestController
@RequestMapping("/paipai/dealrecord")
public class DealrecordController {
    @Resource
    private IDealrecordService dealrecordService;
    @Resource
    private IAuctionService auctionService;

    @GetMapping("getAllByHid")
    public R getAllByHid(Integer no,String hid) throws ParseException {
        QueryWrapper<Dealrecord> wrapper = new QueryWrapper<>();
        wrapper.eq("hid",hid);
        Page<Dealrecord> dealrecordPage = dealrecordService.page(new Page<>(no, 5), wrapper);
        List<Dealrecord> dealrecords = dealrecordPage.getRecords();
        for (Dealrecord d : dealrecords) {
            Auction auction = auctionService.getById(d.getAid());
            String cjtime = d.getCjtime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date cjTime = sdf.parse(cjtime);

            Calendar c = Calendar.getInstance();
            c.setTime(cjTime);
            //加三天
            c.add(Calendar.DAY_OF_MONTH,3);
            Date cTime = c.getTime();
            String format = sdf.format(cTime);
            //给截至日期赋值
            d.setOfftime(format);
            d.setAuction(auction);
        }
        return R.success(dealrecordPage);
    }

    @GetMapping("getById")
    public R getById(Integer cjid) {
        Dealrecord dealrecord = dealrecordService.getById(cjid);
        Auction auction = auctionService.getById(dealrecord.getAid());
        dealrecord.setAuction(auction);
        return R.success(dealrecord);
    }



}

