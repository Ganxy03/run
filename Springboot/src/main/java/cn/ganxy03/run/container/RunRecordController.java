package cn.ganxy03.run.container;

import cn.ganxy03.run.entity.RunRecord;
import cn.ganxy03.run.server.RunRecordServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("run")
public class RunRecordController {
    private final RunRecordServer runRecordServer;

    public RunRecordController(RunRecordServer runRecordServer) {
        this.runRecordServer = runRecordServer;
    }

    @PostMapping("getStatus")
    public ResponseEntity<RunRecord> GetStatus() {
        return new ResponseEntity<>(runRecordServer.GetStatus(), HttpStatus.OK);
    }

    @PostMapping("operate")
    public ResponseEntity<String> Operate(@RequestParam Integer id, @RequestParam String operator, @RequestParam Integer result) {
        runRecordServer.Operate(id, operator, result);
        return new ResponseEntity<>("Operate success", HttpStatus.OK);
    }

    @PostMapping("remark")
    public ResponseEntity<String> Remark(@RequestParam String reason) {
        runRecordServer.Remark(reason);
        return new ResponseEntity<>("Remark success", HttpStatus.OK);
    }

    @PostMapping("getAllRecords")
    public ResponseEntity<List> GetAllRecords() {
        return new ResponseEntity<>(runRecordServer.GetAllRecords(), HttpStatus.OK);
    }

    @PostMapping("getAllOperates")
    public ResponseEntity<List> getAllOperates() {
        return new ResponseEntity<>(runRecordServer.GetAllOperates(), HttpStatus.OK);
    }
}
