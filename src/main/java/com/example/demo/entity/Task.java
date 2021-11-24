package com.example.demo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/23 10:22 上午
 */
@Data
@TableName(value = "task", autoResultMap = true)
public class Task {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String taskType;

    private String createUser;

    private Integer rangeNum;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONObject info;

    /**
     * 创建时间 插入时填充字段
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间 插入和更新时填充字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
