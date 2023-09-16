package com.example.hicardipresscenter.domain.board.dto.req;

import com.example.hicardipresscenter.domain.board.document.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCreateRequestDto {

    private String title;
    private String content;
    private String writer;
    private List<Notice.File> files;
}
