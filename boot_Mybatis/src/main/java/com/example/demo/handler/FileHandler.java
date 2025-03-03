package com.example.demo.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Component
@Slf4j
public class FileHandler {
	private final String UP_DIR = "D:\\_myProject\\_java\\_fileUpload\\";
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		List<FileVO>flist = new ArrayList<>();
		LocalDate date = LocalDate.now();
		String today = date.toString();
		//오늘 날짜를 파일의 경로 모양으로 변경하여 today에 저장
		today = today.replace("-", File.separator);
		//D:\\_myProject\\_java\\_fileUpload\\2024\\01\\29
		File folders = new File(UP_DIR, today);
		
		
		//실제 폴더를 생성하는 명령어 mkdir(한개폴더생성) / mkdirs(여러 폴더 한번에 성공)
		if(!folders.exists()) {
			folders.mkdirs();
		}
		
		//-------------------------------폴더 생성 완료--------------------------------
		
		//fileVO 생성
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename();
			String onlyFileName = originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1);
			fvo.setFileName(onlyFileName);
			
			UUID uuid = UUID.randomUUID();
			fvo.setUuid(uuid.toString());
			
			//---------fvo설정 마무리 ------------
			
			//디스크에 저장할 파일 생성
			String fullFileName = uuid.toString()+"_"+onlyFileName;
			File storeFile = new File(folders, fullFileName);
			
			try {
				//원본파일
				file.transferTo(storeFile);
				//file=type 이미지 파일이면 1 아니면 0
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);
					File thumbnail = new File(folders, uuid.toString()+"_th_"+onlyFileName);
					//이미지만 썸네일 가능
					Thumbnails.of(storeFile).size(80,80).toFile(thumbnail);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("파일 저장 오류");
			}
			flist.add(fvo);
		}
		
		return flist;
	}
	private boolean isImageFile(File file) throws IOException {
		String mimeType = new Tika().detect(file);
		return mimeType.startsWith("image")? true : false;
	}
}