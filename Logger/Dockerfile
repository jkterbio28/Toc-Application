FROM default_base 
RUN mkdir /jar	
COPY ./target/LoggerService-1.0.0.jar /jar/app.jar	
EXPOSE 8080/tcp
ENTRYPOINT java -jar /jar/app.jar --server-port=8080