package com.paipai.paipai.controller;


import com.paipai.paipai.entity.Menu;
import com.paipai.paipai.service.IMenuService;
import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/paipai/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping("getall")
    public R getAll() {
//        QueryWrapper queryWrapper = new QueryWrapper();
        return R.success(menuService.list());
    }

    @PostMapping("add")
    public R add(@RequestBody Menu menu) {
        menuService.save(menu);
        return R.success("添加成功");
    }

}

