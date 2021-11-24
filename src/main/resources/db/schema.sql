CREATE TABLE task
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name        VARCHAR(512) NOT NULL DEFAULT '' COMMENT '任务名称',
    task_type   VARCHAR(50) NOT NULL DEFAULT '' COMMENT '任务类型',
    create_user VARCHAR(128) COMMENT '任务创建者',
    range_num   INT COMMENT '抽签任务队伍总数',
    info        varchar(2000) NOT NULL DEFAULT '' COMMENT '任务信息',
    create_time timestamp            not null comment '创建时间',
    update_time timestamp            not null comment '更新时间',
    PRIMARY KEY (id)
);

CREATE TABLE task_draw
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    task_id     BIGINT(20) NOT NULL COMMENT '关联的任务ID',
    actor       VARCHAR(512) COMMENT '实际抽签人',
    school_name VARCHAR(512) COMMENT '学校名称',
    draw_num    INT COMMENT '抽签选中的号码',
    result      varchar(2000) NOT NULL DEFAULT '' COMMENT '任务分配信息',
    create_time timestamp            not null comment '创建时间',
    update_time timestamp            not null comment '更新时间',
    PRIMARY KEY (id)
);

CREATE TABLE team
(
    team_name   VARCHAR(512) COMMENT '队伍名称',
    school_name VARCHAR(512) COMMENT '学校名称',
    PRIMARY KEY (team_name)
);