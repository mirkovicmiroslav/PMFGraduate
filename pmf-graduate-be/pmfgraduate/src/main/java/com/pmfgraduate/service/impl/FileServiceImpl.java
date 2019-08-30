package com.pmfgraduate.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pmfgraduate.dto.FileResponseDTO;
import com.pmfgraduate.dto.GraduatePaperDTO;
import com.pmfgraduate.exception.PmfGraduateException;
import com.pmfgraduate.mapper.FileMapper;
import com.pmfgraduate.model.File;
import com.pmfgraduate.repository.FileRepository;
import com.pmfgraduate.service.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Transactional
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    GridFsOperations gridFsOperations;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    FileMapper fileMapper;

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
    public boolean saveGraduatePaper(GraduatePaperDTO graduatePaperDTO) {
        File file = fileMapper.map(graduatePaperDTO);

        fileRepository.save(file);

        return true;
    }
}
