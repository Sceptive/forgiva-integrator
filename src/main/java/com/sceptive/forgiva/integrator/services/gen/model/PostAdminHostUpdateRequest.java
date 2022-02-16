package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import com.sceptive.forgiva.integrator.services.gen.model.Host;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminHostUpdateRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:47:12.794+03:00[Europe/Istanbul]")
public class PostAdminHostUpdateRequest   {
  @JsonProperty("host")
  private Host host = null;

  @JsonProperty("header")
  private Header header;

  public PostAdminHostUpdateRequest host(Host host) {
    this.host = host;
    return this;
  }

   /**
   * Get host
   * @return host
  **/
  @ApiModelProperty(value = "")
  public Host getHost() {
    return host;
  }

  public void setHost(Host host) {
    this.host = host;
  }

  public PostAdminHostUpdateRequest header(Header header) {
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
    PostAdminHostUpdateRequest postAdminHostUpdateRequest = (PostAdminHostUpdateRequest) o;
    return Objects.equals(this.host, postAdminHostUpdateRequest.host) &&
        Objects.equals(this.header, postAdminHostUpdateRequest.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, header);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminHostUpdateRequest {\n");
    
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
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

