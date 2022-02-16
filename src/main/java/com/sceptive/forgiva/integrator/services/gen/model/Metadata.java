package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Contains metadata information.
 */
@ApiModel(description = "Contains metadata information.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-03-04T09:32:31.698+03:00[Europe/Istanbul]")
public class Metadata   {
  @JsonProperty("metadataId")
  private String metadataId;

  @JsonProperty("host")
  private String host;

  @JsonProperty("account")
  private String account;

  @JsonProperty("lastRenewal")
  private String lastRenewal;

  @JsonProperty("complexity")
  private Integer complexity;

  @JsonProperty("generatedBefore")
  private Boolean generatedBefore;

  @JsonProperty("groupId")
  private String groupId;

  public Metadata metadataId(String metadataId) {
    this.metadataId = metadataId;
    return this;
  }

   /**
   * Unique metadata id
   * @return metadataId
  **/
  @ApiModelProperty(value = "Unique metadata id")
  public String getMetadataId() {
    return metadataId;
  }

  public void setMetadataId(String metadataId) {
    this.metadataId = metadataId;
  }

  public Metadata host(String host) {
    this.host = host;
    return this;
  }

   /**
   * Host description of the metadata.
   * @return host
  **/
  @ApiModelProperty(required = true, value = "Host description of the metadata.")
  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Metadata account(String account) {
    this.account = account;
    return this;
  }

   /**
   * Account of the metadata.
   * @return account
  **/
  @ApiModelProperty(required = true, value = "Account of the metadata.")
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public Metadata lastRenewal(String lastRenewal) {
    this.lastRenewal = lastRenewal;
    return this;
  }

   /**
   * Last renewal date for the metadata for password generation.
   * @return lastRenewal
  **/
  @ApiModelProperty(value = "Last renewal date for the metadata for password generation.")
  public String getLastRenewal() {
    return lastRenewal;
  }

  public void setLastRenewal(String lastRenewal) {
    this.lastRenewal = lastRenewal;
  }

  public Metadata complexity(Integer complexity) {
    this.complexity = complexity;
    return this;
  }

   /**
   * Complexity range 1-3 for password generation.
   * @return complexity
  **/
  @ApiModelProperty(required = true, value = "Complexity range 1-3 for password generation.")
  public Integer getComplexity() {
    return complexity;
  }

  public void setComplexity(Integer complexity) {
    this.complexity = complexity;
  }

  public Metadata generatedBefore(Boolean generatedBefore) {
    this.generatedBefore = generatedBefore;
    return this;
  }

   /**
   * Indicates whether password generated before or not. If not password generation process should ensure that user is entering master password right.
   * @return generatedBefore
  **/
  @ApiModelProperty(value = "Indicates whether password generated before or not. If not password generation process should ensure that user is entering master password right.")
  public Boolean getGeneratedBefore() {
    return generatedBefore;
  }

  public void setGeneratedBefore(Boolean generatedBefore) {
    this.generatedBefore = generatedBefore;
  }

  public Metadata groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

   /**
   * Unique group id.
   * @return groupId
  **/
  @ApiModelProperty(value = "Unique group id.")
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Metadata metadata = (Metadata) o;
    return Objects.equals(this.metadataId, metadata.metadataId) &&
        Objects.equals(this.host, metadata.host) &&
        Objects.equals(this.account, metadata.account) &&
        Objects.equals(this.lastRenewal, metadata.lastRenewal) &&
        Objects.equals(this.complexity, metadata.complexity) &&
        Objects.equals(this.generatedBefore, metadata.generatedBefore) &&
        Objects.equals(this.groupId, metadata.groupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadataId, host, account, lastRenewal, complexity, generatedBefore, groupId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Metadata {\n");
    
    sb.append("    metadataId: ").append(toIndentedString(metadataId)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    lastRenewal: ").append(toIndentedString(lastRenewal)).append("\n");
    sb.append("    complexity: ").append(toIndentedString(complexity)).append("\n");
    sb.append("    generatedBefore: ").append(toIndentedString(generatedBefore)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
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

