package com.example.hicardipresscenter.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasePageDto<T> {

    private Iterable<T> content;
    private int totalPage;
    private boolean first;
    private boolean last;

    public BasePageDto(Page<T> page) {
        this.content = page.getContent();
        this.totalPage = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
