package com.paipai.paipai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paipai.paipai.entity.Dealrecord;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
public interface IDealrecordService extends IService<Dealrecord> {

    void pay(Integer id);

}
