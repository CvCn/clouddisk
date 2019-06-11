package com.moxiaowei.cloud.clouddisk.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.moxiaowei.cloud.clouddisk.mapper.CloudRecordMapper;
import com.moxiaowei.cloud.clouddisk.pojo.CloudRecord;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class CloudRecordService {

    @Autowired
    private CloudRecordMapper cloudRecordMapper;

    @Value("${file.path}")
    private String path;

    public int add(CloudRecord cr){
        cr.setCreateDate(new Date());
        String fileName = cr.getFileName();
        cr.setSuffix(fileName.substring(fileName.lastIndexOf('.') + 1));
        return this.cloudRecordMapper.add(cr);
    }

    public PageInfo<CloudRecord> get(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize).setOrderBy("createDate desc");
        List<CloudRecord> cloudRecords = this.cloudRecordMapper.get();
        return new PageInfo<>(cloudRecords);
    }

    public int delete(Long id){
        File file = new File(path + "/" + id);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件删除异常");
        }
        return this.cloudRecordMapper.delete(id);
    }
}
