package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostLogin2faRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostLogin2faRequest   {
  @JsonProperty("twoFACode")
  private String twoFACode;

  @JsonProperty("header")
  private Header header;

  public PostLogin2faRequest twoFACode(String twoFACode) {
    this.twoFACode = twoFACode;
    return this;
  }

   /**
   * 2FA code hashed with specified hash algorithm in /session service.
   * @return twoFACode
  **/
  @ApiModelProperty(value = "2FA code hashed with specified hash algorithm in /session service.")
  public String getTwoFACode() {
    return twoFACode;
  }

  public void setTwoFACode(String twoFACode) {
    this.twoFACode = twoFACode;
  }

  public PostLogin2faRequest header(Header header) {
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
    PostLogin2faRequest postLogin2faRequest = (PostLogin2faRequest) o;
    return Objects.equals(this.twoFACode, postLogin2faRequest.twoFACode) &&
        Objects.equals(this.header, postLogin2faRequest.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(twoFACode, header);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostLogin2faRequest {\n");
    
    sb.append("    twoFACode: ").append(toIndentedString(twoFACode)).append("\n");
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

