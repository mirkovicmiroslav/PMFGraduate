package com.pmfgraduate.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pmfgraduate.model.GraduatePaper;

@Repository
public interface GraduatePaperRepository extends MongoRepository<GraduatePaper, String> {

	List<GraduatePaper> findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndMentorIgnoreCaseLike(String title,
			String author, String mentor);

	@Query(value = "{}", fields = "{mentor: 1, president : 1, memberFirst: 1, memberSecond: 1, _id : 0}")
	List<GraduatePaper> findAllMentorsAndMembersOfBoard();

}
