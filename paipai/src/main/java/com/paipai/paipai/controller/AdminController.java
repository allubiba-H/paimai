package com.paipai.paipai.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paipai.paipai.entity.Admin;
import com.paipai.paipai.service.IAdminService;
import com.paipai.paipai.util.JwtUtils;
import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
@RequestMapping("/paipai/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @GetMapping("login")
    public R login(HttpSession session, String name, String password) throws UnsupportedEncodingException {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("aname", name);
        Admin admin = adminService.getOne(wrapper);
        if (null == admin) {
            return R.error("没有此用户");
        } else {
            if (password.equals(admin.getApwd())) {
                String token = JwtUtils.getToken(admin);
                admin.setToken(token);
                adminService.updateById(admin);
                return R.success(token);
            } else {
                return R.error("密码错误");
            }
        }
    }

    @GetMapping("getInfo")
    public R get(@RequestParam String token) throws UnsupportedEncodingException {
        DecodedJWT jwt = JwtUtils.verify(token);
        List<String> audience = jwt.getAudience();
        Admin admin = adminService.getById(audience.get(0));
        return R.success(admin).add("data", audience);
    }

    @PostMapping("save")
    public R add(@RequestBody Admin admin) {
        adminService.save(admin);
        return R.success("成功");
    }

    @GetMapping("getList")
    public R getAdminList() {
        return R.success(adminService.list());
    }

    @GetMapping("getByName")
    public R getAdminByname(@RequestParam String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.like("aname", username);
        return R.success(adminService.list(wrapper));
    }

    @GetMapping("delById")
    public R delete(@RequestParam String id) {
        adminService.removeById(id);
        return R.success("删除成功");
    }

    @PostMapping("insert")
    public R addorUpdate(Admin admin) {
        System.out.println(admin);
        adminService.saveOrUpdate(admin);
        return R.success("更新成功");
    }

    @PostMapping("batchDel")
    public R batchDel(@RequestBody Integer[] arr) {
        return R.success(arr);
    }

}

