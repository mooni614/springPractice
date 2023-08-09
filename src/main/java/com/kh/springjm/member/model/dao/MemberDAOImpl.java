package com.kh.springjm.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kh.springjm.member.model.vo.Member;

@Repository // 실제 동작하는 dao를 알리는 어노테이션, spring에서 bean으로 자동 생성
public class MemberDAOImpl implements MemberDAO {

	
	// 저장하는 공간
	private List<Member> list = new ArrayList<Member>();
	
	
	@Override
	public int insertMember(Member member) {
		//아이디가 없거나 길이가 0이면 아이디가 없다는 뜻
		// 굳이 저장할 필요가 없다.
		
		if(member.getId() == null || member.getId().length() == 0) {
			return 0;
			
			for(Member m : list) {
				if(m.equals(member.getId()) {
					return 0;
					
				}
			}
			
		}
		
		list.add(member); // 추가
		return 1;
		
	
	}

	@Override
	public List<Member> selectAll() {
		
		
		return list;
	}

	@Override
	public Member selectById(String id) {
		
		for(Member m : list) {
			if(m.getId().equals(id)) {
				return m;
				
			}
			
		}
		
		
		return null; // id검색후 데이터가 없으면 null 반환
	}

}
