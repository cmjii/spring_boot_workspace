package com.example.demo.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FileVO {
	private String uuid;
	private String saveDir;
	private String fileName;
	private int fileType;
	private long bno;
	private long fileSize;
	private String regAt;
}
