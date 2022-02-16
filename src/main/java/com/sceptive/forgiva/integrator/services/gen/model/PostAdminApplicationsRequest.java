package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminApplicationsRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:47:12.794+03:00[Europe/Istanbul]")
public class PostAdminApplicationsRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("hostId")
  private Integer hostId;

  @JsonProperty("startIdx")
  private Integer startIdx;

  @JsonProperty("count")
  private Integer count;

  public PostAdminApplicationsRequest header(Header header) {
    this.header = header;
    return this;
  }

   /**
   * Get header
   * @return header
  **/
  @ApiModelProperty(value = "")
  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public PostAdminApplicationsRequest hostId(Integer hostId) {
    this.hostId = hostId;
    return this;
  }

   /**
   * If Host id is null getting whole applications.If there is a specified Host id just fetch it's applications.
   * @return hostId
  **/
  @ApiModelProperty(value = "If Host id is null getting whole applications.If there is a specified Host id just fetch it's applications.")
  public Integer getHostId() {
    return hostId;
  }

  public void setHostId(Integer hostId) {
    this.hostId = hostId;
  }

  public PostAdminApplicationsRequest startIdx(Integer startIdx) {
    this.startIdx = startIdx;
    return this;
  }

   /**
   * Start index of the records. (Default is 0)
   * @return startIdx
  **/
  @ApiModelProperty(value = "Start index of the records. (Default is 0)")
  public Integer getStartIdx() {
    return startIdx;
  }

  public void setStartIdx(Integer startIdx) {
    this.startIdx = startIdx;
  }

  public PostAdminApplicationsRequest count(Integer count) {
    this.count = count;
    return this;
  }

   /**
   * Amount of records to return starting from startIdx. (Default is 100)
   * @return count
  **/
  @ApiModelProperty(value = "Amount of records to return starting from startIdx. (Default is 100)")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminApplicationsRequest postAdminApplicationsRequest = (PostAdminApplicationsRequest) o;
    return Objects.equals(this.header, postAdminApplicationsRequest.header) &&
        Objects.equals(this.hostId, postAdminApplicationsRequest.hostId) &&
        Objects.equals(this.startIdx, postAdminApplicationsRequest.startIdx) &&
        Objects.equals(this.count, postAdminApplicationsRequest.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, hostId, startIdx, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminApplicationsRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
    sb.append("    startIdx: ").append(toIndentedString(startIdx)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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

