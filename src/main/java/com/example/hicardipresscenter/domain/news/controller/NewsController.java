package com.example.hicardipresscenter.domain.news.controller;

import com.example.hicardipresscenter.domain.news.dto.req.NewsCreateRequestDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsCreateResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindAllResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsSubscribeResponseDto;
import com.example.hicardipresscenter.domain.news.service.NewsService;
import com.example.hicardipresscenter.global.response.BasePageDto;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/presscenter/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping("")
    public BaseResponseDto<NewsCreateResponseDto> createNews(
            @RequestBody NewsCreateRequestDto requestDto
    ) {
        return newsService.createNews(requestDto);
    }

    @PostMapping("/file")
    public BaseResponseDto<?> uploadFile(
            @RequestPart MultipartFile file
    ) {
        return new BaseResponseDto<>(newsService.uploadFile(file));
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam String key,
            @RequestParam String downloadFileName
    ) {
        return newsService.downloadFile(key, downloadFileName);
    }

    @GetMapping("/{id}")
    public BaseResponseDto<NewsFindResponseDto> findNews(
            @PathVariable String id
    ) {
        return newsService.findNews(new ObjectId(id));
    }

    @GetMapping("/search")
    public BaseResponseDto<?> searchNews(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "criteria") String criteria,
            Pageable pageable
    ) {
        return new BaseResponseDto<>(new BasePageDto<>(newsService.searchNews(pageable, keyword, criteria)));
    }

    @GetMapping("")
    public BaseResponseDto<?> findAllNews(Pageable pageable) {
        return new BaseResponseDto<>(new BasePageDto<>(newsService.findAllNews(pageable)));
    }

    @PostMapping("/subscribe")
    public BaseResponseDto<NewsSubscribeResponseDto> subscribeNews(
            @RequestParam(name = "email") String email
    ) {
        return newsService.subscribeNews(email);
    }
}
