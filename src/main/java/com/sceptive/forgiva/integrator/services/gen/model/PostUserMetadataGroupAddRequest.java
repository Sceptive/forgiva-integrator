package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import com.sceptive.forgiva.integrator.services.gen.model.MetadataGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserMetadataGroupAddRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataGroupAddRequest   {
  @JsonProperty("group")
  private MetadataGroup group;

  @JsonProperty("header")
  private Header header;

  public PostUserMetadataGroupAddRequest group(MetadataGroup group) {
    this.group = group;
    return this;
  }

   /**
   * Get group
   * @return group
  **/
  @ApiModelProperty(value = "")
  public MetadataGroup getGroup() {
    return group;
  }

  public void setGroup(MetadataGroup group) {
    this.group = group;
  }

  public PostUserMetadataGroupAddRequest header(Header header) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserMetadataGroupAddRequest postUserMetadataGroupAddRequest = (PostUserMetadataGroupAddRequest) o;
    return Objects.equals(this.group, postUserMetadataGroupAddRequest.group) &&
        Objects.equals(this.header, postUserMetadataGroupAddRequest.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(group, header);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataGroupAddRequest {\n");
    
    sb.append("    group: ").append(toIndentedString(group)).append("\n");
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
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

