package com.jt.test;

import java.util.ArrayList;

public class HeapOOM {
    static class heapObject{

    }
    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        while (true){
            objects.add(new heapObject());
        }
    }


}
