FROM ubuntu:20.04 
RUN apt update && \
	DEBIAN_FRONTEND=noninteractive apt install -y wget gnupg software-properties-common && \
	wget -O- https://apt.corretto.aws/corretto.key |  apt-key add -  && \
	add-apt-repository 'deb https://apt.corretto.aws stable main'  && \
	apt-get update; apt-get install -y java-11-amazon-corretto-jdk