package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/24 3:45 下午
 */
@Data
@TableName(value = "team", autoResultMap = true)
public class Team {
    @TableId
    private String teamName;
    private String schoolName;
}
