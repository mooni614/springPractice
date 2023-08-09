package com.kh.springjm;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Spring mvc 개념 정리
 * Spring은 서블릿 대비 어노테이션(@Annotation)의 활용도가 높아졌다.
 * 
 * Spring프레임워크는 상속설계를 지양하고 interface 기반 설계를 권장한다.
 * Dependency injection(의존성 주입)을 통해 객체를 직접 생성하지 않고 spring에서 생성주입시키기 편하기 위해서 new를 안쓰고 프레임워크에서 대신 생성한다.
 * 장점: 의존성을 줄이고 메모리 관리가 우수하다.
 * 
 * spring mvc에서 철저하게 mvc2패턴을 사용하도록 설계되어 있다.
 * -> request(url + 파라미터) -> Controller(java 객체) + model -> View(jsp or Thymleaf)
 * 
 * 서블릿 class 단위로 url을 매핑했던 기능이 @Controller의 핸들러메서드로 이관
 * 
 * web-inf구조(보안기능)로 인해 url로 jsp 접근이 안된다.
 * html에 직접적인 접근이 불가하고 반드시 controller에서 view로 접근이 필요하다.
 * 
 */


@Controller  
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	
	/**
	 * 핸들러 메서드 (handler method)
	 * - url로 매핑이 되어 있는 메서드를 지칭함. 특정 단위 하나의 기능을 담당하는 것이 일반적 
	 *
	 *	return 값이 문자열일 경우 기본적으로 forward되는 jsp페이지를 지칭한다.
	 *	매핑되는 url은 주로 .do를 붙여서 특정행위에 대한 처리를 담당시킨다.
	 * 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	// url을 매핑시키는 가장 간단한 방법
	
	@RequestMapping("home2.do")
	public String home2() {
		
		return "home2"; // /WEB-INF/views/home2.jsp로 forword 전달됨
	}
	
	
	//home2의 메서드 이름 -> 가장 유사한 jsp로 연결한다.
//	@RequestMapping("home2.do")
//	public void home2() {}
	
	
	//home3 메서드에서 home2.do를 호출하고 싶을 경우
/*	@RequestMapping("home3.do")
	public String home3() {
		
		return "home2.do"; // /WEB-INF/views/home2.do.jsp로 forword 전달됨 -> 주소가 잘못되어 에러가 발생할 수 있다!!
	}
	
	
	@RequestMapping("home3.do")
	public String home3() {
		
		return "redirect:home2.do"; // 리다이렉트로 전달되는 방법
	}
	*/
}
