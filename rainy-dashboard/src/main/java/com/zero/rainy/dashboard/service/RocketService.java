package com.zero.rainy.dashboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/12/27 16:02 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RocketService {
    private final DefaultMQAdminExt admin;

    public List<String> findAllTopic() {
        try {
            return new ArrayList<>(admin.fetchAllTopicList().getTopicList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}