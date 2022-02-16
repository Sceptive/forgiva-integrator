package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import com.sceptive.forgiva.integrator.services.gen.model.UserGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminUsergroupUpdateRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostAdminUsergroupUpdateRequest   {
  @JsonProperty("userGroup")
  private UserGroup userGroup;

  @JsonProperty("header")
  private Header header;

  public PostAdminUsergroupUpdateRequest userGroup(UserGroup userGroup) {
    this.userGroup = userGroup;
    return this;
  }

   /**
   * Get userGroup
   * @return userGroup
  **/
  @ApiModelProperty(value = "")
  public UserGroup getUserGroup() {
    return userGroup;
  }

  public void setUserGroup(UserGroup userGroup) {
    this.userGroup = userGroup;
  }

  public PostAdminUsergroupUpdateRequest header(Header header) {
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
    PostAdminUsergroupUpdateRequest postAdminUsergroupUpdateRequest = (PostAdminUsergroupUpdateRequest) o;
    return Objects.equals(this.userGroup, postAdminUsergroupUpdateRequest.userGroup) &&
        Objects.equals(this.header, postAdminUsergroupUpdateRequest.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userGroup, header);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminUsergroupUpdateRequest {\n");
    
    sb.append("    userGroup: ").append(toIndentedString(userGroup)).append("\n");
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

