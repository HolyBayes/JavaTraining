package com.noveogroup.java.generator;

import com.noveogroup.java.validator.*;

import java.io.Serializable;

/**
 * POJO to generate
 * @author artem ryzhikov
 */
public class Pojo1 implements Serializable {
    @Final
    private static final int MIN = 1;
    @Final
    private static final int MAX = 15;
    @NotBlank
    @Pattern(regexp = ".+\\.(com|ru)")
    public String from;
    @Range(min = MIN, max = MAX)
    public int value;

    @NotNull
    @Pattern(regexp = ".+\\.(com|ru)")
    @Size(max = MAX)
    private String to;

    @Size(max = MAX)
    private String cc;

    @Size(min = MIN)
    @Pattern(regexp = ".+\\.(com|ru)")
    private String bcc;

    public Pojo1() {
    }
}
