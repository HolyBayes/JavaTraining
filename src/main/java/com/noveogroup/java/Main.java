package com.noveogroup.java;

import com.noveogroup.java.generator.MailMessage;
import com.noveogroup.java.serialize.Serializer;


import static com.noveogroup.java.generator.Generator.*;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

class Main {

    public static void main(String[] args) {
        int count=0;
        Stack<Object> stack= generate();
        File input=new File("temp.out");
        File output=new File("temp.out");
        try {
            Serializer serializer = new Serializer(input,output);
            for (int i=0;i<(CORCOUNT+NCORCOUNT);i++){
                serializer.store(stack.pop());
            }
            for(int i=0;i<(CORCOUNT+NCORCOUNT);i++){
                stack.push((Object)serializer.load());
            }
            for(int i=0;i<(CORCOUNT+NCORCOUNT);i++){
                System.out.print(((MailMessage)stack.pop())._from+"\n");
            }
        }
        catch (IOException e){
        }
        catch (ClassNotFoundException e){

        }
        System.out.print(count);
    }
}