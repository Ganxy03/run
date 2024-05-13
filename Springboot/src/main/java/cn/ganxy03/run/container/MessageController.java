package cn.ganxy03.run.container;

import cn.ganxy03.run.server.MessageServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageServer messageServer;

    public MessageController(MessageServer messageServer) {
        this.messageServer = messageServer;
    }

    @PostMapping("post")
    public ResponseEntity<String> Post(@RequestParam String content) {
        messageServer.PostMessage(content);
        return new ResponseEntity<>("Post success", HttpStatus.OK);
    }

    @PostMapping("reply")
    public ResponseEntity<String> Reply(@RequestParam Integer goal, @RequestParam String content) {
        messageServer.ReplyMessage(goal, content);
        return new ResponseEntity<>("Reply success", HttpStatus.OK);
    }

    @PostMapping("getAllBroads")
    public ResponseEntity<List> GetAllBroads() {
        return new ResponseEntity<>(messageServer.GetAllBroads(), HttpStatus.OK);
    }

    @PostMapping("getAllReplies")
    public ResponseEntity<List> GetAllReplies() {
        return new ResponseEntity<>(messageServer.GetAllReplies(), HttpStatus.OK);
    }
}
