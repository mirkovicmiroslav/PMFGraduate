package com.pmfgraduate.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.pmfgraduate.dto.*;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.pmfgraduate.exception.PmfGraduateException;
import com.pmfgraduate.mapper.GraduatePaperMapper;
import com.pmfgraduate.model.GraduatePaper;
import com.pmfgraduate.repository.GraduatePaperRepository;
import com.pmfgraduate.service.GraduatePaperService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

			ObjectId fileId = gridFsOperations.store(inputStream, file.getOriginalFilename(), file.getContentType(),
					metaData);

			return new FileResponseDTO(fileId.toString());
		} catch (MultipartException e) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST,
					"Veličina dokumenta je prevelika. Dozvoljena veličina je 2GB.");
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

		graduatePaperRepository.findAll().forEach(graduatePaper -> {
			graduatePaperListDTO.getGraduatePapers().add(graduatePaperMapper.map(graduatePaper));
		});

		List<GraduatePaperDTO> graduatePaperDTOS = graduatePaperListDTO.getGraduatePapers().stream().sorted(Comparator.comparing(GraduatePaperDTO::getPublicationYear).thenComparing(GraduatePaperDTO::getId).reversed()).collect(Collectors.toList());
		graduatePaperListDTO.setGraduatePapers(graduatePaperDTOS);
		return graduatePaperListDTO;
	}

	@Override
	public GraduatePaper getByID(String id) {
		return graduatePaperRepository.findById(id).orElseThrow(
				() -> new PmfGraduateException(HttpStatus.BAD_REQUEST, "Traženi diplomski rad ne postoji."));
	}

	@Override
	public PdfFileDTO getGraduatePaperPdf(String id) {
		try {
			GridFSFile gridFsFile = gridFsOperations.findOne(new Query(Criteria.where("_id").is(id)));

			return new PdfFileDTO(IOUtils.toByteArray(gridFsOperations.getResource(gridFsFile).getInputStream()));
		} catch(IOException e){
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Traženi diplomski rad ne postoji.");
		}
	}

	@Override
	public GraduatePaperListDTO getSearchedFilter(String title, String author, String mentor) {
		GraduatePaperListDTO graduatePaperListDTO = new GraduatePaperListDTO();

		graduatePaperRepository
				.findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndMentorIgnoreCaseLike(title, author, mentor)
				.forEach(graduatePaper -> {
					graduatePaperListDTO.getGraduatePapers().add(graduatePaperMapper.map(graduatePaper));
				});

		return graduatePaperListDTO;
	}

	@Override
	public TopMentorsListDTO getTopMentors() {
		return new TopMentorsListDTO(helper().getMentors().stream().limit(5).collect(Collectors.toList()));
	}

	@Override
	public byte[] getAllMentors() {
		List<GraduatePaper> findAllMentorsAndMembersOfBoard = graduatePaperRepository.findAllMentorsAndMembersOfBoard();
		List<MentorReportDTO> mentorsReportDTOList = new ArrayList<>();

		Map<String, Integer> map = new HashMap<>();
		TopMentorsListDTO topMentorsListDTO = helper();
		int mentorCount = 0;
		for (GraduatePaper mentors : findAllMentorsAndMembersOfBoard) {
			MentorReportDTO mentorsReportDTO = new MentorReportDTO();
			map.put(mentors.getMentor(), 0);
			for (GraduatePaper members : findAllMentorsAndMembersOfBoard) {
				if (mentors.getMentor().equals(members.getPresident())) {
					map.put(mentors.getMentor(), map.get(mentors.getMentor()) + 1);
					continue;
				}
				if (mentors.getMentor().equals(members.getMemberFirst())) {
					map.put(mentors.getMentor(), map.get(mentors.getMentor()) + 1);
					continue;
				}
				if (mentors.getMentor().equals(members.getMemberSecond())) {
					map.put(mentors.getMentor(), map.get(mentors.getMentor()) + 1);
				}
			}
			for (TopMentorsDTO topMentorsDTO : topMentorsListDTO.getMentors()) {
				if (topMentorsDTO.getName().equals(mentors.getMentor())) {
					mentorCount = topMentorsDTO.getCount();
				}
			}
			mentorsReportDTO.setMentor(mentors.getMentor());
			mentorsReportDTO.setMentorCount(mentorCount);
			mentorsReportDTO.setMemberOfBoard(map.get(mentors.getMentor()));

			mentorsReportDTOList.add(mentorsReportDTO);
		}

		mentorsReportDTOList = mentorsReportDTOList.stream().filter(distinctByKey(MentorReportDTO::getMentor))
				.sorted(Comparator.comparingInt(MentorReportDTO::getMentorCount)
						.thenComparing(MentorReportDTO::getMemberOfBoard).reversed())
				.collect(Collectors.toList());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(mentorsReportDTOList);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/rpt_mentors.jasper");
		JasperPrint jasperPrint = null;
		byte[] reportBytes = null;
		try {
			jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap<>(), dataSource);
			inputStream.close();

			reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException | IOException e) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Greška na serveru.");
		}

		return reportBytes;
	}

	private TopMentorsListDTO helper() {
		List<TopMentorsDTO> mentorsList = new ArrayList<>();

		DB db = mongoClient.getDB("pmf-graduate");
		DBCollection collection = db.getCollection("graduate-paper");

		String map = "function() {emit(this.mentor, 1);};";
		String reduce = "function(key, values) { return Array.sum(values);};";

		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, null, MapReduceCommand.OutputType.INLINE,
				null);

		MapReduceOutput out = collection.mapReduce(cmd);

		for (DBObject o : out.results()) {
			TopMentorsDTO topMentorsDTO = new TopMentorsDTO();
			String replaceString = o.toMap().values().toString().replace("[", "").replace("]", "");
			String[] splitMap = replaceString.split(",");

			topMentorsDTO.setName(splitMap[0]);
			topMentorsDTO.setCount((int) Double.parseDouble(splitMap[1].trim()));
			mentorsList.add(topMentorsDTO);
		}
		mentorsList = mentorsList.stream().sorted(Comparator.comparing(TopMentorsDTO::getCount).reversed())
				.collect(Collectors.toList());

		return new TopMentorsListDTO(mentorsList);
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

}
