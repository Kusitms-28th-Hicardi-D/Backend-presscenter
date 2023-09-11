package com.example.hicardipresscenter.domain.report.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReportGetResponseDto {

    private String lastBuildDate;
    private String total;
    private String start;
    private String display;

    private List<Item> items;

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Item {
        private String title;
        private String originallink;
        private String link;
        private String description;
        private String pubDate;
    }

}
