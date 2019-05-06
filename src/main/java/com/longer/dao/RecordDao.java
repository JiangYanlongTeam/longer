package com.longer.dao;

import com.longer.bean.RecordBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecordDao {

    /**
     * 列出所有记录信息
     */
    @Select("SELECT * FROM oplog ORDER BY id desc ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requrl", column = "requrl"),
            @Result(property = "reqAddress", column = "reqAddress"),
            @Result(property = "reqMethod", column = "reqMethod"),
            @Result(property = "reqClass", column = "reqClass"),
            @Result(property = "reqClassMethod", column = "reqClassMethod"),
            @Result(property = "reqClassMethodColumn", column = "reqClassMethodColumn"),
            @Result(property = "resContent", column = "resContent"),
            @Result(property = "totaltime", column = "totaltime"),
            @Result(property = "operator", column = "operator"),
            @Result(property = "operatedatetime", column = "operatedatetime")})
    List<RecordBean> showAllRecord();

    /**
     * 插入新记录
     * @return
     */
    @Insert("INSERT INTO oplog(requrl,reqAddress,reqMethod,reqClass,reqClassMethod,reqClassMethodColumn,resContent,totaltime,operator,operatedatetime)"
            + "VALUES(#{requrl},#{reqAddress},#{reqMethod},#{reqClass},#{reqClassMethod},#{reqClassMethodColumn},#{resContent},#{totaltime},#{operator},#{operatedatetime})")
    public void addRecord(RecordBean recordBean);

    /**
     * 查找最大ID
     */
    @Select("SELECT max(id) id FROM oplog ")
    @Results({
            @Result(property = "id", column = "id")})
    RecordBean selectMaxID();

    /**
     * 根据用户ID更新用户信息（ID唯一标识）
     */

    @Update("UPDATE oplog SET totaltime=#{totaltime},operatedatetime=#{operatedatetime},operator=#{operator},resContent=#{resContent} WHERE id=#{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "totaltime", column = "totaltime"),
            @Result(property = "operatedatetime", column = "operatedatetime"),
            @Result(property = "operator", column = "operator"),
            @Result(property = "resContent", column = "resContent")})
    public void updateRecordById(RecordBean recordBean);
}
