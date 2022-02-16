package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserSettingsGetResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-22T21:24:48.114+03:00[Europe/Istanbul]")
public class PostUserSettingsGetResponse   {
  @JsonProperty("value")
  private String value;

  public PostUserSettingsGetResponse value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Value of the setting queried by the key in request
   * @return value
  **/
  @ApiModelProperty(required = true, value = "Value of the setting queried by the key in request")
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
    PostUserSettingsGetResponse postUserSettingsGetResponse = (PostUserSettingsGetResponse) o;
    return Objects.equals(this.value, postUserSettingsGetResponse.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserSettingsGetResponse {\n");
    
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

