package cn.ganxy03.run.server;

import cn.ganxy03.run.entity.MessageBroad;
import cn.ganxy03.run.entity.MessageReply;

import java.util.List;

public interface MessageServer {
    void PostMessage(String content);

    void ReplyMessage(Integer goal, String content);

    List<MessageBroad> GetAllBroads();

    List<MessageReply> GetAllReplies();
}
