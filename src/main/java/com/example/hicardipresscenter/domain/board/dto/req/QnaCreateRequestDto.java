package com.example.hicardipresscenter.domain.board.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QnaCreateRequestDto {

    private String title;
    private String question;
    private String email;
    private String writer;
}
