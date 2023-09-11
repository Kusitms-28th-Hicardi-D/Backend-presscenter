package com.example.hicardipresscenter.domain.report.service;

import com.example.hicardipresscenter.domain.report.document.Report;
import com.example.hicardipresscenter.domain.report.dto.req.ReportCreateRequestDto;
import com.example.hicardipresscenter.domain.report.dto.res.ReportCreateResponseDto;
import com.example.hicardipresscenter.domain.report.dto.res.ReportFindAllResponseDto;
import com.example.hicardipresscenter.domain.report.dto.res.ReportFindResponseDto;
import com.example.hicardipresscenter.domain.report.dto.res.ReportGetResponseDto;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final MongoTemplate mongoTemplate;

    @Value("${naver.id}")
    private String apiId;

    @Value("${naver.secret}")
    private String apiSecret;

    public BaseResponseDto<ReportCreateResponseDto> createReport(ReportCreateRequestDto requestDto) {
        Report report = Report.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .link(requestDto.getLink())
                .pubDate(dateFormatter(requestDto.getPubDate()))
                .build();

        mongoTemplate.insert(report);

        return new BaseResponseDto<>(ReportCreateResponseDto.of(
                report.getTitle(),
                report.getContent(),
                report.getLink(),
                report.getPubDate()
        ));
    }

    public BaseResponseDto<ReportFindResponseDto> findReport(ObjectId id) {
        Query query = new Query(Criteria.where("_id").is(id));

        Report report = mongoTemplate.findOne(query, Report.class, "report");

        if(report == null)
            return new BaseResponseDto<>(null);

        return new BaseResponseDto<>(ReportFindResponseDto.of(
                report.getTitle(),
                report.getContent(),
                report.getLink(),
                report.getPubDate()
        ));
    }

    public BaseResponseDto<ReportFindAllResponseDto> findAllReport() {
        return new BaseResponseDto<>(ReportFindAllResponseDto.of(
                mongoTemplate.findAll(Report.class, "report")
        ));
    }

    public BaseResponseDto<ReportGetResponseDto> getReport() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", apiId);
        headers.set("X-Naver-Client-Secret", apiSecret);

        RequestEntity<Void> req = RequestEntity
                .get("https://openapi.naver.com/v1/search/news.json?query=하이카디&display=10&sort=date")
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<ReportGetResponseDto> res = restTemplate.exchange(req, ReportGetResponseDto.class);

        return new BaseResponseDto<>(res.getBody());
    }

    /*
     input -> Tue, 22 Aug 2023 06:04:00 +0900
     output -> 23. 8. 2. 오전 06:04
     */
    public String dateFormatter(String inputString) {
        String[] splitString = inputString.split(" ");
        String year = splitString[3].substring(2, 4);
        String month = splitString[2];
        String day = splitString[1];
        String hour = splitString[4].split(":")[0];
        String minute = splitString[4].split(":")[1];

        String ampm = "오전";

        if(Integer.parseInt(hour) > 12) {
            hour = Integer.toString(Integer.parseInt(hour) - 12);
            ampm = "오후";
        }

        if(Integer.parseInt(hour) < 10) {
            hour = hour.substring(1);
        }


        switch(month) {
            case "Jan":
                month = "1";
                break;
            case "Feb":
                month = "2";
                break;
            case "Mar":
                month = "3";
                break;
            case "Apr":
                month = "4";
                break;
            case "May":
                month = "5";
                break;
            case "Jun":
                month = "6";
                break;
            case "Jul":
                month = "7";
                break;
            case "Aug":
                month = "8";
                break;
            case "Sep":
                month = "9";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
        }

        return year + ". " + month + ". " + day + ". " + ampm + " " + hour + ":" + minute;
    }
}
