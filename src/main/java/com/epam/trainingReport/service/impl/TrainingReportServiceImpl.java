package com.epam.trainingReport.service.impl;

import com.epam.trainingReport.mapper.TrainingRecordMapping;
import com.epam.trainingReport.model.Months;
import com.epam.trainingReport.model.TrainingRecord;
import com.epam.trainingReport.model.TrainingReport;
import com.epam.trainingReport.model.Years;
import com.epam.trainingReport.repository.TrainingReportRepository;
import com.epam.trainingReport.service.TrainingReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


@Service
public class TrainingReportServiceImpl implements TrainingReportService {

    private final TrainingReportRepository reportRepository;
    private final TrainingRecordMapping trainingRecordMapping;
    private Logger logger = LoggerFactory.getLogger(TrainingReportServiceImpl.class);

    public TrainingReportServiceImpl(TrainingReportRepository reportRepository, TrainingRecordMapping trainingRecordMapping) {
        this.reportRepository = reportRepository;
        this.trainingRecordMapping = trainingRecordMapping;
    }

    @Override
    public TrainingReport findByUsername(String username) {
        Optional<TrainingReport> trainingReportOptional = reportRepository.findByUsername(username);
        logger.info("Finding training report by username {}", username);
        return trainingReportOptional
                .orElseGet(() -> {
                            logger.error("Training report for {} is not found. Return an empty training report", username);
                            return new TrainingReport();
                        }
                );
    }

    @Override
    public void update(TrainingRecord record) {
        int year = record.getDateAndTime().getYear();
        Month month = record.getDateAndTime().getMonth();
        reportRepository.findByUsername(record.getUsername())
                .ifPresentOrElse(report ->
                                updateSummaryDuration(report, year, month, record.getDuration().toMinutes()),
                        () -> createNewTrainingReport(month, year, record)
                );
    }

    private void updateSummaryDuration(TrainingReport trainingReport, int year, Month month, Long trainingDuration) {
        logger.info("Training report for {} is found, updating the report...", trainingReport.getUsername());
        trainingReport.getYears().stream()
                .filter(years -> years.getYear() == year)
                .findFirst()
                .ifPresentOrElse(
                        updateDurationWithYearExists(month, trainingDuration),
                        () -> createNewYearsRecord(trainingReport, year, month, trainingDuration)
                );
        logger.info("Training report for {} is updated", trainingReport.getUsername());
        reportRepository.save(trainingReport);
    }

    private void createNewYearsRecord(TrainingReport trainingReport, int year, Month month, Long trainingDuration) {
        Years newYears = new Years();
        newYears.setYear(year);
        newYears.setMonths(new ArrayList<>());
        newYears.getMonths().add(new Months(month.name(), trainingDuration));
        trainingReport.getYears().add(newYears);
    }

    private Consumer<Years> updateDurationWithYearExists(Month month, Long trainingDuration) {
        return years -> {
            List<Months> monthsList = new ArrayList<>(years.getMonths());
            Months targetMonth = monthsList.stream()
                    .filter(m -> m.getName().equals(month.name()))
                    .findFirst()
                    .orElse(null);
            if (targetMonth != null) {
                targetMonth.setSummaryDuration(targetMonth.getSummaryDuration() + trainingDuration);
            } else {
                Months newMonth = new Months(month.name(), trainingDuration);
                monthsList.add(newMonth);
            }
            years.setMonths(monthsList);
        };
    }

    private void createNewTrainingReport(Month month, int year, TrainingRecord record) {
        logger.warn("The training report for trainer " + record.getUsername() + " is not found. Creating new training report...");
        Months months = new Months(month.name(), record.getDuration().toMinutes());
        Years years = new Years(year, List.of(months));
        TrainingReport trainingReport = trainingRecordMapping.toTrainingReportWithoutYearsList(record);
        trainingReport.setYears(List.of(years));
        reportRepository.save(trainingReport);
    }
}
