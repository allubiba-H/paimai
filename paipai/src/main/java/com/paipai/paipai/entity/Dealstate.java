package com.paipai.paipai.entity;

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
public class Dealstate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer did;

    private String dname;

    private String dbackup;


}
