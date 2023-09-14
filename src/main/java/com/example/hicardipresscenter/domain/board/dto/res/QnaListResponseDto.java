package com.example.hicardipresscenter.domain.board.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QnaListResponseDto {

    private String id;

    private long num;
    private String title;
    private String createdDate;
    private long viewCount;
}
