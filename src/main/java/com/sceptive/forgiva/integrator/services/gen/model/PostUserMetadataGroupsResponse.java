package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.MetadataGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostUserMetadataGroupsResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataGroupsResponse   {
  @JsonProperty("groups")
  private List<MetadataGroup> groups = null;

  public PostUserMetadataGroupsResponse groups(List<MetadataGroup> groups) {
    this.groups = groups;
    return this;
  }

  public PostUserMetadataGroupsResponse addGroupsItem(MetadataGroup groupsItem) {
    if (this.groups == null) {
      this.groups = new ArrayList<MetadataGroup>();
    }
    this.groups.add(groupsItem);
    return this;
  }

   /**
   * Get groups
   * @return groups
  **/
  @ApiModelProperty(value = "")
  public List<MetadataGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<MetadataGroup> groups) {
    this.groups = groups;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserMetadataGroupsResponse postUserMetadataGroupsResponse = (PostUserMetadataGroupsResponse) o;
    return Objects.equals(this.groups, postUserMetadataGroupsResponse.groups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataGroupsResponse {\n");
    
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
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

