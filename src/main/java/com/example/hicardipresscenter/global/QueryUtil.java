package com.example.hicardipresscenter.global;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QueryUtil {

    public static Query getQueryWithDate(Pageable pageable, String option, String criteria, String keyword) {
        long now = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Query query;

        if (keyword.isEmpty()) {
            query = new Query()
                    .with(pageable)
                    .skip(pageable.getOffset() - 5)
                    .limit(pageable.getPageSize());
        } else {
            query = new Query(
                    Criteria.where(criteria).regex(keyword).and("date").gte(getPeriod(option)).lt(now))
                    .with(pageable)
                    .skip(pageable.getOffset() - 5)
                    .limit(pageable.getPageSize());
        }

        return query;
    }

    public static Query getQuery(Pageable pageable, String criteria, String keyword) {

        Query query;

        if (keyword.isEmpty()) {
            query = new Query()
                    .with(pageable)
                    .skip(pageable.getOffset() - 5)
                    .limit(pageable.getPageSize());
        } else {
            query = new Query(
                    Criteria.where(criteria).regex(keyword))
                    .with(pageable)
                    .skip(pageable.getOffset() - 5)
                    .limit(pageable.getPageSize());
        }

        return query;
    }

    public static Query getTotalQuery(String criteria, String keyword) {

        Query query;

        if (keyword.isEmpty()) {
            query = new Query();
        } else {
            query = new Query(
                    Criteria.where(criteria).regex(keyword));
        }

        return query;
    }


    public static long getPeriod(String option) {
        switch (option) {
            case "week":
                return Long.parseLong(LocalDateTime.now().minusWeeks(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            case "month":
                return Long.parseLong(LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            case "all":
                return Long.parseLong(LocalDateTime.now().minusYears(100).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            default:
                return 0;
        }
    }
}
