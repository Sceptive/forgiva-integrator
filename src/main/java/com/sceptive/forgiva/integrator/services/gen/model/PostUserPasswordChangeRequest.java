package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserPasswordChangeRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-22T21:24:48.114+03:00[Europe/Istanbul]")
public class PostUserPasswordChangeRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("oldPassword")
  private String oldPassword;

  @JsonProperty("newPassword")
  private String newPassword;

  public PostUserPasswordChangeRequest header(Header header) {
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

  public PostUserPasswordChangeRequest oldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
    return this;
  }

   /**
   * User's old password encrypted with session key
   * @return oldPassword
  **/
  @ApiModelProperty(value = "User's old password encrypted with session key")
  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public PostUserPasswordChangeRequest newPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

   /**
   * User's new password encrypted with session key
   * @return newPassword
  **/
  @ApiModelProperty(value = "User's new password encrypted with session key")
  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserPasswordChangeRequest postUserPasswordChangeRequest = (PostUserPasswordChangeRequest) o;
    return Objects.equals(this.header, postUserPasswordChangeRequest.header) &&
        Objects.equals(this.oldPassword, postUserPasswordChangeRequest.oldPassword) &&
        Objects.equals(this.newPassword, postUserPasswordChangeRequest.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, oldPassword, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserPasswordChangeRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    oldPassword: ").append(toIndentedString(oldPassword)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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

