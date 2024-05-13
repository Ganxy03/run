package cn.ganxy03.run.repository;

import cn.ganxy03.run.entity.ManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerInfoRepository extends JpaRepository<ManagerInfo, Integer> {
    ManagerInfo findByAccount(String account);

    List<ManagerInfo> findAll();
}
