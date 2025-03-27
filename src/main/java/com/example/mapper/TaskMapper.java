/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:10:24
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-27 16:48:50
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.mapper;
import com.example.model.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    @Select("SELECT * FROM calendar WHERE userId = #{userId} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findAll(@Param("userId") int userId);

    @Select("SELECT * FROM calendar WHERE id = #{id} AND userId = #{userId}")
    Task findById(@Param("id") int id, @Param("userId") int userId);

    @Select("SELECT * FROM calendar WHERE createDate = #{date} AND userId = #{userId} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findByDate(@Param("date") String date, @Param("userId") int userId);

    @Select("SELECT * FROM calendar WHERE createTime BETWEEN #{startDate} AND #{endDate} AND userId = #{userId} ORDER BY checked ASC, important DESC, updateTime DESC")
    List<Task> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") int userId);


    @Update("UPDATE calendar SET updateTime = #{task.updateTime}, expireTime = #{task.expireTime}, checked = #{task.checked}, important = #{task.important} WHERE id = #{id} AND userId = #{userId}")
    int update(@Param("task") Task task, @Param("id") int id, @Param("userId") int userId);

    @Insert("INSERT INTO calendar (name, checked, important, createTime, expireTime, updateTime, createDate, userId) VALUES (#{task.name}, #{task.checked}, #{task.important}, #{task.createTime}, #{task.expireTime}, #{task.updateTime}, #{task.createDate}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "task.id")
    int insert(@Param("task") Task task, @Param("userId") int userId);

    @Delete("DELETE FROM calendar WHERE id = #{id} AND userId = #{userId}")
    int delete(@Param("id") int id, @Param("userId") int userId);
    
    
}
