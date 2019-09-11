package com.pmfgraduate.service.impl;

import com.mongodb.*;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.pmfgraduate.dto.*;
import com.pmfgraduate.exception.PmfGraduateException;
import com.pmfgraduate.mapper.GraduatePaperMapper;
import com.pmfgraduate.model.GraduatePaper;
import com.pmfgraduate.repository.GraduatePaperRepository;
import com.pmfgraduate.service.GraduatePaperService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GraduatePaperServiceImpl implements GraduatePaperService {

    @Autowired
    GridFsOperations gridFsOperations;
    @Autowired
    MongoClient mongoClient;
    @Autowired
    GraduatePaperRepository graduatePaperRepository;
    @Autowired
    GraduatePaperMapper graduatePaperMapper;

    @Override
    public FileResponseDTO saveFile(MultipartFile file) throws IOException {
        try {
            if (!file.getContentType().equals("application/pdf")) {
                throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Format dokumenta mora biti pdf!");
            }
            DBObject metaData = new BasicDBObject();
            metaData.put("organization", "DMI");

            InputStream inputStream = file.getInputStream();

            ObjectId fileId = gridFsOperations.store(inputStream, file.getOriginalFilename(), file.getContentType(), metaData);

            return new FileResponseDTO(fileId.toString());
        } catch (MultipartException e) {
            throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Veličina dokumenta je prevelika. Dozvoljena veličina je 2GB.");
        }
    }

    @Override
    public boolean saveGraduatePaper(GraduatePaper graduatePaper) {
        graduatePaperRepository.save(graduatePaper);

        return true;
    }

    @Override
    public GraduatePaperListDTO getAllGraduatePapers() {
        GraduatePaperListDTO graduatePaperListDTO = new GraduatePaperListDTO();

        graduatePaperRepository.findAll().forEach(graduatePaper -> { graduatePaperListDTO.getGraduatePapers().add(graduatePaperMapper.map(graduatePaper)); });

        return graduatePaperListDTO;
    }

    @Override
    public GraduatePaper getByID(String id) {
        return graduatePaperRepository.findById(id).orElseThrow(() -> new PmfGraduateException(HttpStatus.BAD_REQUEST, "Traženi diplomski rad ne postoji."));
    }

    @Override
    public PdfFileDTO getGraduatePaperPdf(String id) throws IOException {
        GridFSFile gridFsFile = gridFsOperations.findOne(new Query(Criteria.where("_id").is(id)));

        return new PdfFileDTO(IOUtils.toByteArray(gridFsOperations.getResource(gridFsFile).getInputStream()));
    }

    @Override
    public GraduatePaperListDTO getSearchedFilter(String title, String author, String mentor) {
        GraduatePaperListDTO graduatePaperListDTO = new GraduatePaperListDTO();

        graduatePaperRepository.findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndMentorIgnoreCaseLike(title, author, mentor).forEach(graduatePaper -> { graduatePaperListDTO.getGraduatePapers().add(graduatePaperMapper.map(graduatePaper)); });

        return graduatePaperListDTO;
    }

    @Override
    public TopMentorsListDTO getTopMentors() {
        List<TopMentorsDTO> mentorsList = new ArrayList<>();

        DB db = mongoClient.getDB("pmf-graduate");
        DBCollection collection = db.getCollection("graduate-paper");

        String map = "function() {emit(this.mentor, 1);};";
        String reduce = "function(key, values) { return Array.sum(values);};";

        MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,
                null, MapReduceCommand.OutputType.INLINE, null);

        MapReduceOutput out = collection.mapReduce(cmd);

        for (DBObject o : out.results()) {
            TopMentorsDTO topMentorsDTO = new TopMentorsDTO();
            String replaceString = o.toMap().values().toString().replace("[", "").replace("]", "");
            String[] splitMap = replaceString.split(",");

            topMentorsDTO.setName(splitMap[0]);
            topMentorsDTO.setCount((int) Double.parseDouble(splitMap[1].trim()));
            mentorsList.add(topMentorsDTO);
        }
        mentorsList = mentorsList.stream().sorted(Comparator.comparing(TopMentorsDTO::getCount).reversed()).limit(5).collect(Collectors.toList());

        return new TopMentorsListDTO(mentorsList);
    }


}
