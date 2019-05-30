package com.moxiaowei.cloud.clouddisk.controller;

import com.github.pagehelper.PageInfo;
import com.moxiaowei.cloud.clouddisk.pojo.CloudRecord;
import com.moxiaowei.cloud.clouddisk.service.CloudRecordService;
import com.moxiaowei.cloud.clouddisk.util.IdUtils;
import com.moxiaowei.cloud.clouddisk.util.SplitFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "page/index.html";
    }

    @Value("${file.path}")
    public String path;

    @Autowired
    private CloudRecordService cloudRecordService;

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile data, int total, String name, int index, String id){
        try{
//            System.out.println(data.getSize());
            File file = new File(path + "/" + id);
            if(!file.exists()){
                file.mkdirs();
            }
            File upload = new File(file.getAbsolutePath() + "/" + index + ".data");
            data.transferTo(upload);
//            System.out.println(upload.getAbsoluteFile());
//            SplitFile.multipartFileToFile(data, file.getAbsolutePath() + "/" + index + ".data");

            if(index == total - 1){
                SplitFile.mergeFile(file, name, total);
                CloudRecord cr = new CloudRecord();
                cr.setFileName(name);
                cr.setId(Long.valueOf(id));

                File size = new File(path + "/" + id + "/" + name);
                cr.setSize(size.length());
                this.cloudRecordService.add(cr);
                System.out.println("complete!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "200";
    }

    @RequestMapping("/getId")
    @ResponseBody
    public String getId(){
        return IdUtils.getId();
    }

    @RequestMapping("/get")
    @ResponseBody
    public PageInfo<CloudRecord> get(int pageNum, int pageSize){
        return this.cloudRecordService.get(pageNum, pageSize);
    }


    @RequestMapping("/delete")
    @ResponseBody
    public int delete(long id){
        return this.cloudRecordService.delete(id);
    }

}
