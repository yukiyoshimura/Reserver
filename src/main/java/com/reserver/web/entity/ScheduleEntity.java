package com.reserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="t_schedule")
@Getter
@Setter
public class ScheduleEntity {

    // TODO フィールドがpublicでないとエラーになる。原因を調べる
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String date;
    public String name;
}
