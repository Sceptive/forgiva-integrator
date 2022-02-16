package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import com.sceptive.forgiva.integrator.services.gen.model.Metadata;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserMetadataAddRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserMetadataAddRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("metadata")
  private Metadata metadata;

  @JsonProperty("passwordLength")
  private Integer passwordLength;

  @JsonProperty("optUppercase")
  private Boolean optUppercase;

  @JsonProperty("optLowercase")
  private Boolean optLowercase;

  @JsonProperty("optNumbers")
  private Boolean optNumbers;

  @JsonProperty("optSymbols")
  private Boolean optSymbols;

  public PostUserMetadataAddRequest header(Header header) {
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

  public PostUserMetadataAddRequest metadata(Metadata metadata) {
    this.metadata = metadata;
    return this;
  }

   /**
   * Get metadata
   * @return metadata
  **/
  @ApiModelProperty(required = true, value = "")
  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public PostUserMetadataAddRequest passwordLength(Integer passwordLength) {
    this.passwordLength = passwordLength;
    return this;
  }

   /**
   * Password length of the generated password.
   * @return passwordLength
  **/
  @ApiModelProperty(required = true, value = "Password length of the generated password.")
  public Integer getPasswordLength() {
    return passwordLength;
  }

  public void setPasswordLength(Integer passwordLength) {
    this.passwordLength = passwordLength;
  }

  public PostUserMetadataAddRequest optUppercase(Boolean optUppercase) {
    this.optUppercase = optUppercase;
    return this;
  }

   /**
   * Password should have upper case letters.
   * @return optUppercase
  **/
  @ApiModelProperty(required = true, value = "Password should have upper case letters.")
  public Boolean getOptUppercase() {
    return optUppercase;
  }

  public void setOptUppercase(Boolean optUppercase) {
    this.optUppercase = optUppercase;
  }

  public PostUserMetadataAddRequest optLowercase(Boolean optLowercase) {
    this.optLowercase = optLowercase;
    return this;
  }

   /**
   * Password should have lower case letters.
   * @return optLowercase
  **/
  @ApiModelProperty(required = true, value = "Password should have lower case letters.")
  public Boolean getOptLowercase() {
    return optLowercase;
  }

  public void setOptLowercase(Boolean optLowercase) {
    this.optLowercase = optLowercase;
  }

  public PostUserMetadataAddRequest optNumbers(Boolean optNumbers) {
    this.optNumbers = optNumbers;
    return this;
  }

   /**
   * Password should have numbers.
   * @return optNumbers
  **/
  @ApiModelProperty(required = true, value = "Password should have numbers.")
  public Boolean getOptNumbers() {
    return optNumbers;
  }

  public void setOptNumbers(Boolean optNumbers) {
    this.optNumbers = optNumbers;
  }

  public PostUserMetadataAddRequest optSymbols(Boolean optSymbols) {
    this.optSymbols = optSymbols;
    return this;
  }

   /**
   * Password should have symbols.
   * @return optSymbols
  **/
  @ApiModelProperty(required = true, value = "Password should have symbols.")
  public Boolean getOptSymbols() {
    return optSymbols;
  }

  public void setOptSymbols(Boolean optSymbols) {
    this.optSymbols = optSymbols;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserMetadataAddRequest postUserMetadataAddRequest = (PostUserMetadataAddRequest) o;
    return Objects.equals(this.header, postUserMetadataAddRequest.header) &&
        Objects.equals(this.metadata, postUserMetadataAddRequest.metadata) &&
        Objects.equals(this.passwordLength, postUserMetadataAddRequest.passwordLength) &&
        Objects.equals(this.optUppercase, postUserMetadataAddRequest.optUppercase) &&
        Objects.equals(this.optLowercase, postUserMetadataAddRequest.optLowercase) &&
        Objects.equals(this.optNumbers, postUserMetadataAddRequest.optNumbers) &&
        Objects.equals(this.optSymbols, postUserMetadataAddRequest.optSymbols);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, metadata, passwordLength, optUppercase, optLowercase, optNumbers, optSymbols);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserMetadataAddRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    passwordLength: ").append(toIndentedString(passwordLength)).append("\n");
    sb.append("    optUppercase: ").append(toIndentedString(optUppercase)).append("\n");
    sb.append("    optLowercase: ").append(toIndentedString(optLowercase)).append("\n");
    sb.append("    optNumbers: ").append(toIndentedString(optNumbers)).append("\n");
    sb.append("    optSymbols: ").append(toIndentedString(optSymbols)).append("\n");
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

