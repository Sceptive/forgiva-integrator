package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserSettingsSetRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-22T21:24:48.114+03:00[Europe/Istanbul]")
public class PostUserSettingsSetRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("key")
  private String key;

  @JsonProperty("value")
  private String value;

  public PostUserSettingsSetRequest header(Header header) {
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

  public PostUserSettingsSetRequest key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Setting key
   * @return key
  **/
  @ApiModelProperty(value = "Setting key")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public PostUserSettingsSetRequest value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Setting value to get saved
   * @return value
  **/
  @ApiModelProperty(value = "Setting value to get saved")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserSettingsSetRequest postUserSettingsSetRequest = (PostUserSettingsSetRequest) o;
    return Objects.equals(this.header, postUserSettingsSetRequest.header) &&
        Objects.equals(this.key, postUserSettingsSetRequest.key) &&
        Objects.equals(this.value, postUserSettingsSetRequest.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, key, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserSettingsSetRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

