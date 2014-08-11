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

    @NotBlank
    @Pattern(regexp = ".+\\.(com|ru)")
    public String from;

    @NotNull
    @Pattern(regexp = ".+\\.(com|ru)")
    private String to;

    @Size(min = 1 , max = 10)
    private String cc;

    @Size(min = 1)
    @Pattern(regexp = ".+\\.(com|ru)")
    private String bcc;

    public MailMessage() {

    }


}
