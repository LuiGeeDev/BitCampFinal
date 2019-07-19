package kr.or.bit.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.model.Files;
import kr.or.bit.utils.Helper;

@Service
public class FileUploadService {
  public Files uploadFile(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
    Files files = new Files();
    if (file.getOriginalFilename().trim() != "") {
      String originalFilename = file.getOriginalFilename();
      String filenameOnServer = Helper.userName() + System.currentTimeMillis() + originalFilename;
      String fileDirectory = "/files/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/"
          + LocalDate.now().getDayOfMonth() + "/";
      String filepath = fileDirectory + filenameOnServer;
      String realPath = request.getServletContext().getRealPath(fileDirectory);
      new File(realPath).mkdirs();
      File fileToSave = new File(realPath + filenameOnServer);
      file.transferTo(fileToSave);
      files.setOriginal_filename(originalFilename);
      files.setFilename(filepath);
    }
    return files;
  }

  public List<Files> uploadFile(List<MultipartFile> file, HttpServletRequest request)
      throws IllegalStateException, IOException {
    List<Files> filess = new ArrayList<Files>();
    for (MultipartFile mfile : file) {
      if (mfile.getOriginalFilename().trim() != "") {
        Files files = new Files();
        String originalFilename = mfile.getOriginalFilename();
        String filenameOnServer = Helper.userName() + System.currentTimeMillis() + originalFilename;
        String fileDirectory = "/files/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/"
            + LocalDate.now().getDayOfMonth() + "/";
        String filepath = fileDirectory + filenameOnServer;
        String realPath = request.getServletContext().getRealPath(fileDirectory);
        new File(realPath).mkdirs();
        File fileToSave = new File(realPath + filenameOnServer);
        mfile.transferTo(fileToSave);
        files.setOriginal_filename(originalFilename);
        files.setFilename(filepath);
        filess.add(files);
      }
    }
    return filess;
  }
}
