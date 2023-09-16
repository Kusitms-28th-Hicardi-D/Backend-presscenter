package com.example.hicardipresscenter.domain.board.dto.res;

import com.example.hicardipresscenter.domain.board.document.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NoticeResponseDto {

    private String title;
    private String content;
    private String date;
    private String writer;
    private List<Notice.File> files;
}
