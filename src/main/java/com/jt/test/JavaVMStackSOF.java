package com.jt.test;

public class JavaVMStackSOF {
    private int stackLength = 1;

    public void setStackLeak(){
        stackLength++;
        setStackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.setStackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:"+oom.stackLength);
            throw e;
        }
    }
}

