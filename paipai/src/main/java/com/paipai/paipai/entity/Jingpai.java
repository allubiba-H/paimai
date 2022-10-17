package com.paipai.paipai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Jingpai implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "jpid", type = IdType.AUTO)
    private Integer jpid;

    private Integer aid;

    private Integer jprice;

    private String jptime;

    private String hid;

    private String jptalk;

    private String jbackup;


}
