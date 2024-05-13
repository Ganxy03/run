package cn.ganxy03.run.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "manager_account")
public class ManagerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String account;
    private String pwd;

}
