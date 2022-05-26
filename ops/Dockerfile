FROM        gradle:6.5.0-jdk14
RUN         mkdir -p /app/service/ops
WORKDIR     /app/service
RUN         gradle wrapper --gradle-version 6.4.1 --distribution-type all
RUN         chmod +x gradlew
EXPOSE      80
CMD         ["sh","/app/service/ops/entrypoint.sh"]