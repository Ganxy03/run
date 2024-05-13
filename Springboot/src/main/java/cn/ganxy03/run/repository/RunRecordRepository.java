package cn.ganxy03.run.repository;

import cn.ganxy03.run.entity.RunRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RunRecordRepository extends JpaRepository<RunRecord, Integer> {
    RunRecord findByDate(LocalDateTime date);

    RunRecord getById(Integer id);
}
