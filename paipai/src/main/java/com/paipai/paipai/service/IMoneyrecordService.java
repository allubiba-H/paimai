package com.paipai.paipai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paipai.paipai.entity.Moneyrecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
public interface IMoneyrecordService extends IService<Moneyrecord> {

    void UpdateRecord(Moneyrecord moneyrecord);

    List<Moneyrecord> getByTimeOrType(Map map);

}
