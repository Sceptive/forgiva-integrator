package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;

@Entity
@Table(name = "fg_user",
        indexes =  {
                @Index(name = "us_groupid_index", columnList = "groupId"),
                @Index(name = "us_username_email_index", columnList = "username,email"),
                @Index(name = "us_username_index", columnList = "username"),
                @Index(name = "us_email_index", columnList = "email"),
        })
public class EUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public long id;

    @Column(name = "groupId")
    public long groupId;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "email")
    public String email;

    @Column(name = "fullname")
    public String fullname;

    @Column(name = "externalUser")
    public Boolean externalUser = false;

}
