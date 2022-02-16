package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserMetadataSearchRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataSearchRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("criteria")
  private String criteria;

  public PostUserMetadataSearchRequest header(Header header) {
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

  public PostUserMetadataSearchRequest criteria(String criteria) {
    this.criteria = criteria;
    return this;
  }

   /**
   * Criteria to search within metadatas.
   * @return criteria
  **/
  @ApiModelProperty(required = true, value = "Criteria to search within metadatas.")
  public String getCriteria() {
    return criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserMetadataSearchRequest postUserMetadataSearchRequest = (PostUserMetadataSearchRequest) o;
    return Objects.equals(this.header, postUserMetadataSearchRequest.header) &&
        Objects.equals(this.criteria, postUserMetadataSearchRequest.criteria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, criteria);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataSearchRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    criteria: ").append(toIndentedString(criteria)).append("\n");
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

