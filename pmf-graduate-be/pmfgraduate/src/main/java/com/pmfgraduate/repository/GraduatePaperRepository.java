package com.pmfgraduate.repository;

import com.pmfgraduate.model.GraduatePaper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraduatePaperRepository extends MongoRepository<GraduatePaper, String> {

    List<GraduatePaper> findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndMentorIgnoreCaseLike(String title, String author, String mentor);

}
