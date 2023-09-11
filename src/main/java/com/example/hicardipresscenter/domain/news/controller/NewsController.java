package com.example.hicardipresscenter.domain.news.controller;

import com.example.hicardipresscenter.domain.news.dto.req.NewsCreateRequestDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsCreateResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindAllResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsSubscribeResponseDto;
import com.example.hicardipresscenter.domain.news.service.NewsService;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping("")
    public BaseResponseDto<NewsCreateResponseDto> createNews(
            @RequestBody NewsCreateRequestDto requestDto
    ) {
        return newsService.createNews(requestDto);
    }

    @GetMapping("/{id}")
    public BaseResponseDto<NewsFindResponseDto> findNews(
            @PathVariable String id
    ) {
        return newsService.findNews(new ObjectId(id));
    }

    @GetMapping("")
    public BaseResponseDto<NewsFindAllResponseDto> findAllNews() {
        return newsService.findAllNews();
    }

    @PostMapping("/subscribe")
    public BaseResponseDto<NewsSubscribeResponseDto> subscribeNews(
            @RequestParam(name = "email") String email
    ) {
        return newsService.subscribeNews(email);
    }
}
