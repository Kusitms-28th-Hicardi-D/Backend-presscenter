package com.example.hicardipresscenter.domain.news.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsCreateRequestDto {

    private String title;
    private String content;
    private String writer;
    private String image;
    private List<String> files;
}
