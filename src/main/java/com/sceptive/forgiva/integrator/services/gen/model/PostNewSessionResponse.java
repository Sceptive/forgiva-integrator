package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.LogonState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostNewSessionResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-13T08:44:56.521+03:00[Europe/Istanbul]")
public class PostNewSessionResponse   {
  @JsonProperty("logonState")
  private LogonState logonState;

  @JsonProperty("hshAlg")
  private String hshAlg;

  @JsonProperty("hshSalt")
  private String hshSalt;

  @JsonProperty("sessionPk")
  private String sessionPk;

  @JsonProperty("newSessionId")
  private String newSessionId;

  @JsonProperty("ldapEnabled")
  private Boolean ldapEnabled;

  public PostNewSessionResponse logonState(LogonState logonState) {
    this.logonState = logonState;
    return this;
  }

   /**
   * Get logonState
   * @return logonState
  **/
  @ApiModelProperty(value = "")
  public LogonState getLogonState() {
    return logonState;
  }

  public void setLogonState(LogonState logonState) {
    this.logonState = logonState;
  }

  public PostNewSessionResponse hshAlg(String hshAlg) {
    this.hshAlg = hshAlg;
    return this;
  }

   /**
   * Hashing algorithm chosen by server required to get used by client to provide hashed data to the server.
   * @return hshAlg
  **/
  @ApiModelProperty(value = "Hashing algorithm chosen by server required to get used by client to provide hashed data to the server.")
  public String getHshAlg() {
    return hshAlg;
  }

  public void setHshAlg(String hshAlg) {
    this.hshAlg = hshAlg;
  }

  public PostNewSessionResponse hshSalt(String hshSalt) {
    this.hshSalt = hshSalt;
    return this;
  }

   /**
   * Unique salt value tied with session which will be required to get used with hash algorithm on the client side.
   * @return hshSalt
  **/
  @ApiModelProperty(value = "Unique salt value tied with session which will be required to get used with hash algorithm on the client side.")
  public String getHshSalt() {
    return hshSalt;
  }

  public void setHshSalt(String hshSalt) {
    this.hshSalt = hshSalt;
  }

  public PostNewSessionResponse sessionPk(String sessionPk) {
    this.sessionPk = sessionPk;
    return this;
  }

   /**
   * Session public key to encrypt critical data.
   * @return sessionPk
  **/
  @ApiModelProperty(value = "Session public key to encrypt critical data.")
  public String getSessionPk() {
    return sessionPk;
  }

  public void setSessionPk(String sessionPk) {
    this.sessionPk = sessionPk;
  }

  public PostNewSessionResponse newSessionId(String newSessionId) {
    this.newSessionId = newSessionId;
    return this;
  }

   /**
   * Returns null if sessionId is valid or new sessionId for renewal or initialization.
   * @return newSessionId
  **/
  @ApiModelProperty(value = "Returns null if sessionId is valid or new sessionId for renewal or initialization.")
  public String getNewSessionId() {
    return newSessionId;
  }

  public void setNewSessionId(String newSessionId) {
    this.newSessionId = newSessionId;
  }

  public PostNewSessionResponse ldapEnabled(Boolean ldapEnabled) {
    this.ldapEnabled = ldapEnabled;
    return this;
  }

   /**
   * Returns whether LDAP login is enabled or not
   * @return ldapEnabled
  **/
  @ApiModelProperty(value = "Returns whether LDAP login is enabled or not")
  public Boolean getLdapEnabled() {
    return ldapEnabled;
  }

  public void setLdapEnabled(Boolean ldapEnabled) {
    this.ldapEnabled = ldapEnabled;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostNewSessionResponse postNewSessionResponse = (PostNewSessionResponse) o;
    return Objects.equals(this.logonState, postNewSessionResponse.logonState) &&
        Objects.equals(this.hshAlg, postNewSessionResponse.hshAlg) &&
        Objects.equals(this.hshSalt, postNewSessionResponse.hshSalt) &&
        Objects.equals(this.sessionPk, postNewSessionResponse.sessionPk) &&
        Objects.equals(this.newSessionId, postNewSessionResponse.newSessionId) &&
        Objects.equals(this.ldapEnabled, postNewSessionResponse.ldapEnabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logonState, hshAlg, hshSalt, sessionPk, newSessionId, ldapEnabled);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostNewSessionResponse {\n");
    
    sb.append("    logonState: ").append(toIndentedString(logonState)).append("\n");
    sb.append("    hshAlg: ").append(toIndentedString(hshAlg)).append("\n");
    sb.append("    hshSalt: ").append(toIndentedString(hshSalt)).append("\n");
    sb.append("    sessionPk: ").append(toIndentedString(sessionPk)).append("\n");
    sb.append("    newSessionId: ").append(toIndentedString(newSessionId)).append("\n");
    sb.append("    ldapEnabled: ").append(toIndentedString(ldapEnabled)).append("\n");
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

