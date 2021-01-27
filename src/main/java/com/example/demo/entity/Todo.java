package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	 private Long id;
	
	@NotNull
	 private String title;
	
	@NotNull
	 private boolean completed = false;
}
