package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Moneyrecord;
import com.paipai.paipai.mapper.MoneyrecordMapper;
import com.paipai.paipai.service.IMoneyrecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Service
public class MoneyrecordServiceImpl extends ServiceImpl<MoneyrecordMapper, Moneyrecord> implements IMoneyrecordService {

    @Override
    public void UpdateRecord(Moneyrecord moneyrecord) {
        this.baseMapper.insert(moneyrecord);
    }

    @Override
    public List<Moneyrecord> getByTimeOrType(Map map) {
        return baseMapper.getByTimeOrType(map);
    }
}
