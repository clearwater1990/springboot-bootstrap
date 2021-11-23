package com.example.demo.info;

import lombok.Data;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/23 12:19 下午
 */
@Data
public class UserPosition {
    private String id;
    private String city;
    private String position;
    private Double longitude;
    private Double latitude;
}
