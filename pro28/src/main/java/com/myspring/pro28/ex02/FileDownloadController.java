package com.myspring.pro28.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.System.Logger;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.coobird.thumbnailator.Thumbnails;

//@Controller
public class FileDownloadController {

	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	// 파일 저장 위치를 지정
	
	// 썸네일 이미지 파일을 생성후 다운로드
//	@RequestMapping("/download")
//	public void download(@RequestParam("imageFileName") String imageFileName,
//			// 다운로드할 이미지 파일 이름을 전달
//			HttpServletResponse response)throws Exception {
//		
//		OutputStream out = response.getOutputStream();
//		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
//		File image = new File(filePath);
//		String ecfile = URLEncoder.encode(imageFileName, "utf-8");
//		
//		int lastIndex = imageFileName.lastIndexOf(".");
//		String fileName = imageFileName.substring(0, lastIndex);
//		// 확장자를 제외한 원본 이미지 파일의 이름을 가져옴
//		
//		File thumbnail = new File(CURR_IMAGE_REPO_PATH + "\\" + "thumbnail"+"\\"+fileName+".png");
//		// 원본 이미지 파일 이름과 같은 이름의 썸네일 파일에 대한 File 객체를 생성
//		
//		if (image.exists()) { 
//			// 원본이미지가 존재하면 원본 이미지 파일을 가로세로가 50 픽셀인 png형식의 썸네일 이미지 파일로 생성
//			thumbnail.getParentFile().mkdirs();
//		    Thumbnails.of(image).size(50,50).outputFormat("png").toFile(thumbnail);
//		}
//		
//		
//		FileInputStream in = new FileInputStream(thumbnail);
//		
//		byte[] buffer = new byte[1024*8];
//		
//		while(true) {
//			// 버퍼를 이용해 한 번에 8Kbyte씩 브라우저로 전송
//			int count = in.read(buffer); //버퍼에 읽어들인 문자개수
//			if(count == -1) { // 버퍼의 마지막에 도달했는지 확인
//				break;
//			}
//			
//			out.write(buffer, 0, count);
//		}// 생성된 썸네일 파일을 브라우저로 전송.
//
//		in.close();
//		out.close();
//		
//		
//	}

// 원본 썸네일 이미지 바로출력
	@RequestMapping("/download")
	protected void download(@RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File image = new File(filePath);
		int lastIndex = imageFileName.lastIndexOf(".");
		String fileName = imageFileName.substring(0,lastIndex);
		String ecfile = URLEncoder.encode(fileName, "utf-8");
		System.out.println("ecfile의 인코딩된 이름"+ecfile);
		File thumbnail = new File(CURR_IMAGE_REPO_PATH+"\\"+"thumbnail"+"\\"+ecfile+".png");
		System.out.println("thumbnail의 이름"+thumbnail);
		if (image.exists()) { 
			Thumbnails.of(image).size(50,50).outputFormat("png").toOutputStream(out);
		}else {
			return;
		}
		byte[] buffer = new byte[1024 * 8];
		out.write(buffer);
		out.close();
	}

	
	
}
