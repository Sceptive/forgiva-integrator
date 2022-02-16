package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminApplicationRemoveRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:47:12.794+03:00[Europe/Istanbul]")
public class PostAdminApplicationRemoveRequest   {
  @JsonProperty("applicationId")
  private String applicationId;

  @JsonProperty("header")
  private Header header;

  public PostAdminApplicationRemoveRequest applicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

   /**
   * Specifies application id to get removed.
   * @return applicationId
  **/
  @ApiModelProperty(value = "Specifies application id to get removed.")
  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public PostAdminApplicationRemoveRequest header(Header header) {
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
    PostAdminApplicationRemoveRequest postAdminApplicationRemoveRequest = (PostAdminApplicationRemoveRequest) o;
    return Objects.equals(this.applicationId, postAdminApplicationRemoveRequest.applicationId) &&
        Objects.equals(this.header, postAdminApplicationRemoveRequest.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, header);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminApplicationRemoveRequest {\n");
    
    sb.append("    applicationId: ").append(toIndentedString(applicationId)).append("\n");
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

