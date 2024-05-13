package cn.ganxy03.run.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "manager_info")
public class ManagerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String account;
    private String name;
    private String mail;
    private String depart;
    private Integer role;
}
