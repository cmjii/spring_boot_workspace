package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;

public interface CommentService {

	int insert(CommentVO cvo);

	PagingHandler getList(long bno, PagingVO pgvo);
	
	int edit(CommentVO cvo);

	int delete(long cno);


}
