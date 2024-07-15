# FROM adoptopenjdk/openjdk8:alpine-slim

# USER root

# WORKDIR /app

# # COPY . /app

# # FROM ubuntu:18.04

# RUN apk update && \
#     apk add openjdk8 curl
# RUN apk add --update --no-cache openjdk8 curl tar gzip bash

# RUN curl -fsSL https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz | tar xz
# RUN mv apache-maven-3.8.8 /usr/lib/mvn && \
#     ln -s /usr/lib/mvn/bin/mvn /usr/bin/mvn

# ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

# # Copy the current directory contents into the container at /app
# # RUN bash -c "apk update && apk install maven"  

# RUN echo "Current working directory: $(PWD)"
# # Build the application
# # RUN cd /app

# RUN mvn clean package -X

FROM adoptopenjdk/openjdk8:alpine-slim
 
USER root

WORKDIR /app

RUN apk update && \
    apk add --no-cache curl && \
    curl -fsSL https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz | tar xz && \
    mv apache-maven-3.8.8 /usr/lib/mvn && \
    ln -s /usr/lib/mvn/bin/mvn /usr/bin/mvn

RUN apk add --no-cache bash

RUN apk add --no-cache --update openjdk8

# RUN update-alternatives --config java

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin

# Copy the current directory contents into the container at /app
COPY . /app

RUN echo "Current working directory: $(PWD)"
# Build the application
RUN mvn clean package -X

CMD ["mvn", "tomee-embedded:run"]
