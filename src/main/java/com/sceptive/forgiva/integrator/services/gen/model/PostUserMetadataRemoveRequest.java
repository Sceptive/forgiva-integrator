package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserMetadataRemoveRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataRemoveRequest   {
  @JsonProperty("metadataId")
  private String metadataId;

  @JsonProperty("header")
  private Header header;

  public PostUserMetadataRemoveRequest metadataId(String metadataId) {
    this.metadataId = metadataId;
    return this;
  }

   /**
   * Get metadataId
   * @return metadataId
  **/
  @ApiModelProperty(required = true, value = "")
  public String getMetadataId() {
    return metadataId;
  }

  public void setMetadataId(String metadataId) {
    this.metadataId = metadataId;
  }

  public PostUserMetadataRemoveRequest header(Header header) {
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
    PostUserMetadataRemoveRequest postUserMetadataRemoveRequest = (PostUserMetadataRemoveRequest) o;
    return Objects.equals(this.metadataId, postUserMetadataRemoveRequest.metadataId) &&
        Objects.equals(this.header, postUserMetadataRemoveRequest.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadataId, header);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataRemoveRequest {\n");
    
    sb.append("    metadataId: ").append(toIndentedString(metadataId)).append("\n");
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

