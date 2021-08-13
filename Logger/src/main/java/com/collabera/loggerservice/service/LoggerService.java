package com.collabera.loggerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collabera.loggerservice.models.MessageData;
import com.collabera.loggerservice.repositories.LoggerRepository;

@Service
public class LoggerService {

	@Autowired
	LoggerRepository loggerRepository;

	public List<MessageData> getAll() {
		return loggerRepository.findAll();
	}
}
