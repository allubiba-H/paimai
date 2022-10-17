package com.paipai.paipai.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.paipai.paipai.service.IPtypesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@RestController
@RequestMapping("/paipai/ptypes")
public class PtypesController {
    @Resource
    private IPtypesService ptypesService;

    @PostMapping("selectList")
    public R getAll() {
        return R.ok(ptypesService.list());
    }

}

