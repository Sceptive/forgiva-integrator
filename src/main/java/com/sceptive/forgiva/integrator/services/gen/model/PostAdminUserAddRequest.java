package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import com.sceptive.forgiva.integrator.services.gen.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminUserAddRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostAdminUserAddRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("user")
  private User user;

  @JsonProperty("userGroupId")
  private Integer userGroupId;

  @JsonProperty("password")
  private String password;

  public PostAdminUserAddRequest header(Header header) {
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

  public PostAdminUserAddRequest user(User user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(required = true, value = "")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public PostAdminUserAddRequest userGroupId(Integer userGroupId) {
    this.userGroupId = userGroupId;
    return this;
  }

   /**
   * User group id to add into.
   * @return userGroupId
  **/
  @ApiModelProperty(value = "User group id to add into.")
  public Integer getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(Integer userGroupId) {
    this.userGroupId = userGroupId;
  }

  public PostAdminUserAddRequest password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Hash of password encrypted with session public key.
   * @return password
  **/
  @ApiModelProperty(value = "Hash of password encrypted with session public key.")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminUserAddRequest postAdminUserAddRequest = (PostAdminUserAddRequest) o;
    return Objects.equals(this.header, postAdminUserAddRequest.header) &&
        Objects.equals(this.user, postAdminUserAddRequest.user) &&
        Objects.equals(this.userGroupId, postAdminUserAddRequest.userGroupId) &&
        Objects.equals(this.password, postAdminUserAddRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, user, userGroupId, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminUserAddRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    userGroupId: ").append(toIndentedString(userGroupId)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

