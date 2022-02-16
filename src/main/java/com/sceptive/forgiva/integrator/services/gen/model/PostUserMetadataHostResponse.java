package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataHostResponseHosts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostUserMetadataHostResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataHostResponse   {
  @JsonProperty("hosts")
  private List<PostUserMetadataHostResponseHosts> hosts = null;

  public PostUserMetadataHostResponse hosts(List<PostUserMetadataHostResponseHosts> hosts) {
    this.hosts = hosts;
    return this;
  }

  public PostUserMetadataHostResponse addHostsItem(PostUserMetadataHostResponseHosts hostsItem) {
    if (this.hosts == null) {
      this.hosts = new ArrayList<PostUserMetadataHostResponseHosts>();
    }
    this.hosts.add(hostsItem);
    return this;
  }

   /**
   * Get hosts
   * @return hosts
  **/
  @ApiModelProperty(value = "")
  public List<PostUserMetadataHostResponseHosts> getHosts() {
    return hosts;
  }

  public void setHosts(List<PostUserMetadataHostResponseHosts> hosts) {
    this.hosts = hosts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserMetadataHostResponse postUserMetadataHostResponse = (PostUserMetadataHostResponse) o;
    return Objects.equals(this.hosts, postUserMetadataHostResponse.hosts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hosts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataHostResponse {\n");
    
    sb.append("    hosts: ").append(toIndentedString(hosts)).append("\n");
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

