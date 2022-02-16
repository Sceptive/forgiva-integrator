package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminResourceusageResponseCpuUsageByHour
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-04-15T17:02:35.619+03:00[Europe/Istanbul]")
public class PostAdminResourceusageResponseCpuUsageByHour   {
  @JsonProperty("hour")
  private String hour;

  @JsonProperty("percentage")
  private Integer percentage;

  public PostAdminResourceusageResponseCpuUsageByHour hour(String hour) {
    this.hour = hour;
    return this;
  }

   /**
   * Date-time in YYYY-mm-dd HH:MM format.
   * @return hour
  **/
  @ApiModelProperty(value = "Date-time in YYYY-mm-dd HH:MM format.")
  public String getHour() {
    return hour;
  }

  public void setHour(String hour) {
    this.hour = hour;
  }

  public PostAdminResourceusageResponseCpuUsageByHour percentage(Integer percentage) {
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
    PostAdminResourceusageResponseCpuUsageByHour postAdminResourceusageResponseCpuUsageByHour = (PostAdminResourceusageResponseCpuUsageByHour) o;
    return Objects.equals(this.hour, postAdminResourceusageResponseCpuUsageByHour.hour) &&
        Objects.equals(this.percentage, postAdminResourceusageResponseCpuUsageByHour.percentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hour, percentage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminResourceusageResponseCpuUsageByHour {\n");
    
    sb.append("    hour: ").append(toIndentedString(hour)).append("\n");
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

