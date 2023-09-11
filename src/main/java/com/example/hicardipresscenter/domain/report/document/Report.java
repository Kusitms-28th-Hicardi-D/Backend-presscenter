package com.example.hicardipresscenter.domain.report.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "report")
public class Report {

    @Id
    private ObjectId id;

    private String title;
    private String content;
    private String link;
    private String pubDate;

    @Builder
    public Report(String title, String content, String link, String pubDate) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.pubDate = pubDate;
    }
}
