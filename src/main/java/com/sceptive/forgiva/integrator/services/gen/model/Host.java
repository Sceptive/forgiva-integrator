package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Host
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:54:49.401+03:00[Europe/Istanbul]")
public class Host   {
  @JsonProperty("hostId")
  private Integer hostId;

  @JsonProperty("hostName")
  private String hostName;

  @JsonProperty("dnsName")
  private String dnsName;

  @JsonProperty("operatingSystemName")
  private String operatingSystemName;

  @JsonProperty("operatingSystemVersion")
  private String operatingSystemVersion;

  @JsonProperty("description")
  private String description;

  @JsonProperty("reserved1")
  private String reserved1;

  @JsonProperty("reserved2")
  private String reserved2;

  @JsonProperty("reserved3")
  private String reserved3;

  public Host hostId(Integer hostId) {
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

  public Host hostName(String hostName) {
    this.hostName = hostName;
    return this;
  }

   /**
   * Get hostName
   * @return hostName
  **/
  @ApiModelProperty(value = "")
  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public Host dnsName(String dnsName) {
    this.dnsName = dnsName;
    return this;
  }

   /**
   * Get dnsName
   * @return dnsName
  **/
  @ApiModelProperty(value = "")
  public String getDnsName() {
    return dnsName;
  }

  public void setDnsName(String dnsName) {
    this.dnsName = dnsName;
  }

  public Host operatingSystemName(String operatingSystemName) {
    this.operatingSystemName = operatingSystemName;
    return this;
  }

   /**
   * Get operatingSystemName
   * @return operatingSystemName
  **/
  @ApiModelProperty(value = "")
  public String getOperatingSystemName() {
    return operatingSystemName;
  }

  public void setOperatingSystemName(String operatingSystemName) {
    this.operatingSystemName = operatingSystemName;
  }

  public Host operatingSystemVersion(String operatingSystemVersion) {
    this.operatingSystemVersion = operatingSystemVersion;
    return this;
  }

   /**
   * Get operatingSystemVersion
   * @return operatingSystemVersion
  **/
  @ApiModelProperty(value = "")
  public String getOperatingSystemVersion() {
    return operatingSystemVersion;
  }

  public void setOperatingSystemVersion(String operatingSystemVersion) {
    this.operatingSystemVersion = operatingSystemVersion;
  }

  public Host description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Host reserved1(String reserved1) {
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

  public Host reserved2(String reserved2) {
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

  public Host reserved3(String reserved3) {
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
    Host host = (Host) o;
    return Objects.equals(this.hostId, host.hostId) &&
        Objects.equals(this.hostName, host.hostName) &&
        Objects.equals(this.dnsName, host.dnsName) &&
        Objects.equals(this.operatingSystemName, host.operatingSystemName) &&
        Objects.equals(this.operatingSystemVersion, host.operatingSystemVersion) &&
        Objects.equals(this.description, host.description) &&
        Objects.equals(this.reserved1, host.reserved1) &&
        Objects.equals(this.reserved2, host.reserved2) &&
        Objects.equals(this.reserved3, host.reserved3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostId, hostName, dnsName, operatingSystemName, operatingSystemVersion, description, reserved1, reserved2, reserved3);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Host {\n");
    
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
    sb.append("    dnsName: ").append(toIndentedString(dnsName)).append("\n");
    sb.append("    operatingSystemName: ").append(toIndentedString(operatingSystemName)).append("\n");
    sb.append("    operatingSystemVersion: ").append(toIndentedString(operatingSystemVersion)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

