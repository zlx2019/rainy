package com.zero.rainy.dashboard.model;

import lombok.Data;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/12/27 16:17 </p>
 */
@Data
public class MessageStatisticsDTO {
    private List<TopicInfo> topics;

    @Data
    public static class TopicInfo{
        private String name;
        private String group;
    }
}

