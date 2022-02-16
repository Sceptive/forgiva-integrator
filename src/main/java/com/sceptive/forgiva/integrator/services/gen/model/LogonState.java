package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * LogonState
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-02T20:19:30.778+03:00[Europe/Istanbul]")
public class LogonState   {
  @JsonProperty("authenticated")
  private Boolean authenticated;

  @JsonProperty("isAdmin")
  private Boolean isAdmin;

  public LogonState authenticated(Boolean authenticated) {
    this.authenticated = authenticated;
    return this;
  }

   /**
   * If client is already authenticated returns true.
   * @return authenticated
  **/
  @ApiModelProperty(value = "If client is already authenticated returns true.")
  public Boolean getAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(Boolean authenticated) {
    this.authenticated = authenticated;
  }

  public LogonState isAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
    return this;
  }

   /**
   * Return true if user has administrator rights.
   * @return isAdmin
  **/
  @ApiModelProperty(value = "Return true if user has administrator rights.")
  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogonState logonState = (LogonState) o;
    return Objects.equals(this.authenticated, logonState.authenticated) &&
        Objects.equals(this.isAdmin, logonState.isAdmin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authenticated, isAdmin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogonState {\n");
    
    sb.append("    authenticated: ").append(toIndentedString(authenticated)).append("\n");
    sb.append("    isAdmin: ").append(toIndentedString(isAdmin)).append("\n");
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

