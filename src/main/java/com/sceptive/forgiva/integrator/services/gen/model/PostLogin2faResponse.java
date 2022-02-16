package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostLogin2faResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostLogin2faResponse   {
  @JsonProperty("authenticated")
  private Boolean authenticated;

  public PostLogin2faResponse authenticated(Boolean authenticated) {
    this.authenticated = authenticated;
    return this;
  }

   /**
   * Points out whether authentication is succedded or not.
   * @return authenticated
  **/
  @ApiModelProperty(value = "Points out whether authentication is succedded or not.")
  public Boolean getAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(Boolean authenticated) {
    this.authenticated = authenticated;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostLogin2faResponse postLogin2faResponse = (PostLogin2faResponse) o;
    return Objects.equals(this.authenticated, postLogin2faResponse.authenticated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authenticated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostLogin2faResponse {\n");
    
    sb.append("    authenticated: ").append(toIndentedString(authenticated)).append("\n");
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

