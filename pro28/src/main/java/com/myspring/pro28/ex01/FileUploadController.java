package com.myspring.pro28.ex01;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

//@Controller
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	
	@RequestMapping(value="/form")
	
	public String form() {
	    return "uploadForm";
		// 업로드창인 uploadForm.jsp를 반환
	}
	
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
			// Spring Framework에서 제공하는 인터페이스
			// MultipartHttpServletRequest는 HttpServletRequest 인터페이스를 확장한 서브 인터페이스로, 
			// 파일 업로드와 관련된 메서드와 속성을 추가로 제공
			// 파일 필드에 대한 정보와 업로드된 파일을 가져올수 있음
			
			HttpServletResponse response)throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		Map map = new HashMap();
		// 매개변수 정보와 파일 정보를 저장할 Map을 생성
		
		Enumeration enu = multipartRequest.getParameterNames();
		logger.info("멀티파트 리퀘스트로 가져온 파라미터 이름 : " + enu);
		
		while(enu.hasMoreElements()) {
			// 전송된 매개변수 값은 ket/value로 map에 저장
			
			String name = (String) enu.nextElement();
			logger.info("이름의 다음 요소들 : "+ name);
			String value = multipartRequest.getParameter(name);
			logger.info("요소들의 파라미터 : " + value);
			map.put(name, value);
			
		}
		
		List fileList = fileProcess(multipartRequest);
		// multipartRequest를 매개변수로 받아 파일 처리 작업을 수행하는 함수나 메서드를 호출하는 것을 의미
		logger.info("fileProsess로 받은 multipartRequest : " + fileList);
		
		map.put("fileList", fileList);
		// 파일을 업로드한 후 반환된 파일 이름이 저장된 fileList를 다시 map에 저장
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map",map);
		mav.setViewName("result"); // map을 결과창으로 포워딩
		
		return mav;
	}

	
	
	
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception {

		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		// Java에서 컬렉션을 순회하기 위한 인터페이스
		// 첨부된 파일 이름을 가져옴
		
		while(fileNames.hasNext()) {
			// 다음 요소의 존재 여부를 확인
			String fileName = fileNames.next();
			// 다음 요소를 가져오기
			
			MultipartFile mFile = multipartRequest.getFile(fileName);
			// MultipartHttpServletRequest에서 특정 필드 이름에 해당하는 업로드된 파일을 가져옴
			// 파일 이름에 대한 MultiparFile 객체를 가져옴
			
			String originalFileName = mFile.getOriginalFilename();
			// 실제 파일이름을 가져옴
			
			fileList.add(originalFileName);
			// 파일 이름을 하나씩 fileList에 저장
			
			File file = new File(CURR_IMAGE_REPO_PATH + "\\" +fileName);
			
			if(mFile.getSize()!=0) {//첨부된 파일이 있는지 체크
				if(! file.exists()) {// 경로에 파일이 없으면 그경로에 해당하는 디렉터리를 만든 후 파일을 생성
					if(file.getParentFile().mkdir()) {
						// mkdir()은 디렉토리를 생성하는 메서드로, 디렉토리가 성공적으로 생성되면 true를 반환하고,
						// 이미 디렉토리가 존재하거나 생성에 실패한 경우 false를 반환
						file.createNewFile();
					}
				}
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH+ "\\" +originalFileName));
			}// 임시로 저장된 multiparFile을 실제 파일로 전송
			
			
		}
		
		
		return fileList;
		// 첨부한 파일 이름이 저장된 fileList를 반환.
	}
	
}
