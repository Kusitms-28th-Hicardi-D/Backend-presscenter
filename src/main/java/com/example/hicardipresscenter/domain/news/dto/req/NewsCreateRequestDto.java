package com.example.hicardipresscenter.domain.news.dto.req;

import com.example.hicardipresscenter.domain.news.document.News;
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
    private List<News.File> files;
}
