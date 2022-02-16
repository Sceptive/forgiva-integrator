package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostAdminUserByusergroupResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-04-30T09:29:43.401+03:00[Europe/Istanbul]")
public class PostAdminUserByusergroupResponse   {
  @JsonProperty("users")
  private List<User> users = null;

  @JsonProperty("totalUserCount")
  private Integer totalUserCount;

  public PostAdminUserByusergroupResponse users(List<User> users) {
    this.users = users;
    return this;
  }

  public PostAdminUserByusergroupResponse addUsersItem(User usersItem) {
    if (this.users == null) {
      this.users = new ArrayList<User>();
    }
    this.users.add(usersItem);
    return this;
  }

   /**
   * Get users
   * @return users
  **/
  @ApiModelProperty(value = "")
  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public PostAdminUserByusergroupResponse totalUserCount(Integer totalUserCount) {
    this.totalUserCount = totalUserCount;
    return this;
  }

   /**
   * Total number of users within specific group
   * @return totalUserCount
  **/
  @ApiModelProperty(value = "Total number of users within specific group")
  public Integer getTotalUserCount() {
    return totalUserCount;
  }

  public void setTotalUserCount(Integer totalUserCount) {
    this.totalUserCount = totalUserCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminUserByusergroupResponse postAdminUserByusergroupResponse = (PostAdminUserByusergroupResponse) o;
    return Objects.equals(this.users, postAdminUserByusergroupResponse.users) &&
        Objects.equals(this.totalUserCount, postAdminUserByusergroupResponse.totalUserCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(users, totalUserCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminUserByusergroupResponse {\n");
    
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    totalUserCount: ").append(toIndentedString(totalUserCount)).append("\n");
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

