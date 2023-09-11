package com.example.hicardipresscenter.domain.report.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReportFindResponseDto {

    private String title;
    private String content;
    private String link;
    private String pubDate;
}
