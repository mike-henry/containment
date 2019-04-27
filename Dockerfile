FROM alpine:latest as build
WORKDIR  /containment
LABEL author  mikeh
USER root
VOLUME [ "/root/.gradle" ]

RUN wget https://cdn.azul.com/zulu/bin/zulu11.29.3-ca-jdk11.0.2-linux_musl_x64.tar.gz
RUN wget  https://services.gradle.org/distributions/gradle-5.2.1-bin.zip
RUN tar -xzvf *.tar.gz
RUN unzip gradle-5.2.1-bin.zip
RUN rm -rf *.tar.gzdir
RUN rm -rf *.zip
RUN mv zulu11.29.3-ca-jdk11.0.2-linux_musl_x64 jdk11.0.2 && mv jdk11.0.2  /usr/local/share/jdk11.0.2
RUN mv gradle-5.2.1 /usr/local/share
ENV JAVA_HOME /usr/local/share/jdk11.0.2
ENV PATH "${JAVA_HOME}/bin:${GRADLE_HOME}/bin:${PATH}"


FROM alpine:latest as runnable
COPY --from=build  /usr/local/share/jdk11.0.2 /usr/local/share/jdk11.0.2
WORKDIR  /containment
COPY ./containment-service/build/distributions/*.zip .
COPY ./containment-service/src/main/resources/app.yaml /
RUN unzip *.zip
RUN rm -rf *.zip
ENV JAVA_HOME /usr/local/share/jdk11.0.2
ENV PATH "${JAVA_HOME}/bin:${PATH}"
ENV APP_HOME /containment/lib

CMD  ./bin/containment  server /app.yaml 



