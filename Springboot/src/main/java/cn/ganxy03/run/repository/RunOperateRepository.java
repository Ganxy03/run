package cn.ganxy03.run.repository;

import cn.ganxy03.run.entity.RunOperate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RunOperateRepository extends JpaRepository<RunOperate, Integer> {
    RunOperate findByTime(LocalDateTime time);
}
