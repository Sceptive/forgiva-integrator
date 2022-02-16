package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Application;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminApplicationAddRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:47:12.794+03:00[Europe/Istanbul]")
public class PostAdminApplicationAddRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("application")
  private Application application = null;

  @JsonProperty("hostId")
  private Integer hostId;

  public PostAdminApplicationAddRequest header(Header header) {
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

  public PostAdminApplicationAddRequest application(Application application) {
    this.application = application;
    return this;
  }

   /**
   * Get application
   * @return application
  **/
  @ApiModelProperty(required = true, value = "")
  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public PostAdminApplicationAddRequest hostId(Integer hostId) {
    this.hostId = hostId;
    return this;
  }

   /**
   * Host id to add into.
   * @return hostId
  **/
  @ApiModelProperty(value = "Host id to add into.")
  public Integer getHostId() {
    return hostId;
  }

  public void setHostId(Integer hostId) {
    this.hostId = hostId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminApplicationAddRequest postAdminApplicationAddRequest = (PostAdminApplicationAddRequest) o;
    return Objects.equals(this.header, postAdminApplicationAddRequest.header) &&
        Objects.equals(this.application, postAdminApplicationAddRequest.application) &&
        Objects.equals(this.hostId, postAdminApplicationAddRequest.hostId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, application, hostId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminApplicationAddRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
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

