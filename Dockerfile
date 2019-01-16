FROM openjdk:8-alpine
ENV WORKDIR /opt/app
WORKDIR ${WORKDIR}

COPY src ./
COPY build.gradle gradlew docker/entrypoint.sh ./
COPY gradle ./gradle

EXPOSE 4104
ENTRYPOINT [ "/entrypoint.sh" ]
