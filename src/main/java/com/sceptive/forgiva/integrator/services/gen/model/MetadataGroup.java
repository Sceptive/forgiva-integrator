package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Contains metadata group data specified with tree model.
 */
@ApiModel(description = "Contains metadata group data specified with tree model.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class MetadataGroup   {
  @JsonProperty("groupId")
  private String groupId;

  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("parentGroupId")
  private String parentGroupId;

  @JsonProperty("groupDescription")
  private String groupDescription;

  public MetadataGroup groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

   /**
   * Unique group id.
   * @return groupId
  **/
  @ApiModelProperty(value = "Unique group id.")
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public MetadataGroup groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

   /**
   * Group name such as 'Mail Accounts' or 'CRM Accounts'.
   * @return groupName
  **/
  @ApiModelProperty(value = "Group name such as 'Mail Accounts' or 'CRM Accounts'.")
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public MetadataGroup parentGroupId(String parentGroupId) {
    this.parentGroupId = parentGroupId;
    return this;
  }

   /**
   * Parent group id.
   * @return parentGroupId
  **/
  @ApiModelProperty(value = "Parent group id.")
  public String getParentGroupId() {
    return parentGroupId;
  }

  public void setParentGroupId(String parentGroupId) {
    this.parentGroupId = parentGroupId;
  }

  public MetadataGroup groupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
    return this;
  }

   /**
   * Description of the group such as 'Internal mail accounts' or '3rd party CRM application accounts'.
   * @return groupDescription
  **/
  @ApiModelProperty(value = "Description of the group such as 'Internal mail accounts' or '3rd party CRM application accounts'.")
  public String getGroupDescription() {
    return groupDescription;
  }

  public void setGroupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetadataGroup metadataGroup = (MetadataGroup) o;
    return Objects.equals(this.groupId, metadataGroup.groupId) &&
        Objects.equals(this.groupName, metadataGroup.groupName) &&
        Objects.equals(this.parentGroupId, metadataGroup.parentGroupId) &&
        Objects.equals(this.groupDescription, metadataGroup.groupDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, groupName, parentGroupId, groupDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetadataGroup {\n");
    
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    parentGroupId: ").append(toIndentedString(parentGroupId)).append("\n");
    sb.append("    groupDescription: ").append(toIndentedString(groupDescription)).append("\n");
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

