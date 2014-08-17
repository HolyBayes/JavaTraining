package com.noveogroup.java.generator;

import com.noveogroup.java.validator.NotBlank;
import com.noveogroup.java.validator.NotBlankValidator.*;
import com.noveogroup.java.validator.NotNull;
import com.noveogroup.java.validator.Pattern;
import com.noveogroup.java.validator.Size;

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
