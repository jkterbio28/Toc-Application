package com.collabera.loggerservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabera.loggerservice.controllers.dto.ResponseDTO;
import com.collabera.loggerservice.models.MessageData;
import com.collabera.loggerservice.service.LoggerService;

@RestController
@RequestMapping("/logger")
public class LoggerController {

	@Autowired
	LoggerService loggerService;
	
	@GetMapping("")
	public List<MessageData> getAll(){
		return loggerService.getAll();
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseDTO> generalError(Exception e){
		ResponseDTO resp = ResponseDTO.builder()
				.message("Ooops there is something wrong!")
				.build();
		return ResponseEntity.status(400).body(resp);
	}
}
