package com.example.hicardipresscenter.domain.board.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NoticeResponseDto {

    private String title;
    private String content;
    private String date;
    private String writer;
}
