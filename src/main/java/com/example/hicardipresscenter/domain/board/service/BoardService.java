package com.example.hicardipresscenter.domain.board.service;

import com.example.hicardipresscenter.domain.board.document.Notice;
import com.example.hicardipresscenter.domain.board.document.Qna;
import com.example.hicardipresscenter.domain.board.dto.res.NoticeListResponseDto;
import com.example.hicardipresscenter.domain.board.dto.res.NoticeResponseDto;
import com.example.hicardipresscenter.domain.board.dto.res.QnaListResponseDto;
import com.example.hicardipresscenter.domain.board.dto.res.QnaResponseDto;
import com.example.hicardipresscenter.global.EmailService;
import com.example.hicardipresscenter.global.MessageType;
import com.example.hicardipresscenter.global.Sequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final MongoTemplate mongoTemplate;
    private final EmailService emailService;

    public long getNextSeq(String collection) {
        Sequence sequence = mongoTemplate.findAndModify(
                query(where("_id").is(collection)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                Sequence.class,
                "sequences"
        );

        if (sequence == null)
            throw new RuntimeException("Unable to get sequence id for key : " + collection);

        return sequence.getSeq();
    }

    private long getPeriod(String option) {
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

    // yyyyMMdd -> yyyy.MM.dd
    private String getDateForList(String date) {
        return date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6);
    }

    public String createNotice(String title, String content, String writer) {
        long num = getNextSeq("notice");
        long date = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        Notice input = Notice.createNotice(title, content, writer, date, num);
        Notice notice = mongoTemplate.insert(input);

        return notice.getId().toHexString();
    }

    public NoticeResponseDto findNotice(ObjectId id) {
        Notice notice = mongoTemplate.findById(id, Notice.class);

        if (notice == null) {
            return new NoticeResponseDto();
        }

        addViewCount(id, "notice");

        return NoticeResponseDto.of(
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedDate(),
                notice.getWriter()
        );
    }

    public Page<NoticeListResponseDto> findAllNotice(Pageable pageable) {
        Query query = new Query()
                .with(pageable)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<NoticeListResponseDto> list = findNoticeList(query);

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), Notice.class)
        );
    }

    // 공지 검색
    // 현재부터 period 만큼의 공지사항을 검색
    public Page<NoticeListResponseDto> searchNotice(Pageable pageable, String keyword, String criteria, String option) {
        long now = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Query query = new Query(
                Criteria.where(criteria).regex(keyword).and("date").gte(getPeriod(option)).lte(now))
                .with(pageable)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize()
                );

        List<NoticeListResponseDto> list = findNoticeList(query);

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), Notice.class)
        );
    }

    public List<NoticeListResponseDto> findNoticeList(Query query) {
        return mongoTemplate.find(query, Notice.class, "notice").stream()
                .map(notice -> NoticeListResponseDto.of(
                        notice.getId().toHexString(),
                        notice.getNum(),
                        notice.getTitle(),
                        getDateForList(String.valueOf(notice.getDate())),
                        notice.getViewCount()
                ))
                .collect(Collectors.toList());
    }

    public String createQna(String title, String question, String email, String writer) {
        long num = getNextSeq("qna");
        long date = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        Qna input = Qna.createQna(title, question, email, writer, date, num);
        Qna qna = mongoTemplate.insert(input);

        return qna.getId().toHexString();
    }

    public QnaResponseDto findQna(ObjectId id) {
        Qna qna = mongoTemplate.findById(id, Qna.class);

        if (qna == null) {
            return new QnaResponseDto();
        }

        addViewCount(id, "qna");

        return QnaResponseDto.of(
                qna.getTitle(),
                qna.getEmail(),
                qna.getWriter(),
                getDateForList(String.valueOf(qna.getDate())),
                qna.getQuestion(),
                qna.getAnswer()
        );
    }

    public Page<QnaListResponseDto> findAllQna(Pageable pageable) {
        Query query = new Query()
                .with(pageable)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<QnaListResponseDto> list = findQnaList(query);

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), Qna.class)
        );
    }

    public Page<QnaListResponseDto> searchQna(Pageable pageable, String keyword, String category) {
        Query query = new Query(Criteria.where(category).regex(keyword))
                .with(pageable)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<QnaListResponseDto> list = findQnaList(query);

        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), Qna.class)
        );
    }

    public List<QnaListResponseDto> findQnaList(Query query) {
        return mongoTemplate.find(query, Qna.class, "qna").stream()
                .map(qna -> QnaListResponseDto.of(
                        qna.getId().toHexString(),
                        qna.getNum(),
                        qna.getTitle(),
                        qna.getCreatedDate(),
                        qna.getViewCount()
                ))
                .collect(Collectors.toList());
    }

    public String updateQna(ObjectId id, String answer) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("answer", answer);

        mongoTemplate.updateFirst(query, update, Qna.class);

        Qna qna = mongoTemplate.findById(id, Qna.class);

        if (qna == null) {
            return "답변 등록 실패";
        }

        try {
            emailService.sendSimpleMessage(qna.getEmail(), MessageType.QNA);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id.toHexString();
    }

    public void addViewCount(ObjectId id, String boardType) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().inc("viewCount", 1);

        if (boardType.equals("notice")) {
            mongoTemplate.updateFirst(query, update, Notice.class);
        } else if (boardType.equals("qna")) {
            mongoTemplate.updateFirst(query, update, Qna.class);
        }
    }
}
