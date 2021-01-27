package com.example.demo.entity;

import com.example.demo.employee.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {
	
	private Long id;
	private String userName;
	private String password;
	private int count;
}