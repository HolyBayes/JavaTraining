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
    private static final String from = null;
    @Range(min = MIN, max = MAX)
    private static final int value = 0;

    @NotNull
    @Pattern(regexp = ".+\\.(com|ru)")
    @Size(max = MAX)
    private static final String to = null;

    @Size(max = MAX)
    private static final String cc = null;

    @Size(min = MIN)
    @Pattern(regexp = ".+\\.(com|ru)")
    private static final String bcc = null;
}
