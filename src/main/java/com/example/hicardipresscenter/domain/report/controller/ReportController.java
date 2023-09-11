package com.example.hicardipresscenter.domain.report.controller;

import com.example.hicardipresscenter.domain.report.dto.req.ReportCreateRequestDto;
import com.example.hicardipresscenter.domain.report.dto.res.ReportGetResponseDto;
import com.example.hicardipresscenter.domain.report.service.ReportService;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
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
    public BaseResponseDto<?> findAllReport() {
        return reportService.findAllReport();
    }

    @GetMapping("/test")
    public BaseResponseDto<ReportGetResponseDto> test() {
        return reportService.getReport();
    }
}
