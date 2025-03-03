package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.security.AuthVO;
import com.example.demo.security.MemberVO;

@Mapper
public interface MemberMapper {

	MemberVO selectEmail(String username);

	List<AuthVO> selectAuts(String username);

	int insert(MemberVO mvo);

	int insertAuth(String email);

}
