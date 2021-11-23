package com.example.demo.controller;

import com.example.demo.info.UserPosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/23 12:17 下午
 */
@Controller
public class UserController {

    @RequestMapping(value = "/mynearby")
    public String myNearby(Model model, double lon, double lat) {
        double r = 6371;//地球半径千米
        double dis = 2; //半径 单位:km
        double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
        dlng = dlng * 180 / Math.PI;//角度转为弧度
        double dlat = dis / r;
        dlat = dlat * 180 / Math.PI;
        double minlat = lat - dlat;
        double maxlat = lat + dlat;
        double minlng = lon - dlng;
        double maxlng = lon + dlng;

        List<UserPosition> list = new ArrayList<>();
        UserPosition position = new UserPosition();
        position.setId("suzhou");
        position.setPosition("回龙观新村");
        position.setCity("苏州");
        position.setLatitude(116.31077);
        position.setLongitude(40.0023);
        list.add(position);
        model.addAttribute("myinfo", list);
        return "mynearby";
    }
}
