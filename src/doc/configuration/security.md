## Security
```
#  Security Options =================================================================================== #
#                                                                                                       #
#  !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!!  #
#                                                                                                       #
#  After setting security options and creating user(s) and setting administrator account, it will  not  #
#  be possible to change the security options  without resetting all user and  administrator passwords  #
#  because Forgiva Integrator DOES NOT saves any password to anywhere unless hashing  it with model(s)  #
#  specified within this configuration file and it is not possible to backward the process.             #
#                                                                                                       #
#  !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!! CAUTION !!!  #
#                                                                                                       #
# SECURITY_PW_HASHING_MODEL:       Indicates the combination and/or definition of key derivation and    #
#                                   hashing functions to hold hashes for various purposes. Order is     #
#                                   important and you should separate algorithm definitions with plus   #
#                                   (+) sign.                                                           #
#                                   Available algorithms: SCRYPT, ARGON2, BCRYPT, SHA512, SHA384,       #
#                                   SHA256, SHA3-512, SHA3-384, SHA3-256, BLAKE2B-512, BLAKE2B-384,     #
#                                   BLAKE2B-256, SM3                                                    #
#                                                                                                       #
#                                   You can use any algorithm more than once.                           #
#                                   For example: SCRYPT+ARGON2 or BCRYPT+ARGON2+SHA512.                 #
#                                   Default: SHA512+SCRYPT+ARGON2                                       #
#                                                                                                       #
# SECURITY_PW_HASHING_SALT:        Salt to get used for hashing purposes with hashing model. Should be  #
#                                   random and unique                                                   #
#                                                                                                       #
# SECURITY_DEFAULT_HASHING:        Default hashing algorithm to use on necessary steps.                 #
#                                   Available algorithms: SHA512, SHA384,SHA256, SHA3-512, SHA3-384,    #
#                                   SHA3-256, BLAKE2B-512, BLAKE2B-384, BLAKE2B-256, SM3                #
#                                   Default: SHA3-512                                                   #
#                                                                                                       #
# SECURITY_DEFAULT_HASHING_SALT:   Default hashing salt to get used in . Should be random and unique.   #
#                                                                                                       #
# ARGON2_PARAMETERS:               Argon2_i algorithm parameters separated with colon (:) characters.   #
#                                   Definition:  ITERATIONS:MEMORY:PARALLELIZATION                      #
#                                   Default: 3:12:1                                                     #
#                                   Please refer to https://github.com/P-H-C/phc-winner-argon2 for more #
#                                   details.                                                            #
#                                                                                                       #
# SCRYPT_PARAMETERS:               SCrypt algorithm parameters separated with colon (:) characters.     #
#                                   Definition:  MEMORY:BLOCK_SIZE:PARALELIZATION                       #
#                                   Default: 131072:8:1                                                 #
#                                                                                                       #
# BCRYPT_COST:                      BCrypt algorithm parameter which grows the cost of the function as  #
#                                   2^cost. Legal values are 4..31 inclusive                            #
#                                   Default: 4                                                          #
#                                                                                                       #
# Password Usage Configuration ======================================================================== #
#                                                                                                       #
# SECURITY_PW_MIN_ENTROPY:          Minimum password entropy bits.A password entropy indicates strength #
#                                   against raw brute-force attacks. Minimum of 64-bit entropy required #
#                                   by default.forgiva_integrator                                       #
#                                                                                                       #
# SECURITY_PW_PROHIBITED_WORDLIST:  Blacklisted words and/or password samples which will get prohibited #
#                                   by the Forgiva Integrator. It should be in .gz / .bz2 / .7z or .xz  #
#                                   format file placed in the conf directory.                           #
#  ===================================================================================================  #
```

## Importing SSL Certificates 

```
keytool -import -v -trustcacerts \
        -alias forgiva_integrator -file cert.cer \
        -keystore fi_test_truststore.jks -keypass do_not_use_in_production \
        -storepass do_not_use_in_production
```
