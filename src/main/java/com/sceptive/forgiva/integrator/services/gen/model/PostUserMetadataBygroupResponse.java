package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Metadata;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostUserMetadataBygroupResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataBygroupResponse   {
  @JsonProperty("metadatas")
  private List<Metadata> metadatas = null;

  @JsonProperty("totalRecords")
  private Integer totalRecords;

  @JsonProperty("startIdx")
  private Integer startIdx;

  public PostUserMetadataBygroupResponse metadatas(List<Metadata> metadatas) {
    this.metadatas = metadatas;
    return this;
  }

  public PostUserMetadataBygroupResponse addMetadatasItem(Metadata metadatasItem) {
    if (this.metadatas == null) {
      this.metadatas = new ArrayList<Metadata>();
    }
    this.metadatas.add(metadatasItem);
    return this;
  }

   /**
   * Get metadatas
   * @return metadatas
  **/
  @ApiModelProperty(value = "")
  public List<Metadata> getMetadatas() {
    return metadatas;
  }

  public void setMetadatas(List<Metadata> metadatas) {
    this.metadatas = metadatas;
  }

  public PostUserMetadataBygroupResponse totalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
    return this;
  }

   /**
   * Total number of records of metadata within the group.
   * @return totalRecords
  **/
  @ApiModelProperty(value = "Total number of records of metadata within the group.")
  public Integer getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
  }

  public PostUserMetadataBygroupResponse startIdx(Integer startIdx) {
    this.startIdx = startIdx;
    return this;
  }

   /**
   * Start index of the records returned.
   * @return startIdx
  **/
  @ApiModelProperty(value = "Start index of the records returned.")
  public Integer getStartIdx() {
    return startIdx;
  }

  public void setStartIdx(Integer startIdx) {
    this.startIdx = startIdx;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserMetadataBygroupResponse postUserMetadataBygroupResponse = (PostUserMetadataBygroupResponse) o;
    return Objects.equals(this.metadatas, postUserMetadataBygroupResponse.metadatas) &&
        Objects.equals(this.totalRecords, postUserMetadataBygroupResponse.totalRecords) &&
        Objects.equals(this.startIdx, postUserMetadataBygroupResponse.startIdx);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadatas, totalRecords, startIdx);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataBygroupResponse {\n");
    
    sb.append("    metadatas: ").append(toIndentedString(metadatas)).append("\n");
    sb.append("    totalRecords: ").append(toIndentedString(totalRecords)).append("\n");
    sb.append("    startIdx: ").append(toIndentedString(startIdx)).append("\n");
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

