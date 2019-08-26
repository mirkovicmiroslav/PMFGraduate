package com.pmfgraduate.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pmfgraduate.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

}
