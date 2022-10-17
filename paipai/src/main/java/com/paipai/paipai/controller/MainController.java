package com.paipai.paipai.controller;

import com.paipai.paipai.service.IHuiyuanService;
import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/paipai/main")
public class MainController {
    @Resource
    private IHuiyuanService huiyuanService;

    @GetMapping("index")
    public R index() {
        int count = huiyuanService.count();
        return R.success("成功").add("count", count);
    }
}
