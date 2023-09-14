package com.example.hicardipresscenter.domain.board.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QnaResponseDto {

    private String title;
    private String email;
    private String writer;
    private String createdAt;
    private String question;
    private String answer;
}
