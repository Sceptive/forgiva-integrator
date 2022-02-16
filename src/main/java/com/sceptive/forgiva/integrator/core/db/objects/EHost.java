package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;

@Entity
@Table(name = "fg_host",
       indexes =  {
               @Index(name = "hs_hostName_index", columnList = "hostName"),
               @Index(name = "hs_dnsName_index", columnList = "dnsName"),

       })
public class EHost {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "hostId")
public long hostId;

@Column(name = "hostName")
public String hostName;

@Column(name = "dnsName")
public String dnsName;

@Column(name = "operatingSystemName")
public String operatingSystemName;

@Column(name = "operatingSystemVersion")
public String operatingSystemVersion;

@Column(name = "description")
public String description;

@Column(name = "reserved1")
public String reserved1;

@Column(name = "reserved2")
public String reserved2;

@Column(name = "reserved3")
public String reserved3;

}