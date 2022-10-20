package com.paipai.paipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paipai.paipai.entity.Auction;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.entity.Ptypes;
import com.paipai.paipai.service.IAuctionService;
import com.paipai.paipai.service.IDealrecordService;
import com.paipai.paipai.service.IHuiyuanService;
import com.paipai.paipai.service.IPtypesService;
import com.paipai.paipai.util.Constant;
import com.paipai.paipai.util.R;
import com.paipai.paipai.util.WebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@RestController
@RequestMapping("/paipai/auction")
public class AuctionController {
    @Resource
    private IAuctionService auctionService;
    @Resource
    private IPtypesService ptypesService;
    @Resource
    private IHuiyuanService huiyuanService;
    @Resource
    private IDealrecordService dealrecordService;


    @PostMapping("addAuction")
    public R addAuction(Auction auction, MultipartFile file) {
        String filename = file.getOriginalFilename();
        assert filename != null;
        String prfex = filename.substring(filename.lastIndexOf("."));
        String pic = UUID.randomUUID() + prfex;
        File file1 = new File("D:\\Developer\\HuaQingYuanJian\\software\\nginx\\html\\upload\\img", pic);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        auction.setGpic(pic);
        auction.setCflag(1);
        auctionService.save(auction);
        return R.success("成功").add("file", file);
    }

    @GetMapping("getAll")
    public R getAll(Integer no, Integer size) {
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("state", Constant.AUCTION_STATE_WAIT);
        Page auctions = auctionService.page(new Page<>(no, size), wrapper);
        List<Ptypes> ptypesList = ptypesService.list();
        List<Huiyuan> huiyuanList = huiyuanService.list();

        List<Auction> list = auctions.getRecords();

        for (Auction ac : list) {
            for (Ptypes p : ptypesList) {
                if (p.getTid().equals(ac.getTid())) {
                    ac.setPtypes(p);
                    break;
                }
            }
        }
        for (Auction ac : list) {
            for (Huiyuan h : huiyuanList) {
                if (h.getHid().equals(ac.getCreaterid())) {
                    ac.setHuiyuan(h);
                    break;
                }
            }
        }

        return R.success(auctions);

    }

    @PostMapping("check")
    public R check(Integer id, Integer flag) {
        UpdateWrapper<Auction> wrapper = new UpdateWrapper<>();
        wrapper.eq("aid", id);
        if (flag == 1) {
            wrapper.set("state", Constant.AUCTION_STATE_AUDIT);
        }
        if (flag == 0) {
            wrapper.set("state", Constant.AUCTION_STATE_REFUSE);
        }
        auctionService.update(wrapper);
        return R.success("审核成功");
    }

    @PostMapping("del")
    public R del(Integer id) {
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("aid", id);
        auctionService.remove(wrapper);
        return R.success("删除成功");
    }

    @GetMapping("search")
    public R search(String item) {
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.like("gname", item);
        List<Auction> auctionList = auctionService.list(wrapper);
        List<Ptypes> ptypesList = ptypesService.list();
        List<Huiyuan> huiyuanList = huiyuanService.list();

        for (Auction ac : auctionList) {
            for (Ptypes p : ptypesList) {
                if (p.getTid().equals(ac.getTid())) {
                    ac.setPtypes(p);
                    break;
                }
            }
        }
        for (Auction ac : auctionList) {
            for (Huiyuan h : huiyuanList) {
                if (h.getHid().equals(ac.getCreaterid())) {
                    ac.setHuiyuan(h);
                    break;
                }
            }
        }
        return R.success(auctionList);
    }

    @GetMapping("getAllByStateInfo")
    public R getAllActionningInfo(Integer pageNum, Integer state) {
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("state", state);
        Page<Auction> page = auctionService.page(new Page<>(pageNum, 10), wrapper);
        return R.success(page);
    }

    @GetMapping("getAllByStateAndTidAndGnameInfo")
    public R getAllByStateAndTidAndGnameInfo(Integer pageNum, Integer state, Integer tid, String gname) {
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("state", state);
        if (tid != 0) {
            wrapper.eq("tid", tid);
        }
        if (!gname.equals("")) {
            wrapper.like("gname", gname);
        }
        Page<Auction> auctionPage = auctionService.page(new Page<>(pageNum, 10), wrapper);
        return R.success(auctionPage);

    }

    @GetMapping("like")
    public R addLike(Integer aid) {
        UpdateWrapper<Auction> wrapper = new UpdateWrapper<>();
        wrapper.setSql("gzan = gzan + 1");
        wrapper.eq("aid", aid);
        auctionService.update(wrapper);
        return R.success("ok");

    }

    @GetMapping("getById")
    public R getById(Integer aid) {
        Auction auction = auctionService.getById(aid);
        return R.success(auction);
    }

    @PostMapping("updateNprice")
    public R updateNprice(@RequestBody Auction auction) throws Exception {
        Huiyuan huiyuan = huiyuanService.getById(auction.getHid());
        Auction auction1 = auctionService.getById(auction.getAid());
        if (huiyuan.getHyue() < auction1.getAbmoney()) {
            return R.error("余额不足，请充值再竞拍！");
        } else {
            auctionService.auctionning(auction);
            WebSocketServer.sendMessage(auction.getAid(), auction.getNprice(), auction.getHid());
            return R.success("竞拍成功");
        }
    }

    @GetMapping("getAllByCreateridAndStatesInfo")
    public R getAllByCreateridAndStatesInfo(String id, @RequestParam(required = true,defaultValue = "1") Integer no) {
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("createrid", id);
        wrapper.orderByAsc("stime");
        Page<Auction> page = auctionService.page(new Page<>(no, 5), wrapper);
        return R.success(page);
    }

    @GetMapping("getAllByMultiple")
    public R getAllByMultiple(String id,@RequestParam(required = true,defaultValue = "1") Integer no,Integer[] list) {

        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("createrid",id);
        if (list != null) {
            wrapper.in("state",list);
        }
        wrapper.orderByAsc("stime");
        Page<Auction> auctionPage = auctionService.page(new Page<>(no, 5), wrapper);
        return R.success(auctionPage);

    }
    @GetMapping("pay")
    public R pay(Integer id) {
        dealrecordService.pay(id);
        return R.success("付款成功");
    }
}

