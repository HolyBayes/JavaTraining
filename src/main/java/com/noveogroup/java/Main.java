package com.noveogroup.java;

import com.noveogroup.java.generator.MailMessage;
import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.serialize.Serializer;


import static com.noveogroup.java.generator.Generator.*;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

class Main {
    final static int QUEUE_SIZE=1000;
    public static void main(String[] args) {
        args=new String[3];
        args[0]="temp.out";
        args[1]="temp.out";
        args[2]="1";
        Stack<Object> stack = generate();
        File input = new File(args[1]);
        File output = new File(args[2]);
        Serializer serializer = new Serializer(input, output);
        try{
            for (int i = 0; i < (CORCOUNT + NCORCOUNT); i++) {
                serializer.store(stack.pop());
            }
        }
        catch (IOException e){
            System.out.print("Wrong output"+e.getMessage());
        }
        //java concurrency framework
        BlockingQueue<Object> queue=new ArrayBlockingQueue<Object>(QUEUE_SIZE);
        MyBlockingQueue<Object> myQueue=new MyBlockingQueue<Object>(QUEUE_SIZE);
        AtomicBoolean flag=new AtomicBoolean(false);
        int mode=Integer.parseInt(args[2]);
        Reader reader=new Reader(queue,myQueue,serializer,flag,mode);
        Worker worker=new Worker(queue,myQueue,flag,mode);
        new Thread(reader).start();
        new Thread(worker).start();

    }
}