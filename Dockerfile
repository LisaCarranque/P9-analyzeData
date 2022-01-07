FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD /build/libs/analyzedata-1.0.0.jar analyzedata-1.0.0.jar
ENTRYPOINT ["java","-jar","analyzedata-1.0.0.jar"]