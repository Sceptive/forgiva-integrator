package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserGenerateResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserGenerateResponse   {
  @JsonProperty("result")
  private OperationResult result;

  @JsonProperty("generatedPassword")
  private String generatedPassword;

  public PostUserGenerateResponse result(OperationResult result) {
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @ApiModelProperty(required = true, value = "")
  public OperationResult getResult() {
    return result;
  }

  public void setResult(OperationResult result) {
    this.result = result;
  }

  public PostUserGenerateResponse generatedPassword(String generatedPassword) {
    this.generatedPassword = generatedPassword;
    return this;
  }

   /**
   * Generated password in hex representation.
   * @return generatedPassword
  **/
  @ApiModelProperty(required = true, value = "Generated password in hex representation.")
  public String getGeneratedPassword() {
    return generatedPassword;
  }

  public void setGeneratedPassword(String generatedPassword) {
    this.generatedPassword = generatedPassword;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserGenerateResponse postUserGenerateResponse = (PostUserGenerateResponse) o;
    return Objects.equals(this.result, postUserGenerateResponse.result) &&
        Objects.equals(this.generatedPassword, postUserGenerateResponse.generatedPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, generatedPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserGenerateResponse {\n");
    
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    generatedPassword: ").append(toIndentedString(generatedPassword)).append("\n");
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

