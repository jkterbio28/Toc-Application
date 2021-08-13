package com.collabera.loggerservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.collabera.loggerservice.models.Message;
import com.collabera.loggerservice.models.MessageData;
import com.collabera.loggerservice.repositories.LoggerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageConsumer {
	@Autowired
	LoggerRepository loggerRepository;
	
//	@Qualifier("KafkaUserTemplate")
	@KafkaListener(topics = { "pocTopic" }, groupId = "group_id")
	public void consume(String message) {
		log.info("Message Received: " + message);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JavaTimeModule module = new JavaTimeModule();
			LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
			mapper.registerModule(module);
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			Message mess = mapper.readValue(message, Message.class);
			log.info("Action: " + mess.getAction() + " Message: " + mess.getMessage() + " Time: " + mess.getTime());
		
			int id = loggerRepository.findAll().size();
			id++;
			MessageData msgData = new MessageData();
			msgData.setId(id);
			msgData.setAction(mess.getAction());
			msgData.setMessage(mess.getMessage());
			msgData.setTime(mess.getTime());
			loggerRepository.save(msgData);
		} catch (Exception e) {
			log.error("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
