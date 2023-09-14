package com.example.hicardipresscenter.domain.board.controller;

import com.example.hicardipresscenter.domain.board.dto.req.NoticeCreateRequestDto;
import com.example.hicardipresscenter.domain.board.dto.req.QnaAnswerRequestDto;
import com.example.hicardipresscenter.domain.board.dto.req.QnaCreateRequestDto;
import com.example.hicardipresscenter.domain.board.service.BoardService;
import com.example.hicardipresscenter.global.response.BasePageDto;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 공지 단일 조회
    @GetMapping("/notice/{id}")
    public BaseResponseDto<?> getNotice(@PathVariable String id) {
        return new BaseResponseDto<>(boardService.findNotice(new ObjectId(id)));
    }

    // 공지 전체 조회
    @GetMapping("/notice")
    public BaseResponseDto<?> getNoticeAll(Pageable pageable) {
        return new BaseResponseDto<>(new BasePageDto<>(boardService.findAllNotice(pageable)));
    }

    // 공지 검색
    @GetMapping("/notice/search")
    public BaseResponseDto<?> searchNotice(
            @RequestParam String keyword,
            @RequestParam String criteria,
            @RequestParam String option,
            Pageable pageable) {
        return new BaseResponseDto<>(new BasePageDto<>(boardService.searchNotice(pageable, keyword, criteria, option)));
    }

    // 공지 등록
    @PostMapping("/notice")
    public BaseResponseDto<?> createNotice(
            @RequestBody NoticeCreateRequestDto requestDto
            ) {
        return new BaseResponseDto<>(boardService.createNotice(
                requestDto.getTitle(), requestDto.getContent(), requestDto.getWriter()
        ));
    }

    // Q&A 단일 조회
    @GetMapping("/qna/{id}")
    public BaseResponseDto<?> getQna(@PathVariable String id) {
        return new BaseResponseDto<>(boardService.findQna(new ObjectId(id)));
    }

    // Q&A 전체 조회
    @GetMapping("/qna")
    public BaseResponseDto<?> getQnaAll(Pageable pageable) {
        return new BaseResponseDto<>(new BasePageDto<>(boardService.findAllQna(pageable)));
    }

    // Q&A 검색
    @GetMapping("/qna/search")
    public BaseResponseDto<?> searchQna(
            @RequestParam String keyword,
            @RequestParam String criteria,
            Pageable pageable) {
        return new BaseResponseDto<>(new BasePageDto<>(boardService.searchQna(pageable, keyword, criteria)));
    }

    // Q&A 등록
    @PostMapping("/qna")
    public BaseResponseDto<?> createQna(
            @RequestBody QnaCreateRequestDto requestDto
    ) {
        return new BaseResponseDto<>(boardService.createQna(
                requestDto.getTitle(), requestDto.getQuestion(), requestDto.getEmail(), requestDto.getWriter()
        ));
    }

    // Q&A 답변
    @PatchMapping("/qna")
    public BaseResponseDto<?> answerQna(
            @RequestBody QnaAnswerRequestDto requestDto
            ) {
        return new BaseResponseDto<>(boardService.updateQna(
                new ObjectId(requestDto.getId()), requestDto.getAnswer()
        ));
    }
}
