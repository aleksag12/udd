package com.udd.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udd.dto.ApplicationDTO;
import com.udd.lucene.model.CVIndex;
import com.udd.lucene.model.Index;
import com.udd.lucene.pdf.PdfHandler;
import com.udd.lucene.repository.CVIndexRepository;
import com.udd.lucene.repository.IndexRepository;
import com.udd.model.Application;
import com.udd.repository.ApplicationRepository;

@Service
public class ApplicationService {
	
	@Autowired
	private FileService fileService;
	@Autowired
	private PdfHandler pdfHandler;
	@Autowired
	private LocationService locationService;
	@Autowired
	private CVIndexRepository cvRepository;
	@Autowired
	private IndexRepository indexRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	
	public Application upload(ApplicationDTO applicationDTO) throws Exception {
		MultipartFile cv = applicationDTO.getCv();

		if (!cv.isEmpty()) {
			String urlCV = fileService.upload(cv);
			if (urlCV != null) {
				String text = pdfHandler.getText(new File(urlCV));
				CVIndex unitCV = new CVIndex();
				unitCV.setContent(text);
				unitCV.setUrl(urlCV);
				cvRepository.save(unitCV);
				Application application = new Application(null, applicationDTO.getFirstName(), applicationDTO.getLastName(), applicationDTO.getEmail(), applicationDTO.getEducation(), 
						applicationDTO.getCity(), applicationDTO.getCountry(), urlCV);
				application = applicationRepository.save(application);

				String edu = "";
				switch (applicationDTO.getEducation()) {
					case "1" :
						edu = "Osnovno obrazovanje";
						break;
					case "2" :
						edu = "Srednje obrazovanje";
						break;
					case "3" :
						edu = "Osnovne akademske studije";
						break;
					case "4" :
						edu = "Master akademske studije";
						break;
					default:
						edu = "Doktorske studije";
				}
				String informations = application.getFirstName() + " " + application.getLastName() + ", "+ application.getEmail() + ", " + application.getEducation() + " - " + edu  + ", " + application.getCity() + ", " + application.getCountry();

				GeoPoint geoPoint = locationService.forwardGeocoding(application.getCity());

				if (geoPoint == null) {
					throw new Exception("Lokacija koju ste uneli ne postoji.");
				}

				Index iu = new Index(application.getId(), application.getFirstName(), application.getLastName(), application.getEducation(), informations, unitCV, geoPoint);
				indexRepository.index(iu);
				return application;
			}
		} else {
			throw new Exception("Neodgovarajuci CV dokument.");
		}
		return null;
	}

	
}
