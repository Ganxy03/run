package cn.ganxy03.run.server;

import cn.ganxy03.run.entity.RunOperate;
import cn.ganxy03.run.entity.RunRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface RunRecordServer {
    void Generate();

//    获取今天的晨跑记录
    RunRecord GetStatus();
//    操作/修改晨跑状态
    void Operate(Integer id, String operator, Integer result);
//    不晨跑原因
    void Remark(String reason);

    List<RunRecord> GetAllRecords();

    List<RunOperate> GetAllOperates();
}
