## Mail Server

```
# Mailing Configuration =============================================================================== #
#                                                                                                       #
# SMTP_SERVER:                      Mail server address starts with smtp://, smtps:// or stmp_tls://    #
#                                   combined with host:port couple.                                     #
#                                                                                                       #
#                                   smtp:// Uses plaintext handshake when  mail server doesn't indicate #
#                                   support for STARTTLS. Additionally, even if a TLS session is        #
#                                   negotiated server certificates does not get validated.              #
#                                                                                                       #
#                                   smtps:// SMTP entirely encapsulated by TLS.                         #
#                                   Strict validation of server certificates is enabled. Server certs.  #
#                                   must be issued either by a certificate authority in the system      #
#                                   trust store; or to a subject matching the identity of the remote    #
#                                   SMTP server.                                                        #
#                                                                                                       #
#                                   smtps_tls:// Plaintext SMTP with a mandatory, authenticated         #
#                                   STARTTLS upgrade. Server certs. controls are same with smtps.       #
#                                                                                                       #
# SMTP_USERNAME:                    Username to authenticate SMTP server                                #
# SMTP_PASSWORD:                    Password to use for smtp authentication                             #
# SMTP_FROM:                        "From" address to get used in mail sender part                      #
#                                                                                                       #
# SMTP_PROXY_HOST:                  Proxy host with port combination (host:port) to access mail server  #
# SMTP_PROXY_USERNAME:              Username to authenticate proxy                                      #
# SMTP_PROXY_PASSWORD:              Password to use in proxy authentication                             #
#                                                                                                       #
#  ===================================================================================================  #
```


