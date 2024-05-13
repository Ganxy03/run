package cn.ganxy03.run.repository;

import cn.ganxy03.run.entity.MessageBroad;
import cn.ganxy03.run.entity.MessageReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageBroadRepository extends JpaRepository<MessageBroad, Integer> {

}
