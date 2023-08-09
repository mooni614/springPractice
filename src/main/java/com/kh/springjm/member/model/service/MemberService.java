package com.kh.springjm.member.model.service;

import java.util.List;

import com.kh.springjm.member.model.vo.Member;

public interface MemberService {

	
	int joinMember(Member member);
		List<Member> getAllList();
			Member login(String id);
			
	
		
	
}
