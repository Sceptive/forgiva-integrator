package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;


@Entity
@Table(name = "fg_usergroup",
        indexes = {
                @Index(name = "ug_parentusergroupid_index", columnList = "parentUserGroupId")
        })
public class EUserGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long id;

    @Column(name = "userGroupName")
    public String userGroupName;

    @Column(name = "userGroupDescription")
    public String groupDescription;

    @Column(name = "parentUserGroupId")
    public long parentUserGroupId;
}
