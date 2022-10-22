package com.paipai.paipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.entity.Moneyrecord;
import com.paipai.paipai.service.IHuiyuanService;
import com.paipai.paipai.service.IMoneyrecordService;
import com.paipai.paipai.util.Constant;
import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/paipai/huiyuan")
public class HuiyuanController {
    @Resource
    private IHuiyuanService huiyuanService;
    @Resource
    private IMoneyrecordService moneyrecordService;

    @PostMapping("login/{hid}/{hpwd}")
    public R login(@PathVariable("hid") String hid, @PathVariable("hpwd") String hpwd, HttpSession session) {
        QueryWrapper<Huiyuan> wrapper = new QueryWrapper<>();
        wrapper.eq("hid", hid);
        Huiyuan huiyuan = huiyuanService.getOne(wrapper);
        if (null == huiyuan) {
            return R.error("没有此用户");
        } else {
            if (hpwd.equals(huiyuan.getHpwd())) {
                session.setAttribute("user", hid);
                return R.success("登录成功");
            } else {
                return R.error("密码错误");
            }
        }
    }

    @PostMapping("register")
    public R register(@RequestBody Huiyuan huiyuan) {
        huiyuanService.save(huiyuan);
        return R.success("注册成功");
    }

    @GetMapping("validHid")
    public R validHid(String hid) {
        QueryWrapper<Huiyuan> wrapper = new QueryWrapper<>();
        wrapper.eq("hid", hid);
        Huiyuan huiyuan = huiyuanService.getOne(wrapper);
        if (null == huiyuan) {
            return R.success("可用id");
        } else {
            return R.error("id重复");
        }
    }

    @GetMapping("showData")
    public R show(String hid) {
        QueryWrapper<Huiyuan> wrapper = new QueryWrapper<>();
        wrapper.eq("hid", hid);
        Huiyuan huiyuan = huiyuanService.getOne(wrapper);
        return R.success(huiyuan);
    }

    @PostMapping("updateHuiYuan")
    public R update(@RequestBody Huiyuan huiyuan) {
        QueryWrapper<Huiyuan> wrapper = new QueryWrapper<>();
        wrapper.eq("hid", huiyuan.getHid());
        huiyuanService.update(huiyuan, wrapper);
        return R.success("修改成功");
    }

    @GetMapping("updatePwd")
    public R updatepwd(String hid, String newPwd) {
        Huiyuan huiyuan = huiyuanService.getById(hid);
        huiyuan.setHpwd(newPwd);
        huiyuanService.updateById(huiyuan);
        return R.success("修改成功");
    }

    @PostMapping("chargeMoney")
    public R updatemoney(String hid, String mbackup, Integer hyue, String hname) {
        huiyuanService.chargeMoney(hid, hyue);
        Moneyrecord moneyrecord = new Moneyrecord();
        moneyrecord.setHid(hid);
        moneyrecord.setMbackup(mbackup);
        moneyrecord.setCjmoney(hyue);
        moneyrecord.setHname(hname);
        moneyrecord.setMtype(Constant.CHARGE_MONEY);
        moneyrecordService.UpdateRecord(moneyrecord);
        return R.success("充值成功");
    }

    @PostMapping("extractMoney")
    public R extractMoney(String hid, String mbackup, Integer hyue, String hname) {
        huiyuanService.extractMoney(hid, hyue);
        Moneyrecord moneyrecord = new Moneyrecord();
        moneyrecord.setHid(hid);
        moneyrecord.setMbackup(mbackup);
        moneyrecord.setCjmoney(hyue);
        moneyrecord.setHname(hname);
        moneyrecord.setMtype(Constant.EXTRACT_MONEY);
        moneyrecordService.UpdateRecord(moneyrecord);
        return R.success("体现成功");
    }

    @GetMapping("getList")
    public R getAll(Integer page, Integer size) {
        Page<Huiyuan> huiyuanPage = huiyuanService.page(new Page<>(page, size));
        return R.success(huiyuanPage);
    }
    @GetMapping("search")
    public R search(String item) {
        QueryWrapper<Huiyuan> wrapper = new QueryWrapper<>();
        wrapper.like("hname",item);
        List<Huiyuan> list = huiyuanService.list(wrapper);
        return R.success(list);
    }


}


