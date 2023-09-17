package com.example.hicardipresscenter.domain.news.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NewsFindAllResponseDto {
    private String id;
    private String title;
    private String writer;
    private String createdDate;
    private String image;
}
