package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Contains users group data specified with tree model.
 */
@ApiModel(description = "Contains users group data specified with tree model.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class UserGroup   {
  @JsonProperty("userGroupId")
  private String userGroupId;

  @JsonProperty("userGroupName")
  private String userGroupName;

  @JsonProperty("parentUserGroupId")
  private String parentUserGroupId;

  @JsonProperty("userGroupDescription")
  private String userGroupDescription;

  public UserGroup userGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
    return this;
  }

   /**
   * Get userGroupId
   * @return userGroupId
  **/
  @ApiModelProperty(value = "")
  public String getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
  }

  public UserGroup userGroupName(String userGroupName) {
    this.userGroupName = userGroupName;
    return this;
  }

   /**
   * Get userGroupName
   * @return userGroupName
  **/
  @ApiModelProperty(value = "")
  public String getUserGroupName() {
    return userGroupName;
  }

  public void setUserGroupName(String userGroupName) {
    this.userGroupName = userGroupName;
  }

  public UserGroup parentUserGroupId(String parentUserGroupId) {
    this.parentUserGroupId = parentUserGroupId;
    return this;
  }

   /**
   * Get parentUserGroupId
   * @return parentUserGroupId
  **/
  @ApiModelProperty(value = "")
  public String getParentUserGroupId() {
    return parentUserGroupId;
  }

  public void setParentUserGroupId(String parentUserGroupId) {
    this.parentUserGroupId = parentUserGroupId;
  }

  public UserGroup userGroupDescription(String userGroupDescription) {
    this.userGroupDescription = userGroupDescription;
    return this;
  }

   /**
   * Get userGroupDescription
   * @return userGroupDescription
  **/
  @ApiModelProperty(value = "")
  public String getUserGroupDescription() {
    return userGroupDescription;
  }

  public void setUserGroupDescription(String userGroupDescription) {
    this.userGroupDescription = userGroupDescription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserGroup userGroup = (UserGroup) o;
    return Objects.equals(this.userGroupId, userGroup.userGroupId) &&
        Objects.equals(this.userGroupName, userGroup.userGroupName) &&
        Objects.equals(this.parentUserGroupId, userGroup.parentUserGroupId) &&
        Objects.equals(this.userGroupDescription, userGroup.userGroupDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userGroupId, userGroupName, parentUserGroupId, userGroupDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserGroup {\n");
    
    sb.append("    userGroupId: ").append(toIndentedString(userGroupId)).append("\n");
    sb.append("    userGroupName: ").append(toIndentedString(userGroupName)).append("\n");
    sb.append("    parentUserGroupId: ").append(toIndentedString(parentUserGroupId)).append("\n");
    sb.append("    userGroupDescription: ").append(toIndentedString(userGroupDescription)).append("\n");
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

