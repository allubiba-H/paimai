package com.paipai.paipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paipai.paipai.entity.Moneyrecord;
import com.paipai.paipai.service.IMoneyrecordService;
import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/paipai/moneyrecord")
public class MoneyrecordController {

    @Resource
    private IMoneyrecordService moneyrecordService;

    @GetMapping("showMoneyRecord")
    public R showmoneyrecord(String hid, Integer page) {
        QueryWrapper<Moneyrecord> wrapper = new QueryWrapper<>();
        wrapper.eq("hid", hid);
        Page list = moneyrecordService.page(new Page<>(page, 10), wrapper);
        return R.success(list);
    }

    @GetMapping("getMoneyRecord")
    public R getAllMOneyRecord(Integer page, Integer size) {
        Page<Moneyrecord> moneyrecordPage = moneyrecordService.page(new Page<>(page, size));
        return R.success(moneyrecordPage);
    }

    @DeleteMapping("del")
    public R del(Integer id) {
        moneyrecordService.removeById(id);
        return R.success("删除成功");
    }

    @PutMapping("saveOrUpdate")
    public R addOrUpdate(Moneyrecord record) {
        moneyrecordService.saveOrUpdate(record);
        return R.success("更新成功");
    }

    @GetMapping("search")
    public R getByName(String item) {
        QueryWrapper<Moneyrecord> wrapper = new QueryWrapper<>();
        wrapper.like("hname", item);
        List<Moneyrecord> list = moneyrecordService.list(wrapper);
        return R.success(list);
    }

    @GetMapping("showMoneyRecordByTime")
    public R showMoneyRecordByTime(Integer hid, String stime, @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageno, String etime, Integer[] list) {
        QueryWrapper<Moneyrecord> wrapper = new QueryWrapper<>();
        wrapper.eq("hid", hid);
        if (!"".equals(stime)) {
            wrapper.gt("cjtime", stime + " 00:00:00");
        }
        if (!"".equals(etime)) {
            wrapper.lt("cjtime", etime + " 23:59:59");
        }
        if (null != list) {
            wrapper.in("mtype", list);
        }
        Page<Moneyrecord> moneyrecordPage = moneyrecordService.page(new Page<>(pageno, 5), wrapper);
        return R.success(moneyrecordPage);
    }
}

