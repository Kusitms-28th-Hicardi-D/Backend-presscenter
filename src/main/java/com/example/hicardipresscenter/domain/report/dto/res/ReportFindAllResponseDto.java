package com.example.hicardipresscenter.domain.report.dto.res;

import com.example.hicardipresscenter.domain.report.document.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReportFindAllResponseDto {
    private List<Report> reportList;
}
