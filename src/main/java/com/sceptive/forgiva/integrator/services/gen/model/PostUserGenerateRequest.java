package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostUserGenerateRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class PostUserGenerateRequest   {
  @JsonProperty("header")
  private Header header;

  @JsonProperty("metadataId")
  private String metadataId;

  @JsonProperty("masterKey")
  private String masterKey;

  @JsonProperty("visualConfirmation")
  private String visualConfirmation;

  @JsonProperty("renewalDate")
  private String renewalDate;

  public PostUserGenerateRequest header(Header header) {
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

  public PostUserGenerateRequest metadataId(String metadataId) {
    this.metadataId = metadataId;
    return this;
  }

   /**
   * Get metadataId
   * @return metadataId
  **/
  @ApiModelProperty(required = true, value = "")
  public String getMetadataId() {
    return metadataId;
  }

  public void setMetadataId(String metadataId) {
    this.metadataId = metadataId;
  }

  public PostUserGenerateRequest masterKey(String masterKey) {
    this.masterKey = masterKey;
    return this;
  }

   /**
   * Hex of encrypted master key with session public key.
   * @return masterKey
  **/
  @ApiModelProperty(required = true, value = "Hex of encrypted master key with session public key.")
  public String getMasterKey() {
    return masterKey;
  }

  public void setMasterKey(String masterKey) {
    this.masterKey = masterKey;
  }

  public PostUserGenerateRequest visualConfirmation(String visualConfirmation) {
    this.visualConfirmation = visualConfirmation;
    return this;
  }

   /**
   * Hex of the encrypted visual confirmation data with public session key.
   * @return visualConfirmation
  **/
  @ApiModelProperty(required = true, value = "Hex of the encrypted visual confirmation data with public session key.")
  public String getVisualConfirmation() {
    return visualConfirmation;
  }

  public void setVisualConfirmation(String visualConfirmation) {
    this.visualConfirmation = visualConfirmation;
  }

  public PostUserGenerateRequest renewalDate(String renewalDate) {
    this.renewalDate = renewalDate;
    return this;
  }

   /**
   * Get renewalDate
   * @return renewalDate
  **/
  @ApiModelProperty(value = "")
  public String getRenewalDate() {
    return renewalDate;
  }

  public void setRenewalDate(String renewalDate) {
    this.renewalDate = renewalDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostUserGenerateRequest postUserGenerateRequest = (PostUserGenerateRequest) o;
    return Objects.equals(this.header, postUserGenerateRequest.header) &&
        Objects.equals(this.metadataId, postUserGenerateRequest.metadataId) &&
        Objects.equals(this.masterKey, postUserGenerateRequest.masterKey) &&
        Objects.equals(this.visualConfirmation, postUserGenerateRequest.visualConfirmation) &&
        Objects.equals(this.renewalDate, postUserGenerateRequest.renewalDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, metadataId, masterKey, visualConfirmation, renewalDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostUserGenerateRequest {\n");
    
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    metadataId: ").append(toIndentedString(metadataId)).append("\n");
    sb.append("    masterKey: ").append(toIndentedString(masterKey)).append("\n");
    sb.append("    visualConfirmation: ").append(toIndentedString(visualConfirmation)).append("\n");
    sb.append("    renewalDate: ").append(toIndentedString(renewalDate)).append("\n");
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

