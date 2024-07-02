FROM openjdk:17-alpine
WORKDIR /usr/src/app
COPY ./dependencies ./
COPY ./spring-boot-loader ./
COPY ./snapshot-dependencies ./
COPY ./application ./
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]