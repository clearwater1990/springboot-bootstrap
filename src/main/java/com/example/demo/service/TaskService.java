package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.demo.common.Constants;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskDraw;
import com.example.demo.entity.Team;
import com.example.demo.info.TaskDrawDetail;
import com.example.demo.mapper.TaskDrawMapper;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.TeamMapper;
import com.example.demo.util.NumberUtil;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/24 10:09 上午
 */
@Service
public class TaskService {

    @Resource
    TaskMapper taskMapper;

    @Resource
    TaskDrawMapper taskDrawMapper;

    @Resource
    TeamMapper teamMapper;

    public Task addTask(Task task) {
        Task check = taskMapper.selectOne(new LambdaQueryWrapper<Task>().eq(Task::getName, task.getName()));
        if (check == null) {
            task.setTaskType(Constants.TYPE_DRAW_ASSIGN);
            task.setCreateUser("倪志刚");
            taskMapper.insert(task);
            return task;
        }
        return null;

    }

    public Task addTask(String taskName, Integer rangeNum) {
        Task entity = taskMapper.selectOne(new LambdaQueryWrapper<Task>().eq(Task::getName, taskName));
        if (entity == null) {
            entity = new Task();
            entity.setName(taskName);
            entity.setRangeNum(rangeNum);
            entity.setTaskType(Constants.TYPE_DRAW_ASSIGN);
            entity.setCreateUser("倪志刚");
            taskMapper.insert(entity);
            return entity;
        }
        return null;
    }

    public void deleteTask(long id) {
        taskMapper.deleteById(id);
    }

    public List<TaskDrawDetail> queryTaskDraw(long taskId, String teamName, String member) {
        LambdaQueryWrapper<TaskDraw> queryWrapper = new LambdaQueryWrapper<TaskDraw>().eq(TaskDraw::getTaskId, taskId).orderByAsc(TaskDraw::getDrawNum);
        if (StringUtils.isNotEmpty(teamName) || StringUtils.isNotEmpty(member)) {
            queryWrapper.like(TaskDraw::getActor, teamName).or().like(TaskDraw::getResult, member);
        }
        Task task = taskMapper.selectById(taskId);
        List<TaskDraw> list = taskDrawMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(list) && task != null) {
            List<TaskDrawDetail> dataList = list.stream().map(e -> transform(e, task)).collect(Collectors.toList());
            return dataList;
        }
        return new ArrayList<>();
    }

    public TaskDrawDetail transform(TaskDraw taskDraw, Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        TaskDrawDetail taskDetail = new TaskDrawDetail();
        taskDetail.setTaskName(task.getName());
        taskDetail.setTeamName(taskDraw.getActor());

        JSONObject jsonObject = taskDraw.getResult();

        taskDetail.setLilunMember((String) jsonObject.get("0"));
        taskDetail.setShiyanMember((String) jsonObject.get("1"));
        taskDetail.setFangzhenMember((String) jsonObject.get("2"));
        taskDetail.setDabianMember((String) jsonObject.get("3"));
        taskDetail.setDate(taskDraw.getCreateTime().format(formatter));
        taskDetail.setTeamNumber(NumberUtil.transformNumber(taskDraw.getDrawNum()));
        return taskDetail;
    }


    public synchronized TaskDrawDetail addTaskDraw(long taskId, String teamName, List<String> members) {
        Task task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("抽签任务不存在");
        }
        Team team = teamMapper.selectById(teamName);
        if (team == null) {
            throw new RuntimeException("队伍名称错误,请注意大小写");
        }

        TaskDraw entity = taskDrawMapper.selectOne(new LambdaQueryWrapper<TaskDraw>().eq(TaskDraw::getTaskId, taskId).eq(TaskDraw::getActor, teamName));
        if (entity == null) {
            List<TaskDraw> list = taskDrawMapper.selectList(new LambdaQueryWrapper<TaskDraw>().eq(TaskDraw::getTaskId, taskId).select(TaskDraw::getDrawNum));
            List<Integer> used = list.stream().map(e -> e.getDrawNum()).collect(Collectors.toList());
            if (used != null && used.size() >= task.getRangeNum()) {
                throw new RuntimeException("抽签任务已结束");
            }
            Integer drawNum = NumberUtil.random(1, task.getRangeNum(), used);
            entity = new TaskDraw();
            entity.setTaskId(taskId);
            entity.setActor(teamName);
            entity.setDrawNum(drawNum);
            List<String> realMembers = members.stream().filter(e -> StringUtils.isNotBlank(e)).map(e -> e.trim()).collect(Collectors.toList());
            int size = realMembers.size();
            if (size < 3 || size > 4) {
                throw new RuntimeException("成员数不能小于3或者超过4");
            }
            List<Integer> init = Lists.newArrayList();
            for (int i = 0; i < size; i++) {
                init.add(i);
            }
            List<Integer> sort = NumberUtil.permutation(init);
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < size; i++) {
                jsonObject.put(sort.get(i) + "", realMembers.get(i));
            }
            entity.setResult(jsonObject);
            taskDrawMapper.insert(entity);
        }
        return transform(entity,task);
    }

    public void deleteTaskDraw(long id) {
        taskMapper.deleteById(id);
    }
}
