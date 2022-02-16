package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;

@Entity
@Table(name = "fg_application",
       indexes =  {
               @Index(name = "app_applicationName_index", columnList = "applicationName"),
               @Index(name = "app_loginPageURL_index", columnList = "loginPageURL"),
               @Index(name = "app_homePageURL_index", columnList = "homePageURL"),
               @Index(name = "app_monitoringFingerprint_index", columnList = "monitoringFingerprint")
       })

public class EApplication {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name = "applicationId")
public long applicationId;

@Column(name = "hostId")
public long hostId;

@Column(name = "applicationName")
public String applicationName;

@Column(name = "version")
public String version;

@Column(name = "portRunning")
public String portRunning;

@Column(name = "loginPageURL")
public String loginPageURL;

@Column(name = "homePageURL")
public String homePageURL;

@Column(name = "monitoringFingerprint")
public String monitoringFingerprint;

@Column(name = "reserved1")
public String reserved1;

@Column(name = "reserved2")
public String reserved2;

@Column(name = "reserved3")
public String reserved3;
}