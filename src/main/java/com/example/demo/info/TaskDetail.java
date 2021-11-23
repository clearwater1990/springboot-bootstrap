package com.example.demo.info;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import lombok.Data;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/23 11:08 上午
 */
@Data
public class TaskDetail {
    @JSONField(name = "join_number")
    private Integer joinNumber;

    @JSONField(name = "select_number")
    private List<Integer> selectNumber;

    @JSONField(name = "win_number")
    private List<Integer> winNumber;
}
