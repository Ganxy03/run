package cn.ganxy03.run.server.impl;

import cn.ganxy03.run.entity.RunOperate;
import cn.ganxy03.run.entity.RunRecord;
import cn.ganxy03.run.repository.RunOperateRepository;
import cn.ganxy03.run.repository.RunRecordRepository;
import cn.ganxy03.run.server.RunRecordServer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class RunRecordImpl implements RunRecordServer {
    private final RunRecordRepository runRecordRepository;
    private final RunOperateRepository runOperateRepository;

    public RunRecordImpl(RunRecordRepository runRecordRepository, RunOperateRepository runOperateRepository) {
        this.runOperateRepository = runOperateRepository;
        this.runRecordRepository = runRecordRepository;
    }
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 50 23 * * ?")
//    @Scheduled(cron = "0 5 0 * * ?")
    public void Generate() {
        RunRecord target = new RunRecord();
        target.setDate(LocalDateTime.now());
        target.setStatus(0);
        runRecordRepository.save(target);
        LocalDateTime time = LocalDateTime.now();
        System.out.println("自动执行成功 " + time);
    }

    public RunRecord GetStatus() {
        return runRecordRepository.findAll().stream()
                .sorted(Comparator.comparing(RunRecord::getDate).reversed())
                .findFirst()
                .orElse(null);
    }

    public void Operate(Integer id,String operator, Integer result) {
        if(result == 0) {
            RunRecord runRecord = runRecordRepository.getById(id);
            runRecord.setRemark("");
            runRecordRepository.save(runRecord);
        }

        RunOperate target = new RunOperate();
        target.setOperator(operator);
        target.setResult(result);
        target.setTime(LocalDateTime.now());
        runOperateRepository.save(target);

        RunRecord record = GetStatus();
        record.setStatus(result);
        runRecordRepository.save(record);
    }

    public void Remark(String reason) {
        if(reason == null || reason == "") {
            LocalDateTime today = LocalDateTime.now();
            System.out.println("123");
//            RunRecord record = runRecordRepository.findByDate(today);
//            System.out.println(record);
//            record.setRemark("");
//            runRecordRepository.save(record);
        } else {
            RunRecord record = GetStatus();
            record.setRemark(reason);
            runRecordRepository.save(record);
        }
    }

    public List<RunRecord> GetAllRecords() {
        return runRecordRepository.findAll();
    }

    public List<RunOperate> GetAllOperates() {
        return runOperateRepository.findAll().stream()
                .filter(runOperate -> runOperate.getTime().toLocalDate().equals(LocalDate.now()))
                .sorted(Comparator.comparing(RunOperate::getTime).reversed())
                .collect(Collectors.toList());
    }
}
