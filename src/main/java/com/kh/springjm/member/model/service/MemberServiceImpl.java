package com.kh.springjm.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.springjm.member.model.dao.MemberDAO;
import com.kh.springjm.member.model.vo.Member;

@Service // 서비스임을 알리고, bean을 통해서 자동으로 객체 생성 되는 것 
public class MemberServiceImpl implements MemberService{

	
	@Autowired // 사용자가 별도로 객체를 만들지 않고 BeanFactory로 부터 객체의 관리를 위임하는 어노테이션 
	private MemberDAO dao;

	@Override
	public int joinMember(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Member> getAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member login(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
