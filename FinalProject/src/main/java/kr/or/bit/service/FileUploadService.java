package kr.or.bit.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.utils.Helper;

@Service
public class FileUploadService {
  public String uploadFile(HttpServletRequest request, MultipartFile file) throws IllegalStateException, IOException {
    String originalFilename = file.getOriginalFilename();
    String filenameOnServer = Helper.userName() + System.currentTimeMillis() + originalFilename;
    String fileDirectory = "/files/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth(); 
    String filepath = fileDirectory + filenameOnServer;
    String realPath = request.getServletContext().getRealPath(fileDirectory);
    
    new File(realPath).mkdirs();
    File fileToSave = new File(realPath + filenameOnServer);
    System.out.println(fileToSave.getAbsolutePath());
    file.transferTo(fileToSave);
    
    return filepath;
  }
}
