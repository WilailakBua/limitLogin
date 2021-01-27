package com.example.demo.entity;

import com.example.demo.employee.EmployeeDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
	
	private String userName;
	private String password;

}
