package com.paipai.paipai.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Dealrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cjid", type = IdType.AUTO)
    private Integer cjid;

    private Integer aid;

    private String gname;

    private Integer cjmoney;

    @TableField(value = "cjtime", fill = FieldFill.INSERT)
    private String cjtime;

    private String hid;

    private String hname;

    private Integer did;

    private String dbackup;

    @TableField(exist = false)
    private Auction auction;

    @TableField(exist = false)
    private String offtime;

}
