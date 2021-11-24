package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.Status;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskDraw;
import com.example.demo.info.Result;
import com.example.demo.info.TaskDrawDetail;
import com.example.demo.service.TaskService;
import com.example.demo.util.ExportCSVUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/24 9:54 上午
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Resource
    TaskService taskService;

    @PostMapping("/add")
    public Result add(@RequestParam(value = "name") String name, @RequestParam(value = "rangeNum") Integer rangeNum) {
        Task task = taskService.addTask(name, rangeNum);
        if (task != null) {
            return success(task);
        }
        return Result.error(Status.TASK_HAS_EXIST);
    }

    @PostMapping("/draw/{taskId}")
    public Result draw(@PathVariable Long taskId, @RequestBody JSONObject data) {
        String teamName = data.getString("team_name");
        List<String> members = data.getJSONArray("members").toJavaList(String.class);
        try {
            if (StringUtils.isEmpty(teamName)) {
                throw new RuntimeException("队伍名称不能为空");
            }
            TaskDrawDetail taskDraw = taskService.addTaskDraw(taskId, teamName.trim(), members);
            if (taskDraw != null) {
                return success(taskDraw);
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.error(Status.TASK_NOT_EXIST);
    }

    @GetMapping("{taskId}")
    public Result query(@PathVariable Long taskId,
                        @RequestParam(value = "teamName", required = false) String teamName,
                        @RequestParam(value = "member", required = false) String member) {
        List<TaskDrawDetail> list = taskService.queryTaskDraw(taskId, teamName, member);
        return success(list);
    }

    @GetMapping("/export/{taskId}")
    public void export(@PathVariable Long taskId, HttpServletResponse response) {
        List<Object[]> cellList = new ArrayList<>();
        List<TaskDrawDetail> list = taskService.queryTaskDraw(taskId, null, null);
        list.forEach(e -> {
            Object[] obj = new Object[]{e.getTeamName(), e.getTeamNumber(), e.getDate(), e.getLilunMember(), e.getShiyanMember(), e.getFangzhenMember(), e.getDabianMember()};
            cellList.add(obj);
        });

        String[] tableHeaderArr = {"队伍名称", "队伍顺序", "抽签时间", "理论测试", "实验考核", "虚拟仿真", "答辩"};
        String fileName = System.currentTimeMillis() + ".csv";
        byte[] bytes = ExportCSVUtil.writeCsvAfterToBytes(tableHeaderArr, cellList);
        ExportCSVUtil.responseSetProperties(fileName, bytes, response);
    }
}
