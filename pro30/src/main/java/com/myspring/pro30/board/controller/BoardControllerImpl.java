package com.myspring.pro30.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import com.myspring.pro30.member.vo.MemberVO;


@Controller("boardController")
public class BoardControllerImpl  implements BoardController{
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	
	
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
//	@requestMapping은 컨트롤러 클래스 또는 메서드에 사용되는 주석(Annotation)
//	HTTP 요청과 해당 요청을 처리하는 메서드를 매핑(mapping)해주는 역할
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ModelAndView: 뷰와 모델 데이터를 함께 반환하는 방식으로, ModelAndView 객체를 반환
		String viewName = (String)request.getAttribute("viewName");
		// 인터셉터에서 전달된 뷰이름을 가져옴
		
		List articlesList = boardService.listArticles();
		// 모든 글 정보를 조회

		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesList", articlesList);
		// 조회한 글정보를 바인딩한 후 JSP로 전달
		return mav;
		
	}
	
	

	
	// 해당 위치로 이동하기
	@RequestMapping(value = "/board/*Form.do", method =  {RequestMethod.GET, RequestMethod.POST})
	private ModelAndView form(@RequestParam(required = false) String parentNO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		System.out.println("받아온 form.do의 viewname : " + viewName);
		System.out.println("form.do 메서드 패런트넘버 : " + parentNO);
		
		if(viewName.equals("/board/replyForm")) {
			System.out.println("패런트 넘버 장착.");
			mav.addObject("parentNO", parentNO);
		}
		return mav;
	}
	
	
	
	
	

	
	 //한 개 이미지 글쓰기 및 답글 쓰기
	@Override
	@RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(@RequestParam(defaultValue = "0") String parentNO,
			MultipartHttpServletRequest multipartRequest, 
	HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String,Object> articleMap = new HashMap<String, Object>();
		// 글 정보를 저장하기 위함 articleMap을 생성
		
		Enumeration enu=multipartRequest.getParameterNames();
		
		System.out.println(parentNO + " : 패런트");
		
		while(enu.hasMoreElements()){
			// 글쓰기 창에서 전송된 글 정보를 Map에 key/value로 저장
			
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name,value);
		}
		
		String imageFileName= upload(multipartRequest);
		// 업로드한 이미지 파일 이름을 가져옴
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		// 세션에 저장된 회원 정보로부터 회원 ID를 가져옴
		
		articleMap.put("parentNO", parentNO);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		System.out.println("responseHeaders의 경로 : " + responseHeaders);
		try {
			int articleNO = boardService.addNewArticle(articleMap);
			// 글 정보가 저장된 articleMap을 Service클래스의  addArticle()메서드로 전달
			System.out.println("업로드한 articleNO : " + articleNO);
			
			if(imageFileName!=null && imageFileName.length()!=0) {
				// 글 정보를 추가한 후 업로드한 이미지 파일을 글번호로 명명한 폴더로 이동함
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir,true);
			}
	
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		    // 새글을 추가한후 메시지를 전달
		    
		}catch(Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = " <script>";
			message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	
	

	
	//한개의 이미지 보여주기
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
									// 조회할 글 번호를 가져옴
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		articleVO=boardService.viewArticle(articleNO);
		// 조회한 글 정보를 articleVO에 설정
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("article", articleVO);
		return mav;
	}
	
   

	
	
	
  //한 개 이미지 수정 기능
  @RequestMapping(value="/board/modArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
    HttpServletResponse response) throws Exception{
    multipartRequest.setCharacterEncoding("utf-8");
	Map<String,Object> articleMap = new HashMap<String, Object>();
	Enumeration enu=multipartRequest.getParameterNames();
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
	}
	
	String imageFileName= upload(multipartRequest);
	articleMap.put("imageFileName", imageFileName);
	
	String articleNO=(String)articleMap.get("articleNO");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    try {
       boardService.modArticle(articleMap);
       if(imageFileName!=null && imageFileName.length()!=0) {
         File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
         File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
         FileUtils.moveFileToDirectory(srcFile, destDir, true);
         // 새로 첨부한 파일을 폴더로 이동
         
         String originalFileName = (String)articleMap.get("originalFileName");
         File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
         oldFile.delete();
         // 기존 파일을 삭제
       }	
       message = "<script>";
	   message += " alert('글을 수정했습니다.');";
	   message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	   message +=" </script>";
       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }catch(Exception e) {
      File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
      srcFile.delete();
      message = "<script>";
	  message += " alert('오류가 발생했습니다.다시 수정해주세요');";
	  message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	  message +=" </script>";
      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }
    return resEnt;
  }
  
  
  
  
  
  
  
  // 삭제하기
  @Override
  @RequestMapping(value="/board/removeArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
		  							// 삭제할 글번호를 가져옴
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setContentType("text/html; charset=UTF-8");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	try {
		boardService.removeArticle(articleNO);
		// 글 번호를 전달해서 글을 삭제
		File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
		FileUtils.deleteDirectory(destDir);
		// 글에 첨부된 이미지 파일이 저장된 폴더도 삭제
		
		message = "<script>";
		message += " alert('글을 삭제했습니다.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	       
	}catch(Exception e) {
		message = "<script>";
		message += " alert('작업중 오류가 발생했습니다.다시 시도해 주세요.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    e.printStackTrace();
	}
	return resEnt;
  }  
  
  

  
	//한개 이미지 업로드하기
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		String imageFileName= null;
		Map<String, String> articleMap = new HashMap<String,String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName=mFile.getOriginalFilename();
			
			System.out.println("upload의 이미지파일네임 : "+ imageFileName);
			System.out.println("upload의 파일네임 : "+ fileName);
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+ fileName);
			if(mFile.getSize()!=0){ //File Null Check
				if(! file.exists()){ //경로상에 파일이 존재하지 않을 경우
					if(file.getParentFile().mkdirs()){ //경로에 해당하는 디렉토리들을 생성
							file.createNewFile(); //이후 파일 생성
					}
				}
				mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+imageFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
			}
		}
		return imageFileName;
	}
  
  
  
  
  

	//다중 이미지 보여주기
//	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
//	
//	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
//			  HttpServletRequest request, HttpServletResponse response) throws Exception{
//		System.out.println("받아온 articleNO : " + articleNO);
//		String viewName = (String)request.getAttribute("viewName");
//		Map articleMap=boardService.viewArticle(articleNO);
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName(viewName);
//		mav.addObject("articleMap", articleMap);
//		return mav;
//	}
  
  
  
    

  
  
  
  //다중 이미지 글 추가하기
//  @Override
//  @RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
//  @ResponseBody
//  public ResponseEntity  addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
//	multipartRequest.setCharacterEncoding("utf-8");
//	String imageFileName=null;
//	
//	Map articleMap = new HashMap();
//	Enumeration enu=multipartRequest.getParameterNames();
//	while(enu.hasMoreElements()){
//		String name=(String)enu.nextElement();
//		String value=multipartRequest.getParameter(name);
//		articleMap.put(name,value);
//	}
//	
//	//로그인 시 세션에 저장된 회원 정보에서 글쓴이 아이디를 얻어와서 Map에 저장합니다.
//	HttpSession session = multipartRequest.getSession();
//	MemberVO memberVO = (MemberVO) session.getAttribute("member");
//	String id = memberVO.getId();
//	articleMap.put("id",id);
//	
//	
//	List<String> fileList =upload(multipartRequest);
//	//첨부한 이름을 fileList로 반환
//	List<ImageVO> imageFileList = new ArrayList<ImageVO>();
//	if(fileList!= null && fileList.size()!=0) {
//		for(String fileName : fileList) {
//			// 전송된 이미지 정보를 imageVO객체의 속성에 차례대로 저장한 후 imageFileList에 다시 저장
//			ImageVO imageVO = new ImageVO();
//			imageVO.setImageFileName(fileName);
//			imageFileList.add(imageVO);
//		}
//		articleMap.put("imageFileList", imageFileList);
//		// imageFileList를 다시 articleMap에 저장
//	}
//	String message;
//	ResponseEntity resEnt=null;
//	HttpHeaders responseHeaders = new HttpHeaders();
//    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
//	try {
//		int articleNO = boardService.addNewArticle(articleMap);
//		//articleMap을 서비스 클래스로 전달
//		if(imageFileList!=null && imageFileList.size()!=0) {
//			for(ImageVO  imageVO:imageFileList) {
//				// 첨부한 이미지들을 for문을 이용해 업로드
//				imageFileName = imageVO.getImageFileName();
//				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
//				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
//				//destDir.mkdirs();
//				FileUtils.moveFileToDirectory(srcFile, destDir,true);
//			}
//		}
//		    
//		message = "<script>";
//		message += " alert('새글을 추가했습니다.');";
//		message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
//		message +=" </script>";
//	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
//	    
//		 
//	}catch(Exception e) {
//		if(imageFileList!=null && imageFileList.size()!=0) {
//		  for(ImageVO  imageVO:imageFileList) {
//			  // 오류발생시 temp폴더의 이미지들을 모두 삭제
//		  	imageFileName = imageVO.getImageFileName();
//			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
//		 	srcFile.delete();
//		  }
//		}
//
//		
//		message = " <script>";
//		message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
//		message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
//		message +=" </script>";
//		resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
//		e.printStackTrace();
//	}
//	return resEnt;
//  }
	



	//다중 이미지 업로드하기
//	private List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
//		// 이미지 파일 이름이 저장된 list를 반환
//		List<String> fileList= new ArrayList<String>();
//		Iterator<String> fileNames = multipartRequest.getFileNames();
//		while(fileNames.hasNext()){
//			String fileName = fileNames.next();
//			System.out.println("fileName : " + fileName);
//			MultipartFile mFile = multipartRequest.getFile(fileName);
//			String originalFileName=mFile.getOriginalFilename();
//			fileList.add(originalFileName);
//			// 첨부한 이미지 파일의 이름들을 차례대로 저장
//			File file = new File(ARTICLE_IMAGE_REPO +"\\"+ fileName);
//			if(mFile.getSize()!=0){ //File Null Check
//				if(! file.exists()){ //경로상에 파일이 존재하지 않을 경우
//					if(file.getParentFile().mkdirs()){ //경로에 해당하는 디렉토리들을 생성
//							file.createNewFile(); //이후 파일 생성
//					}
//				}
//				mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+originalFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
//			}
//		}
//		return fileList;
//	}

}
