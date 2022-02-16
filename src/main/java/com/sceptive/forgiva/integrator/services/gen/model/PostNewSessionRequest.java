package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostNewSessionRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostNewSessionRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("clientPk")
  private String clientPk;

  public PostNewSessionRequest header(Header header) {
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

  public PostNewSessionRequest clientPk(String clientPk) {
    this.clientPk = clientPk;
    return this;
  }

   /**
   * Client public key for server to encrypt critical data to be sent to client.
   * @return clientPk
  **/
  @ApiModelProperty(value = "Client public key for server to encrypt critical data to be sent to client.")
  public String getClientPk() {
    return clientPk;
  }

  public void setClientPk(String clientPk) {
    this.clientPk = clientPk;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostNewSessionRequest postNewSessionRequest = (PostNewSessionRequest) o;
    return Objects.equals(this.header, postNewSessionRequest.header) &&
        Objects.equals(this.clientPk, postNewSessionRequest.clientPk);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, clientPk);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostNewSessionRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    clientPk: ").append(toIndentedString(clientPk)).append("\n");
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

