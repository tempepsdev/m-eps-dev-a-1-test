package com.mutualser.developer.infrastructure.persistence;

import com.mutualser.developer.domain.MoneyBox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyBoxRepository extends MongoRepository<MoneyBox, String> {}
