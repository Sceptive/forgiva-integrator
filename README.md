# Forgiva Integrator

## INTRODUCTION

```
      .-" L_    
 ;`, /   ( o\   FORGIVA Integrator
 \  ;    `, /   The new-age password manager.
 ;_/"`.__.-"
```

Forgiva Integrator is the core module of Forgiva Enterprise. It connects Forgiva Server to Forgiva WebClient on password  
generation processes and integrates other Forgiva Enterprise features with the network running  over such as LDAP, 
FORHash and  other various integrations.                                                                                                                          

## BUILDING

To build a runnable package you will need;

 - Unix/Linux environment
 - Maven build automation tool
 - Java 1.8 JDK environment
 - Docker Containerization Toolkit
 
installed. 
                                                                             
```                                                                             
$ ./build.sh run
```

command will be building whole project into the target/deploy/[version]/
path and synchronizing web client (client/web/dist) and Forgiva Server binary releases 
into the relative target path. And starts the Forgiva Integrator instance.

You can go to https://localhost:8443 to seek Forgiva Integrator. 


### Create Release Package

```
$ ./build.sh release
```

Will be creating **forgiva_integrator-$VER-jvm8-release.tar.xz** release file on
root folder. 

### Create Docker Image

```
$ ./build.sh image
```

To run the created forgiva-integrator image
```
$ docker run -p 8443:8443 <image-id>
```





