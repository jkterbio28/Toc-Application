package com.collabera.loggerservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.collabera.loggerservice.models.MessageData;

@Repository
public interface LoggerRepository extends MongoRepository<MessageData, Integer>{

}
