package com.paipai.paipai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paipai.paipai.entity.Moneyrecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Mapper
public interface MoneyrecordMapper extends BaseMapper<Moneyrecord> {

    List<Moneyrecord> getRecordInHuiyuan();

    List<Moneyrecord> getByTimeOrType(Map map);

}
