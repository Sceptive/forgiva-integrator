package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * OperationResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-09T12:05:50.717+03:00[Europe/Istanbul]")
public class OperationResult   {
  @JsonProperty("error")
  private String error;

  @JsonProperty("info")
  private String info;

  @JsonProperty("affectedRecords")
  private List<String> affectedRecords = null;

  public OperationResult error(String error) {
    this.error = error;
    return this;
  }

   /**
   * In case of any error returns error message.
   * @return error
  **/
  @ApiModelProperty(value = "In case of any error returns error message.")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public OperationResult info(String info) {
    this.info = info;
    return this;
  }

   /**
   * For any additional info message may need to get shown to the user
   * @return info
  **/
  @ApiModelProperty(value = "For any additional info message may need to get shown to the user")
  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public OperationResult affectedRecords(List<String> affectedRecords) {
    this.affectedRecords = affectedRecords;
    return this;
  }

  public OperationResult addAffectedRecordsItem(String affectedRecordsItem) {
    if (this.affectedRecords == null) {
      this.affectedRecords = new ArrayList<String>();
    }
    this.affectedRecords.add(affectedRecordsItem);
    return this;
  }

   /**
   * In case of any data operation this returns id's of records. On adding new data this returns new record ids
   * @return affectedRecords
  **/
  @ApiModelProperty(value = "In case of any data operation this returns id's of records. On adding new data this returns new record ids")
  public List<String> getAffectedRecords() {
    return affectedRecords;
  }

  public void setAffectedRecords(List<String> affectedRecords) {
    this.affectedRecords = affectedRecords;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationResult operationResult = (OperationResult) o;
    return Objects.equals(this.error, operationResult.error) &&
        Objects.equals(this.info, operationResult.info) &&
        Objects.equals(this.affectedRecords, operationResult.affectedRecords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, info, affectedRecords);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationResult {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    affectedRecords: ").append(toIndentedString(affectedRecords)).append("\n");
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

