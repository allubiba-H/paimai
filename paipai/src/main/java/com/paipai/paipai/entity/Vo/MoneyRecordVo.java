package com.paipai.paipai.entity.Vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class MoneyRecordVo {
    @TableId(value = "mid", type = IdType.AUTO)
    private Integer mid;

    private String hid;

    private String hname;

    @TableField(value = "cjtime", fill = FieldFill.INSERT)
    private String cjtime;

    private Integer cjmoney;

    private Integer mtype;

    private String mbackup;


}
