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
    String originalFilename = file.getOriginalFilename();
    String filenameOnServer = Helper.userName() + System.currentTimeMillis() + originalFilename;
    String fileDirectory = "/files/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/"
        + LocalDate.now().getDayOfMonth() + "/";
    String filepath = fileDirectory + filenameOnServer;
    String realPath = request.getServletContext().getRealPath(fileDirectory);
    new File(realPath).mkdirs();
    File fileToSave = new File(realPath + filenameOnServer);
    System.out.println(fileToSave.getAbsolutePath());
    file.transferTo(fileToSave);
    System.out.println(filepath);// 진짜주소
    System.out.println(realPath);
    System.out.println(originalFilename);// 오리지널
    files.setOriginal_filename(originalFilename);
    files.setFilename(filepath);
    return files;
  }

  public List<Files> uploadFile(List<MultipartFile> file, HttpServletRequest request) throws IllegalStateException, IOException {
    List<Files> filess = new ArrayList<Files>();
    for (MultipartFile mfile : file) {
      if(mfile.getOriginalFilename().trim()!="") {
        Files files = new Files();
        String originalFilename = mfile.getOriginalFilename();
        System.out.println(originalFilename.trim());
        String filenameOnServer = Helper.userName() + System.currentTimeMillis() + originalFilename;
        String fileDirectory = "/files/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/"
            + LocalDate.now().getDayOfMonth() + "/";
        String filepath = fileDirectory + filenameOnServer;
        String realPath = request.getServletContext().getRealPath(fileDirectory);
        new File(realPath).mkdirs();
        File fileToSave = new File(realPath + filenameOnServer);
        System.out.println("파일업로드 sysout");
        System.out.println(fileToSave.getAbsolutePath());
        mfile.transferTo(fileToSave);
        System.out.println(filepath);// 진짜주소
        System.out.println(realPath);
        System.out.println(originalFilename);// 오리지널
        System.out.println("파일 업로드 sysout end");
        
        files.setOriginal_filename(originalFilename);
        files.setFilename(filepath);
        filess.add(files);
      }
    }
    return filess;
  }
}
