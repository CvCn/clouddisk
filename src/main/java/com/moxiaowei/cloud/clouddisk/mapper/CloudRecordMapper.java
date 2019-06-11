package com.moxiaowei.cloud.clouddisk.mapper;

import com.moxiaowei.cloud.clouddisk.pojo.CloudRecord;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface CloudRecordMapper {

    @Insert("insert into CloudRecord(id, fileName, size, createDate, suffix) " +
            "values(#{cr.id}, #{cr.fileName}, #{cr.size}, #{cr.createDate}, #{cr.suffix})")
    int add(@Param("cr") CloudRecord cr);

    @Select("select * from CloudRecord")
    List<CloudRecord> get();

    @Delete("delete from CloudRecord where id=#{0}")
    int delete(Long id);

    @Select("select * form CloudRecord where fileName like '%#{0}%'")
    List<CloudRecord> like(String fileName);
}
