package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Header
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class Header   {
  @JsonProperty("sessionId")
  private String sessionId;

  @JsonProperty("clientAgent")
  private String clientAgent;

  public Header sessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

   /**
   * Get sessionId
   * @return sessionId
  **/
  @ApiModelProperty(value = "")
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Header clientAgent(String clientAgent) {
    this.clientAgent = clientAgent;
    return this;
  }

   /**
   * Get clientAgent
   * @return clientAgent
  **/
  @ApiModelProperty(value = "")
  public String getClientAgent() {
    return clientAgent;
  }

  public void setClientAgent(String clientAgent) {
    this.clientAgent = clientAgent;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Header header = (Header) o;
    return Objects.equals(this.sessionId, header.sessionId) &&
        Objects.equals(this.clientAgent, header.clientAgent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessionId, clientAgent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Header {\n");
    
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    clientAgent: ").append(toIndentedString(clientAgent)).append("\n");
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

