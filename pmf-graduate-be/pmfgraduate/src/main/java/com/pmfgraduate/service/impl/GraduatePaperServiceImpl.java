package com.pmfgraduate.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.pmfgraduate.dto.FileResponseDTO;
import com.pmfgraduate.dto.GraduatePaperListDTO;
import com.pmfgraduate.dto.PdfFileDTO;
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
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class GraduatePaperServiceImpl implements GraduatePaperService {

    @Autowired
    GridFsOperations gridFsOperations;
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
            throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Some problem...");
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
        return graduatePaperRepository.findById(id).orElseThrow(() -> new PmfGraduateException(HttpStatus.BAD_REQUEST, "Some problem..."));
    }

    @Override
    public PdfFileDTO getGraduatePaperPdf(String id) throws IOException {
        GridFSFile gridFsFile = gridFsOperations.findOne(new Query(Criteria.where("_id").is(id)));

        return new PdfFileDTO(IOUtils.toByteArray(gridFsOperations.getResource(gridFsFile).getInputStream()));
    }


}
