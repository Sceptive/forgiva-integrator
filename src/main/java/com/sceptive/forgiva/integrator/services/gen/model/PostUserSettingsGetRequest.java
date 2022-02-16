package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserSettingsGetRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-22T21:24:48.114+03:00[Europe/Istanbul]")
public class PostUserSettingsGetRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("key")
  private String key;

  public PostUserSettingsGetRequest header(Header header) {
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

  public PostUserSettingsGetRequest key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Setting key to query
   * @return key
  **/
  @ApiModelProperty(value = "Setting key to query")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserSettingsGetRequest postUserSettingsGetRequest = (PostUserSettingsGetRequest) o;
    return Objects.equals(this.header, postUserSettingsGetRequest.header) &&
        Objects.equals(this.key, postUserSettingsGetRequest.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, key);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserSettingsGetRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
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

