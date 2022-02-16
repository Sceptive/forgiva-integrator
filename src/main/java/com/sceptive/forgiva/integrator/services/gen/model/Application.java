package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Application
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:54:49.401+03:00[Europe/Istanbul]")
public class Application   {
  @JsonProperty("hostId")
  private Integer hostId;

  @JsonProperty("applicationId")
  private Integer applicationId;

  @JsonProperty("applicationName")
  private String applicationName;

  @JsonProperty("version")
  private String version;

  @JsonProperty("portRunning")
  private String portRunning;

  @JsonProperty("loginPageURL")
  private String loginPageURL;

  @JsonProperty("homePageURL")
  private String homePageURL;

  @JsonProperty("monitoringFingerprint")
  private String monitoringFingerprint;

  @JsonProperty("reserved1")
  private String reserved1;

  @JsonProperty("reserved2")
  private String reserved2;

  @JsonProperty("reserved3")
  private String reserved3;

  public Application hostId(Integer hostId) {
    this.hostId = hostId;
    return this;
  }

   /**
   * Get hostId
   * @return hostId
  **/
  @ApiModelProperty(value = "")
  public Integer getHostId() {
    return hostId;
  }

  public void setHostId(Integer hostId) {
    this.hostId = hostId;
  }

  public Application applicationId(Integer applicationId) {
    this.applicationId = applicationId;
    return this;
  }

   /**
   * Get applicationId
   * @return applicationId
  **/
  @ApiModelProperty(value = "")
  public Integer getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(Integer applicationId) {
    this.applicationId = applicationId;
  }

  public Application applicationName(String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

   /**
   * Get applicationName
   * @return applicationName
  **/
  @ApiModelProperty(value = "")
  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public Application version(String version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Application portRunning(String portRunning) {
    this.portRunning = portRunning;
    return this;
  }

   /**
   * Get portRunning
   * @return portRunning
  **/
  @ApiModelProperty(value = "")
  public String getPortRunning() {
    return portRunning;
  }

  public void setPortRunning(String portRunning) {
    this.portRunning = portRunning;
  }

  public Application loginPageURL(String loginPageURL) {
    this.loginPageURL = loginPageURL;
    return this;
  }

   /**
   * Get loginPageURL
   * @return loginPageURL
  **/
  @ApiModelProperty(value = "")
  public String getLoginPageURL() {
    return loginPageURL;
  }

  public void setLoginPageURL(String loginPageURL) {
    this.loginPageURL = loginPageURL;
  }

  public Application homePageURL(String homePageURL) {
    this.homePageURL = homePageURL;
    return this;
  }

   /**
   * Get homePageURL
   * @return homePageURL
  **/
  @ApiModelProperty(value = "")
  public String getHomePageURL() {
    return homePageURL;
  }

  public void setHomePageURL(String homePageURL) {
    this.homePageURL = homePageURL;
  }

  public Application monitoringFingerprint(String monitoringFingerprint) {
    this.monitoringFingerprint = monitoringFingerprint;
    return this;
  }

   /**
   * Get monitoringFingerprint
   * @return monitoringFingerprint
  **/
  @ApiModelProperty(value = "")
  public String getMonitoringFingerprint() {
    return monitoringFingerprint;
  }

  public void setMonitoringFingerprint(String monitoringFingerprint) {
    this.monitoringFingerprint = monitoringFingerprint;
  }

  public Application reserved1(String reserved1) {
    this.reserved1 = reserved1;
    return this;
  }

   /**
   * Get reserved1
   * @return reserved1
  **/
  @ApiModelProperty(value = "")
  public String getReserved1() {
    return reserved1;
  }

  public void setReserved1(String reserved1) {
    this.reserved1 = reserved1;
  }

  public Application reserved2(String reserved2) {
    this.reserved2 = reserved2;
    return this;
  }

   /**
   * Get reserved2
   * @return reserved2
  **/
  @ApiModelProperty(value = "")
  public String getReserved2() {
    return reserved2;
  }

  public void setReserved2(String reserved2) {
    this.reserved2 = reserved2;
  }

  public Application reserved3(String reserved3) {
    this.reserved3 = reserved3;
    return this;
  }

   /**
   * Get reserved3
   * @return reserved3
  **/
  @ApiModelProperty(value = "")
  public String getReserved3() {
    return reserved3;
  }

  public void setReserved3(String reserved3) {
    this.reserved3 = reserved3;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Application application = (Application) o;
    return Objects.equals(this.hostId, application.hostId) &&
        Objects.equals(this.applicationId, application.applicationId) &&
        Objects.equals(this.applicationName, application.applicationName) &&
        Objects.equals(this.version, application.version) &&
        Objects.equals(this.portRunning, application.portRunning) &&
        Objects.equals(this.loginPageURL, application.loginPageURL) &&
        Objects.equals(this.homePageURL, application.homePageURL) &&
        Objects.equals(this.monitoringFingerprint, application.monitoringFingerprint) &&
        Objects.equals(this.reserved1, application.reserved1) &&
        Objects.equals(this.reserved2, application.reserved2) &&
        Objects.equals(this.reserved3, application.reserved3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostId, applicationId, applicationName, version, portRunning, loginPageURL, homePageURL, monitoringFingerprint, reserved1, reserved2, reserved3);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Application {\n");
    
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    portRunning: ").append(toIndentedString(portRunning)).append("\n");
    sb.append("    loginPageURL: ").append(toIndentedString(loginPageURL)).append("\n");
    sb.append("    homePageURL: ").append(toIndentedString(homePageURL)).append("\n");
    sb.append("    monitoringFingerprint: ").append(toIndentedString(monitoringFingerprint)).append("\n");
    sb.append("    reserved1: ").append(toIndentedString(reserved1)).append("\n");
    sb.append("    reserved2: ").append(toIndentedString(reserved2)).append("\n");
    sb.append("    reserved3: ").append(toIndentedString(reserved3)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

