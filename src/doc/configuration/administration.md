## Administration
```
#  Administrator Credentials ========================================================================== #
#                                                                                                       #
#  As a security precaution for Administrator credentials for cases on any database breaches we hold it #
#  in the configuration file for only file-system access.                                               #
#                                                                                                       #
#  ADMINISTRATOR_USERNAME:      User name for administrator account                                     #
#  ADMINISTRATOR_PASSWORD:      Hashed and or derived password specified with SECURITY_PW_HASHING_MODEL.#
#                               and SECURITY_DEFAULT_HASHING.                                           #
#                               You can generate it by running bin/fhash.sh after defining model with   #
#                               SECURITY_PW_HASHING_MODEL and SECURITY_DEFAULT_HASHING which will       #
#                               generate it respective to SECURITY_HASHING_SALT parameter.              #
#  ADMINISTRATOR_IP_ADDRESSES:  Ip addresses dedicated to administrator account. Multiple IPs can be    #
#                               separated with comma (,) sign.                                          #
#                                                                                                       #
# ===================================================================================================== #
```