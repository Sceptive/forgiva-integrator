package com.sceptive.forgiva.integrator.core.db.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.maven.repository.internal.SnapshotMetadataGeneratorFactory;

import javax.persistence.*;

@Entity
@Table(name = "fg_metadata",
        indexes =  {
                @Index(name = "md_host_index", columnList = "host"),
                @Index(name = "md_account_index", columnList = "account"),
                @Index(name = "md_userid_index", columnList = "userId"),
                @Index(name = "md_groupid_index", columnList = "metadataGroupId"),
                @Index(name = "md_userid_groupid_index", columnList = "userId,metadataGroupId" ),
        }
)
public class EMetadata {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    public long id;

    @Column(name = "metadataGroupId")
    public long metadataGroupId;

    @Column(name = "userId")
    public long userId;

    @Column(name = "passwordLength")
    public Integer passwordLength;

    @Column(name = "optUppercase")
    public Boolean optUppercase;

    @Column(name = "optLowercase")
    public Boolean optLowercase;

    @Column(name = "optNumbers")
    public Boolean optNumbers;

    @Column(name = "optSymbols")
    public Boolean optSymbols;

    @Column(name = "host")
    public String host;

    @Column(name = "account")
    public String account;

    @Column(name = "lastRenewal")
    public String lastRenewal;

    @Column(name = "complexity")
    public Integer complexity;

    @Column(name = "generatedBefore")
    public Boolean generatedBefore;

}
