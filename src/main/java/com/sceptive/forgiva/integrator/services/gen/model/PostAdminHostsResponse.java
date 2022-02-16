package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Host;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PostAdminHostsResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T08:54:49.401+03:00[Europe/Istanbul]")
public class PostAdminHostsResponse   {
  @JsonProperty("hosts")
  private List<Host> hosts = null;

  public PostAdminHostsResponse hosts(List<Host> hosts) {
    this.hosts = hosts;
    return this;
  }

  public PostAdminHostsResponse addHostsItem(Host hostsItem) {
    if (this.hosts == null) {
      this.hosts = new ArrayList<Host>();
    }
    this.hosts.add(hostsItem);
    return this;
  }

   /**
   * Get hosts
   * @return hosts
  **/
  @ApiModelProperty(value = "")
  public List<Host> getHosts() {
    return hosts;
  }

  public void setHosts(List<Host> hosts) {
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
    PostAdminHostsResponse postAdminHostsResponse = (PostAdminHostsResponse) o;
    return Objects.equals(this.hosts, postAdminHostsResponse.hosts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hosts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminHostsResponse {\n");
    
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

