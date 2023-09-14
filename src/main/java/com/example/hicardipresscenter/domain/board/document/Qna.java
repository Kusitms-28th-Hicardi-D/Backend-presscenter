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
@Document(collection = "qna")
@SuperBuilder
public class Qna extends BaseDocument {

    @Id
    private ObjectId id;
    private long num;

    private String writer;
    private String title;
    private String question;
    private String answer;
    private String email;

    private long date;
    private long viewCount;

    public static Qna createQna(String title, String question, String email, String writer, long date, long num){
        return Qna.builder()
                .title(title)
                .question(question)
                .answer("")
                .email(email)
                .writer(writer)
                .date(date)
                .num(num)
                .viewCount(0)
                .build();
    }

    public void addViewCount(){
        this.viewCount++;
    }

    public void addAnswer(String answer){
        this.answer = answer;
    }
}
