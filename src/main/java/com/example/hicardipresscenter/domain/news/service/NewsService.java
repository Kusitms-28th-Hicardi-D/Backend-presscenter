package com.example.hicardipresscenter.domain.news.service;

import com.example.hicardipresscenter.domain.news.document.News;
import com.example.hicardipresscenter.domain.news.document.Subscribe;
import com.example.hicardipresscenter.domain.news.dto.req.NewsCreateRequestDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsCreateResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindAllResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsSubscribeResponseDto;
import com.example.hicardipresscenter.domain.news.repository.NewsRepository;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final MongoTemplate mongoTemplate;
    private final EmailService emailService;

    public BaseResponseDto<NewsCreateResponseDto> createNews(NewsCreateRequestDto requestDto) {
        News news = News.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writer(requestDto.getWriter())
                .image(requestDto.getImage())
                .build();

        mongoTemplate.insert(news);

        List<Subscribe> subscribes = mongoTemplate.findAll(Subscribe.class, "subscribe");

        try {
            for (Subscribe subscribe : subscribes) {
                emailService.sendSimpleMessage(subscribe.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BaseResponseDto<>(NewsCreateResponseDto.of(
                news.getTitle(),
                news.getContent(),
                news.getWriter(),
                news.getImage()
        ));
    }

    public BaseResponseDto<NewsFindResponseDto> findNews(ObjectId id) {
        Query query = new Query(Criteria.where("_id").is(id));

        News news = mongoTemplate.findOne(query, News.class, "news");

        if(news == null)
            return new BaseResponseDto<>(null);

        return new BaseResponseDto<>(NewsFindResponseDto.of(
                news.getTitle(),
                news.getContent(),
                news.getWriter(),
                news.getImage(),
                news.getCreatedDate(),
                news.getModifiedDate()
        ));
    }

    public BaseResponseDto<NewsFindAllResponseDto> findAllNews() {
        return new BaseResponseDto<>(NewsFindAllResponseDto.of(
                newsRepository.findAll()
        ));
    }

    public BaseResponseDto<NewsSubscribeResponseDto> subscribeNews(String email) {
        Subscribe subscribe = Subscribe.builder()
                .email(email)
                .build();

        mongoTemplate.insert(subscribe);

        return new BaseResponseDto<>(NewsSubscribeResponseDto.of(
                subscribe.getEmail()
        ));
    }
}
