FROM openjdk:8

ARG VER

RUN ["/bin/bash", "-c", ": ${VER:?Version needs to be set to create container image.}"]

ENV WD=/opt/forgiva-integrator/

RUN addgroup forgiva
RUN adduser --disabled-password --disabled-login --gecos '' --home "$WD" --ingroup forgiva  fuser
RUN chown -R fuser:forgiva ${WD}

USER fuser

RUN mkdir -p ${WD}/bin

COPY \
    build/deploy/${VER} \
    ${WD}

EXPOSE 8443

WORKDIR ${WD}
ENTRYPOINT ["/opt/forgiva-integrator/bin/forgiva_integrator.sh"]
