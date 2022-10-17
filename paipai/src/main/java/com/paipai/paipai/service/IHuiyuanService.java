package com.paipai.paipai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paipai.paipai.entity.Huiyuan;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
public interface IHuiyuanService extends IService<Huiyuan> {

    boolean chargeMoney(String hid, Integer hyue);

    boolean extractMoney(String hid, Integer hyue);

}
