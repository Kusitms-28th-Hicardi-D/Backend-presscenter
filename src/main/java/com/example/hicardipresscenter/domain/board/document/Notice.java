package com.example.hicardipresscenter.domain.board.document;

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
@Document(collection = "notice")
@SuperBuilder
public class Notice extends BaseDocument {

    @Id
    private ObjectId id;
    private long num;

    private String title;
    private String content;
    private String writer;
    private long date;
    private long viewCount;

    public static Notice createNotice(String title, String content, String writer, long date, long num){
        return Notice.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .date(date)
                .num(num)
                .viewCount(0)
                .build();
    }

    public void addViewCount(){
        this.viewCount++;
    }
}
