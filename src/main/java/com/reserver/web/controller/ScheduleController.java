package com.reserver.web.controller;

import com.reserver.web.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @GetMapping("/schedule")
    public String index(Model model) {
        logger.info("---start getSchedule");
        model.addAttribute("scheduleList", scheduleRepository.findAll());

        return "schedule/index";

    }

    @GetMapping("schedule/csv")
    public ResponseEntity<byte[]> download() throws IOException {

        var HEADER_VALUE = "attachment; filename=\"%s\"; filename*=UTF-8''%s";
        String CSVData = null;
            StringBuilder CSV = new StringBuilder();

            CSV.append("ssss");
            CSV.append("aaaa");
            CSVData =CSV.toString();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(CSVData.getBytes("MS932"));

    }
}
