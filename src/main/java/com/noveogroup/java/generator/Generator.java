package com.noveogroup.java.generator;

import java.util.Stack;

/**
 * Created by artem on 24.07.14.
 */
public class Generator {
    public static int CORCOUNT=50;
    public static int NCORCOUNT=30;
    public static Stack<Object> generate(){
        MailMessage nonCorrMailMessage=new MailMessage("","asd","asd","asd");//Non-correct MailMessage
        MailMessage corrMailMessage=new MailMessage("asd","asd","asd","asd");
        Stack<Object> stack=new Stack<Object>();
        for(int i=0;i<CORCOUNT;i++){
            stack.push(corrMailMessage);
        }
        for(int i=0;i<NCORCOUNT;i++){
            stack.push(nonCorrMailMessage);
        }
        return stack;
    }
}
