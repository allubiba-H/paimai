package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Huiyuan;
import com.paipai.paipai.mapper.HuiyuanMapper;
import com.paipai.paipai.service.IHuiyuanService;
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
public class HuiyuanServiceImpl extends ServiceImpl<HuiyuanMapper, Huiyuan> implements IHuiyuanService {

    @Override
    public boolean chargeMoney(String hid, Integer hyue) {
        Huiyuan huiyuan = getById(hid);
        huiyuan.setHyue(hyue);
        updateById(huiyuan);
        return true;
    }

    @Override
    public boolean extractMoney(String hid, Integer hyue) {
        Huiyuan huiyuan = getById(hid);
        huiyuan.setHyue(hyue);
        updateById(huiyuan);
        return true;
    }

}
