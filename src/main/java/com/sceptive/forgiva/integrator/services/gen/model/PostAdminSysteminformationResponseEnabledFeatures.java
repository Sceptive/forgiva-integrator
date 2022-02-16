package com.sceptive.forgiva.integrator.services.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PostAdminSysteminformationResponseEnabledFeatures
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-09T04:10:44.059+03:00[Europe/Istanbul]")
public class PostAdminSysteminformationResponseEnabledFeatures   {
  @JsonProperty("activeDirectory")
  private Boolean activeDirectory;

  public PostAdminSysteminformationResponseEnabledFeatures activeDirectory(Boolean activeDirectory) {
    this.activeDirectory = activeDirectory;
    return this;
  }

   /**
   * Get activeDirectory
   * @return activeDirectory
  **/
  @ApiModelProperty(value = "")
  public Boolean getActiveDirectory() {
    return activeDirectory;
  }

  public void setActiveDirectory(Boolean activeDirectory) {
    this.activeDirectory = activeDirectory;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostAdminSysteminformationResponseEnabledFeatures postAdminSysteminformationResponseEnabledFeatures = (PostAdminSysteminformationResponseEnabledFeatures) o;
    return Objects.equals(this.activeDirectory, postAdminSysteminformationResponseEnabledFeatures.activeDirectory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activeDirectory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostAdminSysteminformationResponseEnabledFeatures {\n");
    
    sb.append("    activeDirectory: ").append(toIndentedString(activeDirectory)).append("\n");
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

