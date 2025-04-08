/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:10:24
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 10:09:08
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.mapper;
import com.example.model.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    /**
     * CREATE TABLE IF NOT EXISTS calendar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    details VARCHAR(255),
    checked BOOLEAN NOT NULL,
    important BOOLEAN NOT NULL,
    createTime DATETIME NOT NULL,
    expireTime DATETIME,
    updateTime DATETIME NOT NULL,
    createDate DATE NOT NULL,
    userName VARCHAR(255) NOT NULL);
     */
    @Select("SELECT * FROM calendar WHERE userName = #{userName} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findAll(@Param("userName") int userName);

    @Select("SELECT * FROM calendar WHERE id = #{id}")
    Task findById(@Param("id") int id);

    @Select("SELECT * FROM calendar WHERE createDate = #{date} AND userName = #{userName} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findByDate(@Param("date") String date, @Param("userName") String userName);

    @Select("SELECT * FROM calendar WHERE createTime BETWEEN #{startDate} AND #{endDate} AND userName = #{userName} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userName") String userName);


    @Update("UPDATE calendar SET title = #{task.title}, details = #{task.details}, updateTime = #{task.updateTime}, expireTime = #{task.expireTime}, checked = #{task.checked}, important = #{task.important} WHERE id = #{id}")
    int update(@Param("task") Task task, @Param("id") int id);

    @Insert("INSERT INTO calendar (title, details, checked, important, createTime, expireTime, updateTime, createDate, userName) VALUES (#{task.title}, #{task.details}, #{task.checked}, #{task.important}, #{task.createTime}, #{task.expireTime}, #{task.updateTime}, #{task.createDate}, #{userName})")
    @Options(useGeneratedKeys = true, keyProperty = "task.id")
    int insert(@Param("task") Task task, @Param("userName") String userName);

    @Delete("DELETE FROM calendar WHERE id = #{id} AND userName = #{userName}")
    int delete(@Param("id") int id, @Param("userName") String userName);
    
    
}
