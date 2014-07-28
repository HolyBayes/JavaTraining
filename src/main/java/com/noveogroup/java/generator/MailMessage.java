package com.noveogroup.java.generator;

import com.noveogroup.java.validator.NotNullConstraint.*;
import com.noveogroup.java.validator.PatternConstraint.*;
import com.noveogroup.java.validator.NotBlankConstraint.*;
import com.noveogroup.java.validator.SizeConstraint.*;

import java.io.Serializable;

/**
 * Created by artem on 19.07.14.
 */
public class MailMessage  implements Serializable{//POJO to generate
    @NotBlank
    @Pattern(regexp=".+\\.(com|ru)")
    public String _from;

    @NotNull
    @Pattern(regexp=".+\\.(com|ru)")
    private String _to;

    @Size(min=1,max=10)
    private String _cc;

    @Size(min=1)
    @Pattern(regexp=".+\\.(com|ru)")
    private String _bcc;

    public MailMessage(String from,String to,String cc,String bcc){
        _from=from;
        _to=to;
        _cc=cc;
        _bcc=bcc;
    }
}
