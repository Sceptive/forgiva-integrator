package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserMetadataBygroupRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataBygroupRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("groupId")
  private String groupId;

  @JsonProperty("startIdx")
  private Integer startIdx;

  @JsonProperty("count")
  private Integer count;

  public PostUserMetadataBygroupRequest header(Header header) {
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

  public PostUserMetadataBygroupRequest groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

   /**
   * Group id contains metadatas.
   * @return groupId
  **/
  @ApiModelProperty(value = "Group id contains metadatas.")
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public PostUserMetadataBygroupRequest startIdx(Integer startIdx) {
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

  public PostUserMetadataBygroupRequest count(Integer count) {
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
    PostUserMetadataBygroupRequest postUserMetadataBygroupRequest = (PostUserMetadataBygroupRequest) o;
    return Objects.equals(this.header, postUserMetadataBygroupRequest.header) &&
        Objects.equals(this.groupId, postUserMetadataBygroupRequest.groupId) &&
        Objects.equals(this.startIdx, postUserMetadataBygroupRequest.startIdx) &&
        Objects.equals(this.count, postUserMetadataBygroupRequest.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, groupId, startIdx, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataBygroupRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
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

