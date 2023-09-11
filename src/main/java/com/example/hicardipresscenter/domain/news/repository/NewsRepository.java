package com.example.hicardipresscenter.domain.news.repository;

import com.example.hicardipresscenter.domain.news.document.News;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NewsRepository extends MongoRepository<News, ObjectId> {

}
