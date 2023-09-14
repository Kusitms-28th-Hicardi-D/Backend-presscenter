package com.example.hicardipresscenter.domain.news.document;

import com.example.hicardipresscenter.global.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "news")
@SuperBuilder
public class News extends BaseDocument {

    @Id
    private ObjectId id;

    private String title;
    private String content;
    private String writer;
    private String image;

    private List<String> files;
}
