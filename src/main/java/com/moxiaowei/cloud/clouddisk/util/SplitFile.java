package com.moxiaowei.cloud.clouddisk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.SequenceInputStream;
import java.util.*;

/**
 * 功能说明:
 * 
 * @author: <a href="mailto:bylv@isoftstone.com">bylv</a>
 * @DATE:2015-9-7 @TIME: 上午09:27:43
 */
public class SplitFile {
 
    private static final int SIZE = 1024 * 1024 * 2;// 定义单个文件的大小这里采用1m
 
    public static void main(String[] args) throws Exception {
//         拆分
         File file = new File("C:\\Users\\htsd\\Desktop\\apache-tomcat-8.5.40-windows-x64.rar");
         splitFile(file);
        // 合并
//        File file = new File("C:\\Users\\htsd\\Desktop\\1559120258338_0");
//        mergeFile(file);
    }
 
    /**
     * 功能说明：合并文件
     * 
     */
    public static void mergeFile(File dir, String fileName, int splitCount) throws Exception {
//        // 读取properties文件的拆分信息
//        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".properties"));
//        File file = files[0];
//        // 获取该文件的信息
//        Properties pro = new Properties();
//        FileInputStream fis = new FileInputStream(file);
//        pro.load(fis);
//        String fileName = pro.getProperty("fileName");
//        int splitCount = Integer.valueOf(pro.getProperty("partCount"));
//        if (files.length != 1) {
//            throw new Exception(dir + ",该目录下没有解析的properties文件或不唯一");
//        }
 
        // 获取该目录下所有的碎片文件
        File[] partFiles = dir.listFiles((dir12, name) -> name.endsWith(".data"));
        ArrayList<File> treeFile = new ArrayList<>();
        Collections.addAll(treeFile, partFiles);
        Collections.sort(treeFile, (o1, o2)->{
            String o1Name = o1.getName();
            String o2Name = o2.getName();
            o1Name = o1Name.substring(0, o1Name.lastIndexOf('.'));
            o2Name = o2Name.substring(0, o2Name.lastIndexOf('.'));
            return Integer.parseInt(o1Name) - Integer.parseInt(o2Name);
        });
        // 将碎片文件存入到集合中
        List<FileInputStream> al = new ArrayList<>();
        for (int i = 0; i < splitCount; i++) {
            try {
                al.add(new FileInputStream(treeFile.get(i)));
            } catch (Exception e) {
                // 异常
                e.printStackTrace();
            }
        }
        try {
            // 构建文件流集合
            Enumeration<FileInputStream> en = Collections.enumeration(al);
            // 将多个流合成序列流
            SequenceInputStream sis = new SequenceInputStream(en);
            FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
            byte[] b = new byte[2048];
            int len = 0;
            while ((len = sis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            fos.close();
            sis.close();
            for(File f : partFiles){
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能说明：拆分文件
     */
    private static void splitFile(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            // 定义缓冲区
            byte[] b = new byte[SIZE];
            FileOutputStream fo = null;
            int len = 0;
            int count = 0;
 
            /**
             * 切割文件时，记录 切割文件的名称和切割的子文件个数以方便合并
             * 这个信息为了简单描述，使用键值对的方式，用到了properties对象
             */
            Properties pro = new Properties();
            // 定义输出的文件夹路径
            File dir = new File("C:/parfiles");
            // 判断文件夹是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 切割文件
            while ((len = fs.read(b)) != -1) {
                fo = new FileOutputStream(new File(dir, (count++) + ".part"));
                fo.write(b, 0, len);
                fo.close();
            }
            // 将被切割的文件信息保存到properties中
            pro.setProperty("partCount", count + "");
            pro.setProperty("fileName", file.getName());
            fo = new FileOutputStream(new File(dir, (count++) + ".properties"));
            // 写入properties文件
            pro.store(fo, "save file info");
            fo.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
}