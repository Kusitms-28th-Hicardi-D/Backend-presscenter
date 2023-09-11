package com.example.hicardipresscenter.domain.news.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NewsCreateResponseDto {
    private String title;
    private String content;
    private String writer;
    private String image;
}
