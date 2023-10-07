# 此文件用于云效流水线
FROM java:8
EXPOSE 9081

VOLUME /tmp
COPY castle-fortress-admin.jar /admin.jar
ENTRYPOINT ["java", "-jar", "admin.jar", "--spring.profiles.active=prod"]