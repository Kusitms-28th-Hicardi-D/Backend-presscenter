package com.example.hicardipresscenter.domain.board.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCreateRequestDto {

    private String title;
    private String content;
    private String writer;
}
