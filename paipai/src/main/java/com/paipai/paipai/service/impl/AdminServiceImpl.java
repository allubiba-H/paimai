package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Admin;
import com.paipai.paipai.mapper.AdminMapper;
import com.paipai.paipai.service.IAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
