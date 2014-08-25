package com.noveogroup.java.generator;

import com.noveogroup.java.validator.*;

import java.io.Serializable;

/**
 * POJO to generate.
 * @author artem ryzhikov
 */
public class Pojo1 implements Serializable {
    @Final
    private static final int MIN = 1;
    @Final
    private static final int MAX = 15;
    @NotBlank
    @Pattern(regexp = ".+\\.(com|ru)")
    private String from;
    public void setFrom(final String from) {
        this.from = from;
    }
    public String getFrom() {
        return this.from;
    }
    @Range(min = MIN, max = MAX)
    private int value;
    public void setValue(final int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
    @NotNull
    @Pattern(regexp = ".+\\.(com|ru)")
    @Size(max = MAX)
    private String to;
    public void setTo(final String to) {
        this.to = to;
    }
    public String getTo() {
        return to;
    }
    @Size(max = MAX)
    private String cc;
    public void setCc(final String cc) {
        this.cc = cc;
    }
    public String getCc() {
        return cc;
    }
    @Size(min = MIN)
    @Pattern(regexp = ".+\\.(com|ru)")
    private String bcc;
    public void setBcc(final String bcc) {
        this.bcc = bcc;
    }
    public String getBcc() {
        return this.bcc;
    }
}
