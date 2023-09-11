package com.example.hicardipresscenter.domain.report.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReportCreateResponseDto {

    private String title;
    private String content;
    private String link;
    private String pubDate;
}
