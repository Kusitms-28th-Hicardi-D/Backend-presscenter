package com.example.hicardipresscenter.domain.news.service;

import com.example.hicardipresscenter.domain.news.document.News;
import com.example.hicardipresscenter.domain.news.document.Subscribe;
import com.example.hicardipresscenter.domain.news.dto.req.NewsCreateRequestDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsCreateResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindAllResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsFindResponseDto;
import com.example.hicardipresscenter.domain.news.dto.res.NewsSubscribeResponseDto;
import com.example.hicardipresscenter.domain.news.repository.NewsRepository;
import com.example.hicardipresscenter.global.*;
import com.example.hicardipresscenter.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final MongoTemplate mongoTemplate;
    private final EmailService emailService;
    private final S3Service s3Service;

    public BaseResponseDto<NewsCreateResponseDto> createNews(NewsCreateRequestDto requestDto) {
        News news = News.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writer(requestDto.getWriter())
                .image(requestDto.getImage())
                .files(requestDto.getFiles())
                .build();

        News saved = mongoTemplate.insert(news);

        List<Subscribe> subscribes = mongoTemplate.findAll(Subscribe.class, "subscribe");

        try {
            for (Subscribe subscribe : subscribes) {
                emailService.sendSimpleMessage(subscribe.getEmail(), MessageType.NEWS, saved.getId().toHexString());
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

    public String uploadFile(MultipartFile file) {
        return s3Service.uploadPdf(file, "news");
    }

    public ResponseEntity<byte[]> downloadFile(String key, String downloadFileName) {
        return s3Service.downloadPdf(key, downloadFileName);
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
                DateUtil.newsDateFormatter(news.getCreatedDate()),
                news.getFiles()
        ));
    }

    public Page<NewsFindAllResponseDto> findAllNews(Pageable pageable) {
        Query query = new Query()
                .with(pageable)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize());

        log.info("pageable: {}", pageable);

        List<NewsFindAllResponseDto> list = findNewsList(query);

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), News.class)
        );
    }

    public Page<NewsFindAllResponseDto> searchNews(Query query, Pageable pageable) {

        List<NewsFindAllResponseDto> list = findNewsList(query);

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), News.class)
        );
    }

    public long getTotal(Query query) {
        return mongoTemplate.find(query, News.class, "news").size();
    }

    public List<NewsFindAllResponseDto> findNewsList(Query query) {
        return mongoTemplate.find(query, News.class, "news").stream()
                .map(news -> NewsFindAllResponseDto.of(
                        news.getId().toHexString(),
                        news.getTitle(),
                        news.getWriter(),
                        DateUtil.newsDateFormatter(news.getCreatedDate()),
                        news.getImage()
                ))
                .collect(Collectors.toList());
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
