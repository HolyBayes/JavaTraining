package com.noveogroup.java.generator;

import java.util.Stack;

/**
 * Generates POJO
 * @author artem ryzhikov
 * CORCOUNT - number of correct POJO's, NCORCOUNT - of non-correct
 */
public class Generator {
    public static final int CORCOUNT = 50000;
    public static final int NCORCOUNT = 30000;
    public static Stack<Object> generate() {
        //Non-correct MailMessage
        final MailMessage nonCorrMailMessage = new MailMessage("" , "asd.ru" , "asd.com" , "asd");
        final MailMessage corrMailMessage = new MailMessage("ash.ru" , "asf.ru" , "asp.com" , "asl.ru");
        final Stack<Object> stack = new Stack<Object>();
        for (int i = 0; i < CORCOUNT; i++) {
            stack.push(corrMailMessage);
        }
        for (int i = 0; i < NCORCOUNT; i++) {
            stack.push(nonCorrMailMessage);
        }
        return stack;
    }
}
