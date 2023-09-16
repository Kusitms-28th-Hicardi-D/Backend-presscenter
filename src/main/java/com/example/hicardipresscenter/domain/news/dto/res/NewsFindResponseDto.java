package com.example.hicardipresscenter.domain.news.dto.res;

import com.example.hicardipresscenter.domain.news.document.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NewsFindResponseDto {
    private String title;
    private String content;
    private String writer;
    private String createdDate;
    private List<News.File> files;
}
