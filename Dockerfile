FROM ubuntu as application

ARG MAVEN_VERSION=3.6.1
ARG USER_HOME_DIR="/root"
ARG SHA=b4880fb7a3d81edd190a029440cdf17f308621af68475a4fe976296e71ff4a4b546dd6d8a58aaafba334d309cc11e638c52808a4b0e818fc0fd544226d952544
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN apt-get update \
  && apt-get install -y \
    software-properties-common \
    apt-transport-https \
    ca-certificates \
    curl \
    procps \
    dos2unix \
  && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Install Java
RUN add-apt-repository ppa:openjdk-r/ppa \
  && apt-get update \
  && apt-get install openjdk-11-jdk -y

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

# Define working directory
WORKDIR /data
ENV TEST_PROJECT_DIR=/framework
WORKDIR ${TEST_PROJECT_DIR}
COPY src/ src/
COPY entrypoint.sh .
COPY pom.xml .

# Install test project
RUN mvn install -B -T 1C -DskipTests=true

RUN dos2unix /framework/entrypoint.sh

RUN chmod +x /framework/entrypoint.sh
ENTRYPOINT ["/bin/bash", "/framework/entrypoint.sh"]