## Web Server

```
#  HTTPS Configuration================================================================================= #
#                                                                                                       #
#  HTTPS_PORT:                      Port number of HTTPS gateway.                                       #
#  HTTPS_HOST:                      Host to bind port to.                                               #
#  HTTPS_SSL_KEYSTORE_FILE:         .JKS KeyStore file containing SSL Keys Respective to conf directory #
#  HTTPS_SSL_KEYSTORE_PASS:         .JKS KeyStore file password                                         #
#  HTTPS_SSL_KEYSTORE_PASS:         Certificate password located in JKS File                            #
#                                                                                                       #
# ===================================================================================================== #
```


## Generating Self-Signed SSL certificates for web server

#### Generating KeyStore 

```
keytool -genkey -alias forgiva_integrator \
        -keyalg RSA -keypass do_not_use_in_production \
        -storepass do_not_use_in_production -keystore fi_test_keystore.jks
```

### Exporting generated certificate (IF NECCESSARY)

```
keytool -export -alias forgiva_integrator \
        -storepass do_not_use_in_production \
        -file cert.cer \
        -keystore fi_test_keystore.jks
```