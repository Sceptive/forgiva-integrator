package com.sceptive.forgiva.integrator.core.db.objects;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "fg_loginlog",
        indexes =  {
                @Index(name = "ll_username_index", columnList = "userName"),
                @Index(name = "ll_trydate_index", columnList = "tryDate"),
        })
public class ELoginLog {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public long id;

    @Column(name = "userName")
    public String userName;

    @Column(name = "tryDate")
    public LocalDateTime tryDate ;

    @Column(name = "success")
    public boolean success ;


}