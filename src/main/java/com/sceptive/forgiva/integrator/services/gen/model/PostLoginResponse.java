package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.LogonState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostLoginResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-02T20:19:30.778+03:00[Europe/Istanbul]")
public class PostLoginResponse   {
  @JsonProperty("logonState")
  private LogonState logonState;

  @JsonProperty("twoFARequired")
  private Boolean twoFARequired;

  public PostLoginResponse logonState(LogonState logonState) {
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

  public PostLoginResponse twoFARequired(Boolean twoFARequired) {
    this.twoFARequired = twoFARequired;
    return this;
  }

   /**
   * 2FA service is required or not.
   * @return twoFARequired
  **/
  @ApiModelProperty(value = "2FA service is required or not.")
  public Boolean getTwoFARequired() {
    return twoFARequired;
  }

  public void setTwoFARequired(Boolean twoFARequired) {
    this.twoFARequired = twoFARequired;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostLoginResponse postLoginResponse = (PostLoginResponse) o;
    return Objects.equals(this.logonState, postLoginResponse.logonState) &&
        Objects.equals(this.twoFARequired, postLoginResponse.twoFARequired);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logonState, twoFARequired);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostLoginResponse {\n");
    
    sb.append("    logonState: ").append(toIndentedString(logonState)).append("\n");
    sb.append("    twoFARequired: ").append(toIndentedString(twoFARequired)).append("\n");
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

