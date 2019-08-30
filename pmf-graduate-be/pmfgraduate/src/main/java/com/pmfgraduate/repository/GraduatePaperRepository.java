package com.pmfgraduate.repository;

import com.pmfgraduate.model.GraduatePaper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraduatePaperRepository extends MongoRepository<GraduatePaper, String> {
}
