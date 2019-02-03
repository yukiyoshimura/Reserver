package com.reserver.web.controller;

import com.reserver.web.repository.ScheduleRepository;
import com.reserver.web.util.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.List;


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

    @GetMapping("schedule/import")
    public String  ImportCsv() {
        System.out.println("-----start ImportCsv");
        File file = new File("/Applications/develop/workspace/Reserver/sample.csv");
        List<String[]> ReadRecords = CsvUtils.ImportCsv(file);

        System.out.println("aaa");
        return "schedule/index";

    }
}
