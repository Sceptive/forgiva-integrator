package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostLoginRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-13T08:44:56.521+03:00[Europe/Istanbul]")
public class PostLoginRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  @JsonProperty("loginOverLdap")
  private Boolean loginOverLdap;

  public PostLoginRequest header(Header header) {
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

  public PostLoginRequest username(String username) {
    this.username = username;
    return this;
  }

   /**
   * User name encrypted with session public key
   * @return username
  **/
  @ApiModelProperty(value = "User name encrypted with session public key")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public PostLoginRequest password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Hash of password encrypted with session public key.
   * @return password
  **/
  @ApiModelProperty(value = "Hash of password encrypted with session public key.")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public PostLoginRequest loginOverLdap(Boolean loginOverLdap) {
    this.loginOverLdap = loginOverLdap;
    return this;
  }

   /**
   * Requests login over LDAP servers
   * @return loginOverLdap
  **/
  @ApiModelProperty(value = "Requests login over LDAP servers")
  public Boolean getLoginOverLdap() {
    return loginOverLdap;
  }

  public void setLoginOverLdap(Boolean loginOverLdap) {
    this.loginOverLdap = loginOverLdap;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostLoginRequest postLoginRequest = (PostLoginRequest) o;
    return Objects.equals(this.header, postLoginRequest.header) &&
        Objects.equals(this.username, postLoginRequest.username) &&
        Objects.equals(this.password, postLoginRequest.password) &&
        Objects.equals(this.loginOverLdap, postLoginRequest.loginOverLdap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, username, password, loginOverLdap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostLoginRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    loginOverLdap: ").append(toIndentedString(loginOverLdap)).append("\n");
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

