package com.collabera.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.collabera.productservice.entities.Message;

@Service
public class MessageProducer {

	@Autowired
	@Qualifier("KafkaUserTemplate")
	private KafkaTemplate<String, Message> kafkaTemplate;

	public void sendMessage(Message message) {
		kafkaTemplate.send("pocTopic", message);
	}
}
