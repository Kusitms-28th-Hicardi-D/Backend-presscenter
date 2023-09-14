package com.example.hicardipresscenter.global;

import lombok.Getter;

@Getter
public enum MessageType {
    NEWS("news", "하이카디 신규 소식 알림", "하이카디에 대한 새로운 소식입니다.", "'http://localhost:8082/news'"),
    QNA("qna", "하이카디 Q&A 답변 알림", "하이카디 Q&A 대한 답변입니다.", "'http://localhost:8082/qna'");

    private final String name;
    private final String subject;
    private final String content;
    private final String link;

    MessageType(String name, String subject, String content, String link) {
        this.name = name;
        this.subject = subject;
        this.content = content;
        this.link = link;
    }
}
