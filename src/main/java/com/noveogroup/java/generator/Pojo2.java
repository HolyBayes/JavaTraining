package com.noveogroup.java.generator;

import com.noveogroup.java.validator.Date;
import com.noveogroup.java.validator.NotNull;
import com.noveogroup.java.validator.Range;

import java.io.Serializable;

/**
 * Created by artem on 26.08.14.
 */
public class Pojo2 implements Serializable {
    @Final
    private static final int MIN = 1;
    @Final
    private static final int MAX = 15;
    private String date;
    @Range(min = MIN , max  = MAX)
    private Integer integer;
}