package com.paipai.paipai.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paipai.paipai.entity.Ptypes;
import com.paipai.paipai.service.IPtypesService;
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
@RequestMapping("/paipai/ptypes")
public class PtypesController {
    @Resource
    private IPtypesService ptypesService;

    @PostMapping("selectList")
    public R getAll() {
        return R.success(ptypesService.list());
    }

    @PostMapping("insert")
    public R insert(Ptypes ptypes) {
        ptypesService.save(ptypes);
        return R.success("添加成功");
    }
    @GetMapping("getAll")
    public R getAll(@RequestParam(required = true,defaultValue = "1") Integer no, Integer size) {
        return R.success(ptypesService.page(new Page<>(no,size)));
    }
}

