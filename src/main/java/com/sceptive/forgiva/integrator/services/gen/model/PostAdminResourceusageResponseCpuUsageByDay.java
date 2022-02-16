package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminResourceusageResponseCpuUsageByDay
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-04-15T17:02:35.619+03:00[Europe/Istanbul]")
public class PostAdminResourceusageResponseCpuUsageByDay   {
  @JsonProperty("day")
  private String day;

  @JsonProperty("percentage")
  private Integer percentage;

  public PostAdminResourceusageResponseCpuUsageByDay day(String day) {
    this.day = day;
    return this;
  }

   /**
   * Date-time in YYYY-mm-dd HH:MM format.
   * @return day
  **/
  @ApiModelProperty(value = "Date-time in YYYY-mm-dd HH:MM format.")
  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public PostAdminResourceusageResponseCpuUsageByDay percentage(Integer percentage) {
    this.percentage = percentage;
    return this;
  }

   /**
   * Get percentage
   * @return percentage
  **/
  @ApiModelProperty(value = "")
  public Integer getPercentage() {
    return percentage;
  }

  public void setPercentage(Integer percentage) {
    this.percentage = percentage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminResourceusageResponseCpuUsageByDay postAdminResourceusageResponseCpuUsageByDay = (PostAdminResourceusageResponseCpuUsageByDay) o;
    return Objects.equals(this.day, postAdminResourceusageResponseCpuUsageByDay.day) &&
        Objects.equals(this.percentage, postAdminResourceusageResponseCpuUsageByDay.percentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, percentage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminResourceusageResponseCpuUsageByDay {\n");
    
    sb.append("    day: ").append(toIndentedString(day)).append("\n");
    sb.append("    percentage: ").append(toIndentedString(percentage)).append("\n");
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

