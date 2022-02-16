package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminSysteminformationResponseIpConfiguration
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostAdminSysteminformationResponseIpConfiguration   {
  @JsonProperty("device")
  private String device;

  @JsonProperty("ipv4")
  private String ipv4;

  @JsonProperty("ipv6")
  private String ipv6;

  @JsonProperty("mac")
  private String mac;

  public PostAdminSysteminformationResponseIpConfiguration device(String device) {
    this.device = device;
    return this;
  }

   /**
   * Get device
   * @return device
  **/
  @ApiModelProperty(value = "")
  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public PostAdminSysteminformationResponseIpConfiguration ipv4(String ipv4) {
    this.ipv4 = ipv4;
    return this;
  }

   /**
   * Get ipv4
   * @return ipv4
  **/
  @ApiModelProperty(value = "")
  public String getIpv4() {
    return ipv4;
  }

  public void setIpv4(String ipv4) {
    this.ipv4 = ipv4;
  }

  public PostAdminSysteminformationResponseIpConfiguration ipv6(String ipv6) {
    this.ipv6 = ipv6;
    return this;
  }

   /**
   * Get ipv6
   * @return ipv6
  **/
  @ApiModelProperty(value = "")
  public String getIpv6() {
    return ipv6;
  }

  public void setIpv6(String ipv6) {
    this.ipv6 = ipv6;
  }

  public PostAdminSysteminformationResponseIpConfiguration mac(String mac) {
    this.mac = mac;
    return this;
  }

   /**
   * Get mac
   * @return mac
  **/
  @ApiModelProperty(value = "")
  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminSysteminformationResponseIpConfiguration postAdminSysteminformationResponseIpConfiguration = (PostAdminSysteminformationResponseIpConfiguration) o;
    return Objects.equals(this.device, postAdminSysteminformationResponseIpConfiguration.device) &&
        Objects.equals(this.ipv4, postAdminSysteminformationResponseIpConfiguration.ipv4) &&
        Objects.equals(this.ipv6, postAdminSysteminformationResponseIpConfiguration.ipv6) &&
        Objects.equals(this.mac, postAdminSysteminformationResponseIpConfiguration.mac);
  }

  @Override
  public int hashCode() {
    return Objects.hash(device, ipv4, ipv6, mac);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminSysteminformationResponseIpConfiguration {\n");
    
    sb.append("    device: ").append(toIndentedString(device)).append("\n");
    sb.append("    ipv4: ").append(toIndentedString(ipv4)).append("\n");
    sb.append("    ipv6: ").append(toIndentedString(ipv6)).append("\n");
    sb.append("    mac: ").append(toIndentedString(mac)).append("\n");
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

