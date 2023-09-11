package com.example.hicardipresscenter.domain.news.document;

import com.example.hicardipresscenter.global.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "subscribe")
@SuperBuilder
public class Subscribe extends BaseDocument {

    @Id
    private ObjectId id;

    private String email;
}
