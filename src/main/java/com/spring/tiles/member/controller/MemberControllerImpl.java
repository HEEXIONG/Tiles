package com.spring.tiles.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.tiles.member.service.MemberService;
import com.spring.tiles.member.vo.MemberVO;



@Controller("memberController")
//@EnableAspectJAutoProxy
public class MemberControllerImpl implements MemberController {
	
	
	// LoggerFactory 클래스를 이용해 Logger 클래스 객체를 가져옵니다.
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO ;
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		// Logger 클래스의 info() 메서드로 로그 메시지 레벨을 info로 설정합니다.
		logger.info("viewName: "+ viewName);
		
		// Logger 클래스의 debug() 메서드로 로그 메시지 레벨을 debug로 설정합니다.
		logger.debug("viewName: "+ viewName);
		List membersList = memberService.listMembers();
		
		// 브라우저에서 컨트롤러 요청 시 요청명에 대해 뷰이름을 가져옵니다.
		// 그리고 다시 ModelAndView 객체에 설정한 후 뷰리졸버로 반환 처리 합니다.
		//                  아래 ModelAndView() 매개변수 viewName은 tiles_member xml 파일의 definition 태그에 설정한 뷰이름과 일치합니다. 
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		// ModelAndView 객체에 설정한 뷰이름 mav를 타일즈 뷰리졸버로 반환 처리 합니다.
		return mav;
		// 실행 확인은 톰캣을 재실행한 후 http://localhost:9005/tiles/member/listMembers.do 로 확인해 봅니다.
	}

	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}


}
