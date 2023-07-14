package com.myspring.pro28.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
public class FileDownloadController {

	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	// 파일 저장 위치를 지정
	
	@RequestMapping("/download")
	public void download(@RequestParam("imageFileName") String imageFileName,
			// 다운로드할 이미지 파일 이름을 전달
			HttpServletResponse response)throws Exception {
		
		OutputStream out = response.getOutputStream();
		String downFile = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File file = new File(downFile);
		// 다운로드할 파일 객체를 생성
		
		String ecfile = URLEncoder.encode(imageFileName, "utf-8");
		
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + ecfile);
		// 헤더에 파일 이름을 설정
		
		FileInputStream in = new FileInputStream(file);
		
		byte[] buffer = new byte[1024*8];
		
		while(true) {
			// 버퍼를 이용해 한 번에 8Kbyte씩 브라우저로 전송
			int count = in.read(buffer); //버퍼에 읽어들인 문자개수
			if(count == -1) { // 버퍼의 마지막에 도달했는지 확인
				break;
			}
			
			out.write(buffer, 0, count);
			
		}

		in.close();
		out.close();
		
		
	}

}
