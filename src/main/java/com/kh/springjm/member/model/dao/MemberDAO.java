package com.kh.springjm.member.model.dao;

import java.util.List;

import com.kh.springjm.member.model.vo.Member;

public interface MemberDAO {

	
	int insertMember(Member member);
	
	List<Member> selectAll();
	Member selectById(String id); 
}
