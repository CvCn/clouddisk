package com.moxiaowei.cloud.clouddisk.util;

public class IdUtils {

    private static long nextNumber = 0L;

    private static long lastTimeStamp = 0L;

    public static String getId() {
        return System.currentTimeMillis() + "_" + getNext();
    }

    private static synchronized long getNext() {
        long curr = System.currentTimeMillis();
        if (curr == lastTimeStamp) {
            nextNumber++;
        } else {
            nextNumber = 0L;
            lastTimeStamp = curr;
        }
        return nextNumber;
    }

//    public static void main(String[] args) {
//        new Thread(()->{
//            for(int i = 0; i<10; i++) {
//                System.out.println("线程1："+getId());
//            }
//        }).start();
//        new Thread(()->{
//            for(int i = 0; i<10; i++) {
//                System.out.println("线程2："+getId());
//            }
//        }).start();
//        new Thread(()->{
//            for(int i = 0; i<10; i++) {
//                System.out.println("线程3："+getId());
//            }
//        }).start();
//    }
}
