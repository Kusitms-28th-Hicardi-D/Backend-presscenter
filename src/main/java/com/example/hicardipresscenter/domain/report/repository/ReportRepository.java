package com.example.hicardipresscenter.domain.report.repository;

import com.example.hicardipresscenter.domain.report.document.Report;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, ObjectId> {
}
