package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;

@Entity
@Table(name = "fg_metadatagroup",
        indexes =  {
        @Index(name = "mdg_userid_index", columnList = "userId"),
        @Index(name = "mdg_parentgroupid_index", columnList = "parentGroupId"),
        @Index(name = "mdg_userid_groupid_index", columnList = "userId,parentGroupId" ),
})
public class EMetadataGroup {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    public long id;

    @Column(name = "userId")
    public long userId;

    @Column(name = "groupName")
    public String groupName;

    @Column(name = "groupDescription")
    public String groupDescription;

    @Column(name = "parentGroupId")
    public long parentGroupId;
}
