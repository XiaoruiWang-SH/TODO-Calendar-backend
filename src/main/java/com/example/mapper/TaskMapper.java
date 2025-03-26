/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:10:24
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-26 21:58:09
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.mapper;
import com.example.model.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    @Select("SELECT * FROM calendar ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findAll();

    @Select("SELECT * FROM calendar WHERE id = #{id}")
    Task findById(@Param("id") int id);

    @Select("SELECT * FROM calendar WHERE createDate = #{date} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findByDate(@Param("date") String date);

    @Select("SELECT * FROM calendar WHERE createTime BETWEEN #{startDate} AND #{endDate} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);


    @Update("UPDATE calendar SET updateTime = #{task.updateTime}, expireTime = #{task.expireTime}, checked = #{task.checked}, important = #{task.important} WHERE id = #{id}")
    int update(@Param("task") Task task, @Param("id") int id);

    @Insert("INSERT INTO calendar (name, checked, important, createTime, expireTime, updateTime, createDate) VALUES (#{task.name}, #{task.checked}, #{task.important}, #{task.createTime}, #{task.expireTime}, #{task.updateTime}, #{task.createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "task.id")
    int insert(@Param("task") Task task);

    @Delete("DELETE FROM calendar WHERE id = #{id}")
    int delete(@Param("id") int id);
    
    
}
