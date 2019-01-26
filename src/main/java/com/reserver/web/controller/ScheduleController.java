package com.reserver.web.controller;

import com.reserver.web.entity.ScheduleEntity;
import com.reserver.web.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @GetMapping("/schedule")
    public List<ScheduleEntity> index(Model model) {
        logger.info("---start getSchedule");
        List<ScheduleEntity> scheduleList = scheduleRepository.findAll();

        return scheduleList;
//        return "/schedule_list";


    }
}
