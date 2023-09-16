package com.example.hicardipresscenter.domain.report.controller;

import com.example.hicardipresscenter.domain.report.dto.req.ReportCreateRequestDto;
import com.example.hicardipresscenter.domain.report.dto.res.ReportGetResponseDto;
import com.example.hicardipresscenter.domain.report.service.ReportService;
import com.example.hicardipresscenter.global.QueryUtil;
import com.example.hicardipresscenter.global.response.BasePageDto;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/presscenter/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("")
    public BaseResponseDto<?> createReport(
            @RequestBody ReportCreateRequestDto requestDto
    ) {
        return reportService.createReport(requestDto);
    }

    @GetMapping("/{id}")
    public BaseResponseDto<?> findReport(
            @PathVariable String id
    ) {
        return reportService.findReport(new ObjectId(id));
    }

    @GetMapping("")
    public BaseResponseDto<?> searchReport(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "criteria") String criteria,
            Pageable pageable
    ) {
        Query totalQuery = QueryUtil.getTotalQuery(criteria, keyword);
        long total = reportService.getTotal(totalQuery);
        Query query = QueryUtil.getQuery(pageable, criteria, keyword);
        return new BaseResponseDto<>(new BasePageDto<>(reportService.searchReport(pageable, query), total));
    }

//    @GetMapping("")
//    public BaseResponseDto<?> findAllReport(Pageable pageable) {
//        log.info("pageable: {}", pageable);
//        return new BaseResponseDto<>(new BasePageDto<>(reportService.findAllReport(pageable)));
//    }

    @GetMapping("/test")
    public BaseResponseDto<ReportGetResponseDto> test() {
        return reportService.getReport();
    }

    @PostMapping("/test")
    public void test2() {
        reportService.getReportSchedule();
    }
}
