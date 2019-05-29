package com.moxiaowei.cloud.clouddisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ClouddiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClouddiskApplication.class, args);
//        File file = new File("C:\\Users\\cvge\\Desktop\\1559138840793_0\\第一行代码 Android 第2版-郭霖.zip");
//        System.out.println(file.getName().substring(0, file.getName().lastIndexOf('.')));
    }

}
