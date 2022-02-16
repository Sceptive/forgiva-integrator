package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.UserGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostAdminUsergroupsResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostAdminUsergroupsResponse   {
  @JsonProperty("userGroups")
  private List<UserGroup> userGroups = null;

  public PostAdminUsergroupsResponse userGroups(List<UserGroup> userGroups) {
    this.userGroups = userGroups;
    return this;
  }

  public PostAdminUsergroupsResponse addUserGroupsItem(UserGroup userGroupsItem) {
    if (this.userGroups == null) {
      this.userGroups = new ArrayList<UserGroup>();
    }
    this.userGroups.add(userGroupsItem);
    return this;
  }

   /**
   * Get userGroups
   * @return userGroups
  **/
  @ApiModelProperty(value = "")
  public List<UserGroup> getUserGroups() {
    return userGroups;
  }

  public void setUserGroups(List<UserGroup> userGroups) {
    this.userGroups = userGroups;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminUsergroupsResponse postAdminUsergroupsResponse = (PostAdminUsergroupsResponse) o;
    return Objects.equals(this.userGroups, postAdminUsergroupsResponse.userGroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userGroups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminUsergroupsResponse {\n");
    
    sb.append("    userGroups: ").append(toIndentedString(userGroups)).append("\n");
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

