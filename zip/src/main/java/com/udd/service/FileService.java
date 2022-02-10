package com.udd.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileService {

	private static final String DOCS_PATH = "src/main/resources/cv/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
	public String upload(MultipartFile pdfFile) throws IOException {
		String retVal = null;
        if (!pdfFile.isEmpty()) {
            byte[] bytes = pdfFile.getBytes();

            LocalDateTime created = LocalDateTime.now();
            String pdfName = created.format(FORMATTER) + pdfFile.getOriginalFilename();

            Path path = Paths.get(DOCS_PATH + File.separator + pdfName);
            
            Files.write(path, bytes);

            retVal = path.toString();
        }
        return retVal;
    }
	
	public byte[] readFile(String filePath) throws IOException {
        Path pdfPath = Paths.get(filePath);
        return Files.readAllBytes(pdfPath);
    }
}
