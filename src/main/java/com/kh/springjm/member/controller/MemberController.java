package com.kh.springjm.member.controller;

import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.springjm.member.model.service.MemberService;
import com.kh.springjm.member.model.vo.Member;

/*
 *	controller 주요 개념 정리
 *  	-> 노션 스프링 세팅
 * 
 * */
 

// 서블릿 대체하는 어노테이션
@Controller
public class MemberController {
	
	
	// spring에서 생성한 bean객체와 연결이 된다.
	@Autowired
	private MemberService service;
	
	
	//메서드를 정의하지 않는 경우 get/post 둘다 처리 가능
	@RequestMapping("/member/index.do")
	public String index() {
		
		
		return "member/index"; // viewResolver로 jsp파일과 연결됨 
	}

	
	// -------핸들러 메서드 스타일 정리
	// 1. 서블릿 스타일
	// 장점: 서블릿 하던 사람이나 서블릿 프로젝트의 호환성을 쥬이할 수 있다
	// 단점: 안쓴다
	@RequestMapping("/member/memberServlet.do")
	public String memberServlet(HttpServletRequest req, HttpServletRequest resp, HttpSession session) {
		
	
		//회원가입시 member 내용을 객체생성해서 저장하는 내용
		Member member = new Member();
		member.setId(req.getParameter("id"));
		member.setName(req.getParameter("name"));
		member.setGender(req.getParameter("gender"));
		member.setAge(Integer.parseInt(req.getParameter("age")));
		member.setDevLang(req.getParameterValues("devLang"));
		
		//jsp로 파라미터 보내는 방법
		req.setAttribute("member",member);
		
		// session 사용법 old문법
		req.getSession().setAttribute("id",member.getId());
		
		//Spring session사용법
		session.setAttribute("id",member.getId());
		
		
		
		return "member/memberView";
		
	}
	
	//@RequestParam 을 통한 html - form-method 매핑하는 방법
	// RequestParam 어노테이션으로 form-name과 핸들러 메서드의 매개변수와 매핑시키는 방법
	// 스프링에서 가장 올드한 스타일 -> 아직 사용됨
	
	// Model : request, response의 기능을 대체하기 위한 객체, 주로 파라미터에 담는 용도로 수행
	//@RequestParam(value="formName") : formName을 메서드 인자명과 매핑시키는 방법
	// -> 만일 formName과 메서드 인자명이 같은 경우에는 생략가능, 다른 경우에는 반드시 있어야 한다.
	
	
	@RequestMapping("/member/memberParams2.do")
	public String memberParam2(Model model, @RequestParam(value="id") String userId,
											@RequestParam(value="name") String userName,
											@RequestParam(value="gender") String gender,
											@RequestParam(value="age") int age,
											@RequestParam(value="devLang") String[] devLang){
		
		Member m = new Member();
		m.setId(userId);
		m.setName(userName);
		m.setAge(age);
		m.setGender(gender);
		m.setDevLang(devLang);
		
		
		
		//model을 통해서 view(jsp)로 파라미터를 넘기는 방법
		//addAttribute: key- value 형태로 속성값을 저장하는 방법, 
		//request의 기능을 지원하는 메서드 
		
		
		
		model.addAttribute("member", m);
		
		return "member/memberView";
	}
	
	//객체(vo)를 파라메터로 활용하는 방법
	// 사용자가 지정한 vo의 멤버 변수의 명칭과 form의 name을 일치시켜 가져오는 방법
	// - 반드시! 멤버변수이름과 form name이 꼭 일치되어야 한다. 자동으로 매핑
	// 주의: vo에 기본자료형(문자열 포함)만 지원이 간으하다. 이외에 다른 객체 자료형있는 경우 처리되지 않는다.
	// 실제 현업에서 가장 많이 애용하는 스타일 1
	
	@RequestMapping("member/memberCommand2.do")
	String memberCommand2(Model model, Member member) {
		model.addAttribute("member", member);
		System.out.println("memberCommand : " + member);
		return "member/memberView";
	}
	
	
	// model없이 vo 파라미터를 view로 넘기는 방법
	// @ModelAttribute("name") : view에서 model정보를  name으로 넘기는 방법
	@RequestMapping("/member/memberCommand.do")
	String memberCommand(@ModelAttribute("member") Member member) {
		return "member/memberView";
	}
	
	
	//컬렉션의 map을 활용법
	// - form파라미터를 map으로 가져오는 방법
	// -실제 현업에서 가장 많이 사용되는 스타일2
	// 장점: vo상관없이 모든 파라미터를 처리가능하다.
	// 단점: value 's'에 해당되는 배열이 잘 처리되지 않음 -> values문제는 이런 상황을 만들지 않거나 RequestParam을 통해 특별히 처리한다.
	// -> 현업에서 map을 통해서 파라미터를 받고 mybatis 부로 연결하고 table명칭까지 일치하면 거의 자동화가능
	
	@RequestMapping("member/memberMap2.do")
	String memberMap2(Model model, @RequestParam Map<String, Object> param) {
		System.out.println("memberMap : " + param);
		model.addAttribute("member", param);
		return "member/memberView";
	}
	
	@RequestMapping("member/memberMap.do")
	String memberMap(Model model, @RequestParam Map<String, Object> param, String[] devLang) {
		System.out.println("memberMap : " + param);
		param.put("devLang", devLang);
		model.addAttribute("member", param);
		return "member/memberView";
	}
	
	//header, cookie정보 받아오기
	//Writer: json이나 문자열 형태로 만들때는 Writer를 활용한다. -> resp에 있었던 객체
	//@RequestHeader: header값 가져오는 어노테이션
	//@CookieValue: 쿠키 정보를 가져올때 사용하는 어노테이션
	// void인 경우는 return 없을 때 활용 -> view가 존재하지 않는다
	
	@RequestMapping("/member/memberAddInfo.do")
	public void memberAddInfo(
				Writer writer,
				@RequestHeader(value="user-agent") String userAgent,
				@RequestHeader(value="referer") String prevPage,
				@CookieValue(value="saveId", required = false) String saveId
				) {
		try {
			writer.append("<html>");
			writer.append("userAgent : " + userAgent+"<br>");
			writer.append("prevPage : " + prevPage+"<br>");
			writer.append("saveId : " + saveId+"<br>");
			writer.append("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Service 객체 활용, ModelAndView스타일, 에러처리하는 방법
	// 
	
	
	@RequestMapping("/member/joinMember.do")
	public ModelAndView joinMember(ModelAndView model,Member member) {
		
		//실제 저장을 하기 위해서 저장을 시키는 service가 필요하다.
		// 아직 객체가 만들어 지지 않다 그래서 에러! 
		
		int result = service.joinMember(member);
		if(result > 0) {
			
			// 성공
			// addObject : view로 보낼 파라미터를 처리하는 방법 model의 일 
			// setViewName: retrun하였던 view의 이름을 설정하는 메서드
			model.addObject("msg","회원가입 성공하였습니다!");
			model.setViewName("member/index");
		}else {
			model.setViewName("redirect:error.do");
		}
		
		
		return model;
		
	}
	
	// 7. List 객체를 jsp로 보내는 방법 + error 코드
		@RequestMapping("/member/memberList.do")
		public String memberList(Model model) {
			List<Member> list = service.getAllList();
			System.out.println(list);
			model.addAttribute("list", list);
			if(list != null) {
				return "member/memberList";
			}else {
				return "redirect:error.do";
			}
		}
	
	
	@RequestMapping("/member/error.do")
	public String errorPage(String msg) {
		System.out.println("에러 발생 로그 출력!");
		return "common/error";
		
	}
	
}
