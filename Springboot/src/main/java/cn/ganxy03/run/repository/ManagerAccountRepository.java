package cn.ganxy03.run.repository;

import cn.ganxy03.run.entity.ManagerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerAccountRepository extends JpaRepository<ManagerAccount, Integer> {
    ManagerAccount findByAccount(String account);
}
