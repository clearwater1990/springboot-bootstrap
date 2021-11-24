package com.example.demo.common;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/24 10:01 上午
 */
public enum Status {

    SUCCESS(0, "success", "成功"),
    TASK_HAS_EXIST(10111, "task is exist", "抽签任务存在，请勿重复"),
    TASK_NOT_EXIST(10101, "task not exist", "抽签不存在"),
    TASK_DRAW_EXIST(10102, "task draw exist", "抽签已完成"),
    TEAM_NAME_NULL(10103, "team name is null", "队伍名称为空"),
    TEAM_MEMBER_LESS(10104, "members less than 3", "队伍成员少于3个"),

    INTERNAL_SERVER_ERROR_ARGS(10000, "Internal Server Error: {0}", "服务端异常: {0}"),

    REQUEST_PARAMS_NOT_VALID_ERROR(10001, "request parameter {0} is not valid", "请求参数[{0}]无效");

    private final int code;
    private final String enMsg;
    private final String zhMsg;

    private Status(int code, String enMsg, String zhMsg) {
        this.code = code;
        this.enMsg = enMsg;
        this.zhMsg = zhMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
            return this.zhMsg;
        } else {
            return this.zhMsg;
        }
    }
}
