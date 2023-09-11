package com.example.hicardipresscenter.domain.report.dto.req;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportCreateRequestDto {

    private String title;
    private String content;
    private String link;
    private String pubDate;
}
