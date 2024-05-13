package cn.ganxy03.run.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "manager_secure")
public class ManagerSecure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String account;
    private String ip;
    private Double latitude;
    private Double longitude;
    private String nation;
    private String province;
    private String city;
    private String district;

    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    private LocalDateTime time;

}
