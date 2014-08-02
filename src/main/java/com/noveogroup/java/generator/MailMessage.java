package com.noveogroup.java.generator;

import com.noveogroup.java.validator.NotNullConstraint.*;
import com.noveogroup.java.validator.PatternConstraint.*;
import com.noveogroup.java.validator.NotBlankConstraint.*;
import com.noveogroup.java.validator.SizeConstraint.*;

import java.io.Serializable;

/**
 * POJO to generate
 * @author artem ryzhikov
 */
public class MailMessage  implements Serializable {
    private static Generator gen = new Generator();

    @NotBlank
    @Pattern(regexp = ".+\\.(com|ru)")
    final public String from;

    @NotNull
    @Pattern(regexp = ".+\\.(com|ru)")
    final private String to;

    @Size(min = 1 , max = 10)
    final private String cc;

    @Size(min = 1)
    @Pattern(regexp = ".+\\.(com|ru)")
    final private String bcc;

    public MailMessage() {
        this.from = gen.nextEmail();
        this.to = gen.nextEmail();
        this.cc = gen.nextEmail();
        this.bcc = gen.nextEmail();
    }


}
