package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminResourceusageResponseCpuUsageByDay;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminResourceusageResponseCpuUsageByHour;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostAdminResourceusageResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-04-15T17:02:35.619+03:00[Europe/Istanbul]")
public class PostAdminResourceusageResponse   {
  @JsonProperty("cpuUsagePercentage")
  private String cpuUsagePercentage;

  @JsonProperty("totalMemory")
  private String totalMemory;

  @JsonProperty("usedMemory")
  private String usedMemory;

  @JsonProperty("totalDiskspace")
  private String totalDiskspace;

  @JsonProperty("usedDiskspace")
  private String usedDiskspace;

  @JsonProperty("cpuUsageByHour")
  private List<PostAdminResourceusageResponseCpuUsageByHour> cpuUsageByHour = null;

  @JsonProperty("cpuUsageByDay")
  private List<PostAdminResourceusageResponseCpuUsageByDay> cpuUsageByDay = null;

  @JsonProperty("totalUsers")
  private String totalUsers;

  @JsonProperty("totalUniqueHosts")
  private String totalUniqueHosts;

  @JsonProperty("databaseSize")
  private String databaseSize;

  @JsonProperty("averagePasswordGenerationTime")
  private String averagePasswordGenerationTime;

  @JsonProperty("serverPingTime")
  private String serverPingTime;

  @JsonProperty("averageDatabaseResponseTime")
  private String averageDatabaseResponseTime;

  public PostAdminResourceusageResponse cpuUsagePercentage(String cpuUsagePercentage) {
    this.cpuUsagePercentage = cpuUsagePercentage;
    return this;
  }

   /**
   * Get cpuUsagePercentage
   * @return cpuUsagePercentage
  **/
  @ApiModelProperty(value = "")
  public String getCpuUsagePercentage() {
    return cpuUsagePercentage;
  }

  public void setCpuUsagePercentage(String cpuUsagePercentage) {
    this.cpuUsagePercentage = cpuUsagePercentage;
  }

  public PostAdminResourceusageResponse totalMemory(String totalMemory) {
    this.totalMemory = totalMemory;
    return this;
  }

   /**
   * Total memory in MB.
   * @return totalMemory
  **/
  @ApiModelProperty(value = "Total memory in MB.")
  public String getTotalMemory() {
    return totalMemory;
  }

  public void setTotalMemory(String totalMemory) {
    this.totalMemory = totalMemory;
  }

  public PostAdminResourceusageResponse usedMemory(String usedMemory) {
    this.usedMemory = usedMemory;
    return this;
  }

   /**
   * Used memory in MB.
   * @return usedMemory
  **/
  @ApiModelProperty(value = "Used memory in MB.")
  public String getUsedMemory() {
    return usedMemory;
  }

  public void setUsedMemory(String usedMemory) {
    this.usedMemory = usedMemory;
  }

  public PostAdminResourceusageResponse totalDiskspace(String totalDiskspace) {
    this.totalDiskspace = totalDiskspace;
    return this;
  }

   /**
   * Total disk space in MB.
   * @return totalDiskspace
  **/
  @ApiModelProperty(value = "Total disk space in MB.")
  public String getTotalDiskspace() {
    return totalDiskspace;
  }

  public void setTotalDiskspace(String totalDiskspace) {
    this.totalDiskspace = totalDiskspace;
  }

  public PostAdminResourceusageResponse usedDiskspace(String usedDiskspace) {
    this.usedDiskspace = usedDiskspace;
    return this;
  }

   /**
   * Used disk space in MB.
   * @return usedDiskspace
  **/
  @ApiModelProperty(value = "Used disk space in MB.")
  public String getUsedDiskspace() {
    return usedDiskspace;
  }

  public void setUsedDiskspace(String usedDiskspace) {
    this.usedDiskspace = usedDiskspace;
  }

  public PostAdminResourceusageResponse cpuUsageByHour(List<PostAdminResourceusageResponseCpuUsageByHour> cpuUsageByHour) {
    this.cpuUsageByHour = cpuUsageByHour;
    return this;
  }

  public PostAdminResourceusageResponse addCpuUsageByHourItem(PostAdminResourceusageResponseCpuUsageByHour cpuUsageByHourItem) {
    if (this.cpuUsageByHour == null) {
      this.cpuUsageByHour = new ArrayList<PostAdminResourceusageResponseCpuUsageByHour>();
    }
    this.cpuUsageByHour.add(cpuUsageByHourItem);
    return this;
  }

   /**
   * Get cpuUsageByHour
   * @return cpuUsageByHour
  **/
  @ApiModelProperty(value = "")
  public List<PostAdminResourceusageResponseCpuUsageByHour> getCpuUsageByHour() {
    return cpuUsageByHour;
  }

  public void setCpuUsageByHour(List<PostAdminResourceusageResponseCpuUsageByHour> cpuUsageByHour) {
    this.cpuUsageByHour = cpuUsageByHour;
  }

  public PostAdminResourceusageResponse cpuUsageByDay(List<PostAdminResourceusageResponseCpuUsageByDay> cpuUsageByDay) {
    this.cpuUsageByDay = cpuUsageByDay;
    return this;
  }

  public PostAdminResourceusageResponse addCpuUsageByDayItem(PostAdminResourceusageResponseCpuUsageByDay cpuUsageByDayItem) {
    if (this.cpuUsageByDay == null) {
      this.cpuUsageByDay = new ArrayList<PostAdminResourceusageResponseCpuUsageByDay>();
    }
    this.cpuUsageByDay.add(cpuUsageByDayItem);
    return this;
  }

   /**
   * Get cpuUsageByDay
   * @return cpuUsageByDay
  **/
  @ApiModelProperty(value = "")
  public List<PostAdminResourceusageResponseCpuUsageByDay> getCpuUsageByDay() {
    return cpuUsageByDay;
  }

  public void setCpuUsageByDay(List<PostAdminResourceusageResponseCpuUsageByDay> cpuUsageByDay) {
    this.cpuUsageByDay = cpuUsageByDay;
  }

  public PostAdminResourceusageResponse totalUsers(String totalUsers) {
    this.totalUsers = totalUsers;
    return this;
  }

   /**
   * Total users of Forgiva Enterprise including LDAP users.
   * @return totalUsers
  **/
  @ApiModelProperty(value = "Total users of Forgiva Enterprise including LDAP users.")
  public String getTotalUsers() {
    return totalUsers;
  }

  public void setTotalUsers(String totalUsers) {
    this.totalUsers = totalUsers;
  }

  public PostAdminResourceusageResponse totalUniqueHosts(String totalUniqueHosts) {
    this.totalUniqueHosts = totalUniqueHosts;
    return this;
  }

   /**
   * Total unique host count recorded in the database.
   * @return totalUniqueHosts
  **/
  @ApiModelProperty(value = "Total unique host count recorded in the database.")
  public String getTotalUniqueHosts() {
    return totalUniqueHosts;
  }

  public void setTotalUniqueHosts(String totalUniqueHosts) {
    this.totalUniqueHosts = totalUniqueHosts;
  }

  public PostAdminResourceusageResponse databaseSize(String databaseSize) {
    this.databaseSize = databaseSize;
    return this;
  }

   /**
   * Total database size in MB.
   * @return databaseSize
  **/
  @ApiModelProperty(value = "Total database size in MB.")
  public String getDatabaseSize() {
    return databaseSize;
  }

  public void setDatabaseSize(String databaseSize) {
    this.databaseSize = databaseSize;
  }

  public PostAdminResourceusageResponse averagePasswordGenerationTime(String averagePasswordGenerationTime) {
    this.averagePasswordGenerationTime = averagePasswordGenerationTime;
    return this;
  }

   /**
   * Average password generation time in seconds.
   * @return averagePasswordGenerationTime
  **/
  @ApiModelProperty(value = "Average password generation time in seconds.")
  public String getAveragePasswordGenerationTime() {
    return averagePasswordGenerationTime;
  }

  public void setAveragePasswordGenerationTime(String averagePasswordGenerationTime) {
    this.averagePasswordGenerationTime = averagePasswordGenerationTime;
  }

  public PostAdminResourceusageResponse serverPingTime(String serverPingTime) {
    this.serverPingTime = serverPingTime;
    return this;
  }

   /**
   * Forgiva Server ping time in MS.
   * @return serverPingTime
  **/
  @ApiModelProperty(value = "Forgiva Server ping time in MS.")
  public String getServerPingTime() {
    return serverPingTime;
  }

  public void setServerPingTime(String serverPingTime) {
    this.serverPingTime = serverPingTime;
  }

  public PostAdminResourceusageResponse averageDatabaseResponseTime(String averageDatabaseResponseTime) {
    this.averageDatabaseResponseTime = averageDatabaseResponseTime;
    return this;
  }

   /**
   * If database is on remote average response time in MS.
   * @return averageDatabaseResponseTime
  **/
  @ApiModelProperty(value = "If database is on remote average response time in MS.")
  public String getAverageDatabaseResponseTime() {
    return averageDatabaseResponseTime;
  }

  public void setAverageDatabaseResponseTime(String averageDatabaseResponseTime) {
    this.averageDatabaseResponseTime = averageDatabaseResponseTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminResourceusageResponse postAdminResourceusageResponse = (PostAdminResourceusageResponse) o;
    return Objects.equals(this.cpuUsagePercentage, postAdminResourceusageResponse.cpuUsagePercentage) &&
        Objects.equals(this.totalMemory, postAdminResourceusageResponse.totalMemory) &&
        Objects.equals(this.usedMemory, postAdminResourceusageResponse.usedMemory) &&
        Objects.equals(this.totalDiskspace, postAdminResourceusageResponse.totalDiskspace) &&
        Objects.equals(this.usedDiskspace, postAdminResourceusageResponse.usedDiskspace) &&
        Objects.equals(this.cpuUsageByHour, postAdminResourceusageResponse.cpuUsageByHour) &&
        Objects.equals(this.cpuUsageByDay, postAdminResourceusageResponse.cpuUsageByDay) &&
        Objects.equals(this.totalUsers, postAdminResourceusageResponse.totalUsers) &&
        Objects.equals(this.totalUniqueHosts, postAdminResourceusageResponse.totalUniqueHosts) &&
        Objects.equals(this.databaseSize, postAdminResourceusageResponse.databaseSize) &&
        Objects.equals(this.averagePasswordGenerationTime, postAdminResourceusageResponse.averagePasswordGenerationTime) &&
        Objects.equals(this.serverPingTime, postAdminResourceusageResponse.serverPingTime) &&
        Objects.equals(this.averageDatabaseResponseTime, postAdminResourceusageResponse.averageDatabaseResponseTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuUsagePercentage, totalMemory, usedMemory, totalDiskspace, usedDiskspace, cpuUsageByHour, cpuUsageByDay, totalUsers, totalUniqueHosts, databaseSize, averagePasswordGenerationTime, serverPingTime, averageDatabaseResponseTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminResourceusageResponse {\n");
    
    sb.append("    cpuUsagePercentage: ").append(toIndentedString(cpuUsagePercentage)).append("\n");
    sb.append("    totalMemory: ").append(toIndentedString(totalMemory)).append("\n");
    sb.append("    usedMemory: ").append(toIndentedString(usedMemory)).append("\n");
    sb.append("    totalDiskspace: ").append(toIndentedString(totalDiskspace)).append("\n");
    sb.append("    usedDiskspace: ").append(toIndentedString(usedDiskspace)).append("\n");
    sb.append("    cpuUsageByHour: ").append(toIndentedString(cpuUsageByHour)).append("\n");
    sb.append("    cpuUsageByDay: ").append(toIndentedString(cpuUsageByDay)).append("\n");
    sb.append("    totalUsers: ").append(toIndentedString(totalUsers)).append("\n");
    sb.append("    totalUniqueHosts: ").append(toIndentedString(totalUniqueHosts)).append("\n");
    sb.append("    databaseSize: ").append(toIndentedString(databaseSize)).append("\n");
    sb.append("    averagePasswordGenerationTime: ").append(toIndentedString(averagePasswordGenerationTime)).append("\n");
    sb.append("    serverPingTime: ").append(toIndentedString(serverPingTime)).append("\n");
    sb.append("    averageDatabaseResponseTime: ").append(toIndentedString(averageDatabaseResponseTime)).append("\n");
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

