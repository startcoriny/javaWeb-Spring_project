package com.myspring.pro29.ex03;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/boards")
// 첫번째 단계의 요청명을 매핑
public class BoardController {
	static Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	// get 방식으로 요청하므로 모든 글의 정보를 조회
	
	public ResponseEntity<List<ArticleVO>> listArticles() {
		logger.info("listArticles 메서드 호출");
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		for (int i = 0; i < 10; i++) {
			ArticleVO vo = new ArticleVO();
			vo.setArticleNO(i);
			vo.setWriter("이순신"+i);
			vo.setTitle("안녕하세요"+i);
			vo.setContent("새 상품을 소개합니다."+i);
			list.add(vo);
		}
		
		return new ResponseEntity(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{articleNO}", method = RequestMethod.GET)
	// get방식으로 요청하면서 글 번호를 전달하므로 글 번호에 대한 글 정보를 조회
	
	public ResponseEntity<ArticleVO> findArticle (@PathVariable("articleNO") Integer articleNO) {
		logger.info("findArticle 메서드 호출");
		ArticleVO vo = new ArticleVO();
		vo.setArticleNO(articleNO);
		vo.setWriter("홍길동");
		vo.setTitle("안녕하세요");
		vo.setContent("홍길동 글입니다");
		return new ResponseEntity(vo,HttpStatus.OK);
		// HttpStatus.OK는 "200 OK"를 나타냄
		// 클라이언트의 요청이 성공적으로 처리되었음을 나타내는 상태 코드
		// GET 요청에 대한 성공 응답으로 사용
		// 클라이언트에게 요청된 데이터가 정상적으로 반환되었음을 알려줌
	}	
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	// post방식으로 요청하므로 요청 시 json으로 전달되는 객체를 새 글로 추가
	
	public ResponseEntity<String> addArticle (@RequestBody ArticleVO articleVO) {
		ResponseEntity<String>  resEntity = null;
		try {
			logger.info("addArticle 메서드 호출");
			logger.info(articleVO.toString());
			resEntity =new ResponseEntity("ADD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
			// 예외 객체 e에서 발생한 예외 메시지를 반환하는 메서드
			// 예외가 발생한 경우 해당 예외 메시지를 클라이언트에게 반환하는데 사용
			
			// HttpStatus.BAD_REQUEST는 400 Bad Request
			// 클라이언트의 요청이 잘못된 구문 또는 매개변수 오류 등으로 인해 처리할 수 없는 경우에 사용
		}
		
		return resEntity;
	}	
	
	//수정하기
	@RequestMapping(value = "/{articleNO}", method = RequestMethod.PUT)
	// put방식으로 요청하므로 articleNO에 대한 글을 전달되는 JSON정보로 수정
	public ResponseEntity<String> modArticle (@PathVariable("articleNO") Integer articleNO,
									@RequestBody ArticleVO articleVO) {
									// 전송된 JSON회원 정보를 바로 ArticleVO객체의 속성에 설정

		ResponseEntity<String>  resEntity = null;
		try {
			logger.info("modArticle 메서드 호출");
			logger.info(articleVO.toString());
			resEntity =new ResponseEntity("MOD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}
	
	//삭제하기
	@RequestMapping(value = "/{articleNO}", method = RequestMethod.DELETE)
	// DELETE 방식으로 요청하므로 전달되는 articleNO에 대한 글을 삭제
	public ResponseEntity<String> removeArticle (@PathVariable("articleNO") Integer articleNO) {
		ResponseEntity<String>  resEntity = null;
		try {
			logger.info("removeArticle 메서드 호출");
			logger.info(articleNO.toString());
			resEntity =new ResponseEntity("REMOVE_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	

}
