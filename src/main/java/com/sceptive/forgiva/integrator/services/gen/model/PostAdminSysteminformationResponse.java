package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminSysteminformationResponseEnabledFeatures;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminSysteminformationResponseIpConfiguration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostAdminSysteminformationResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-09T04:11:18.276+03:00[Europe/Istanbul]")
public class PostAdminSysteminformationResponse   {
  @JsonProperty("operatingSystem")
  private String operatingSystem;

  @JsonProperty("processorInformation")
  private String processorInformation;

  @JsonProperty("timeOnSystem")
  private String timeOnSystem;

  @JsonProperty("systemUptime")
  private String systemUptime;

  @JsonProperty("forgivaVersion")
  private String forgivaVersion;

  @JsonProperty("hostname")
  private String hostname;

  @JsonProperty("ipConfiguration")
  private List<PostAdminSysteminformationResponseIpConfiguration> ipConfiguration = null;

  @JsonProperty("javaEnvironment")
  private String javaEnvironment;

  @JsonProperty("enabledFeatures")
  private PostAdminSysteminformationResponseEnabledFeatures enabledFeatures;

  public PostAdminSysteminformationResponse operatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
    return this;
  }

   /**
   * Get operatingSystem
   * @return operatingSystem
  **/
  @ApiModelProperty(value = "")
  public String getOperatingSystem() {
    return operatingSystem;
  }

  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }

  public PostAdminSysteminformationResponse processorInformation(String processorInformation) {
    this.processorInformation = processorInformation;
    return this;
  }

   /**
   * Get processorInformation
   * @return processorInformation
  **/
  @ApiModelProperty(value = "")
  public String getProcessorInformation() {
    return processorInformation;
  }

  public void setProcessorInformation(String processorInformation) {
    this.processorInformation = processorInformation;
  }

  public PostAdminSysteminformationResponse timeOnSystem(String timeOnSystem) {
    this.timeOnSystem = timeOnSystem;
    return this;
  }

   /**
   * Get timeOnSystem
   * @return timeOnSystem
  **/
  @ApiModelProperty(value = "")
  public String getTimeOnSystem() {
    return timeOnSystem;
  }

  public void setTimeOnSystem(String timeOnSystem) {
    this.timeOnSystem = timeOnSystem;
  }

  public PostAdminSysteminformationResponse systemUptime(String systemUptime) {
    this.systemUptime = systemUptime;
    return this;
  }

   /**
   * Get systemUptime
   * @return systemUptime
  **/
  @ApiModelProperty(value = "")
  public String getSystemUptime() {
    return systemUptime;
  }

  public void setSystemUptime(String systemUptime) {
    this.systemUptime = systemUptime;
  }

  public PostAdminSysteminformationResponse forgivaVersion(String forgivaVersion) {
    this.forgivaVersion = forgivaVersion;
    return this;
  }

   /**
   * Get forgivaVersion
   * @return forgivaVersion
  **/
  @ApiModelProperty(value = "")
  public String getForgivaVersion() {
    return forgivaVersion;
  }

  public void setForgivaVersion(String forgivaVersion) {
    this.forgivaVersion = forgivaVersion;
  }

  public PostAdminSysteminformationResponse hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

   /**
   * Get hostname
   * @return hostname
  **/
  @ApiModelProperty(value = "")
  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public PostAdminSysteminformationResponse ipConfiguration(List<PostAdminSysteminformationResponseIpConfiguration> ipConfiguration) {
    this.ipConfiguration = ipConfiguration;
    return this;
  }

  public PostAdminSysteminformationResponse addIpConfigurationItem(PostAdminSysteminformationResponseIpConfiguration ipConfigurationItem) {
    if (this.ipConfiguration == null) {
      this.ipConfiguration = new ArrayList<PostAdminSysteminformationResponseIpConfiguration>();
    }
    this.ipConfiguration.add(ipConfigurationItem);
    return this;
  }

   /**
   * Get ipConfiguration
   * @return ipConfiguration
  **/
  @ApiModelProperty(value = "")
  public List<PostAdminSysteminformationResponseIpConfiguration> getIpConfiguration() {
    return ipConfiguration;
  }

  public void setIpConfiguration(List<PostAdminSysteminformationResponseIpConfiguration> ipConfiguration) {
    this.ipConfiguration = ipConfiguration;
  }

  public PostAdminSysteminformationResponse javaEnvironment(String javaEnvironment) {
    this.javaEnvironment = javaEnvironment;
    return this;
  }

   /**
   * Get javaEnvironment
   * @return javaEnvironment
  **/
  @ApiModelProperty(value = "")
  public String getJavaEnvironment() {
    return javaEnvironment;
  }

  public void setJavaEnvironment(String javaEnvironment) {
    this.javaEnvironment = javaEnvironment;
  }

  public PostAdminSysteminformationResponse enabledFeatures(PostAdminSysteminformationResponseEnabledFeatures enabledFeatures) {
    this.enabledFeatures = enabledFeatures;
    return this;
  }

   /**
   * Get enabledFeatures
   * @return enabledFeatures
  **/
  @ApiModelProperty(value = "")
  public PostAdminSysteminformationResponseEnabledFeatures getEnabledFeatures() {
    return enabledFeatures;
  }

  public void setEnabledFeatures(PostAdminSysteminformationResponseEnabledFeatures enabledFeatures) {
    this.enabledFeatures = enabledFeatures;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminSysteminformationResponse postAdminSysteminformationResponse = (PostAdminSysteminformationResponse) o;
    return Objects.equals(this.operatingSystem, postAdminSysteminformationResponse.operatingSystem) &&
        Objects.equals(this.processorInformation, postAdminSysteminformationResponse.processorInformation) &&
        Objects.equals(this.timeOnSystem, postAdminSysteminformationResponse.timeOnSystem) &&
        Objects.equals(this.systemUptime, postAdminSysteminformationResponse.systemUptime) &&
        Objects.equals(this.forgivaVersion, postAdminSysteminformationResponse.forgivaVersion) &&
        Objects.equals(this.hostname, postAdminSysteminformationResponse.hostname) &&
        Objects.equals(this.ipConfiguration, postAdminSysteminformationResponse.ipConfiguration) &&
        Objects.equals(this.javaEnvironment, postAdminSysteminformationResponse.javaEnvironment) &&
        Objects.equals(this.enabledFeatures, postAdminSysteminformationResponse.enabledFeatures);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operatingSystem, processorInformation, timeOnSystem, systemUptime, forgivaVersion, hostname, ipConfiguration, javaEnvironment, enabledFeatures);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminSysteminformationResponse {\n");
    
    sb.append("    operatingSystem: ").append(toIndentedString(operatingSystem)).append("\n");
    sb.append("    processorInformation: ").append(toIndentedString(processorInformation)).append("\n");
    sb.append("    timeOnSystem: ").append(toIndentedString(timeOnSystem)).append("\n");
    sb.append("    systemUptime: ").append(toIndentedString(systemUptime)).append("\n");
    sb.append("    forgivaVersion: ").append(toIndentedString(forgivaVersion)).append("\n");
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    ipConfiguration: ").append(toIndentedString(ipConfiguration)).append("\n");
    sb.append("    javaEnvironment: ").append(toIndentedString(javaEnvironment)).append("\n");
    sb.append("    enabledFeatures: ").append(toIndentedString(enabledFeatures)).append("\n");
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

