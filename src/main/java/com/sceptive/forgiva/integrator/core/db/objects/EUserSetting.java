package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;

@Entity
@Table(name = "fg_usersetting",
       indexes = {
               @Index(name = "us_userid_index", columnList = "userId"),
               @Index(name = "us_userid_key_index", columnList = "userId,key"),
       })
public class EUserSetting {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "id")
public long id;

@Column(name = "userId")
public long userId;

@Column(name = "key")
public String key;

@Column(name = "host")
public String value;
}
