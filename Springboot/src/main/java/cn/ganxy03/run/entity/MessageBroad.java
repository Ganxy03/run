package cn.ganxy03.run.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "message_broad")
public class MessageBroad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ip;
    private String content;
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    private LocalDateTime time;
}
