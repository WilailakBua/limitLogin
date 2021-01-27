package com.example.demo.loginController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.employee.EmployeeController;
import com.example.demo.employee.EmployeeService;
import com.example.demo.entity.Login;
import com.example.demo.entity.LoginDto;
import com.example.demo.service.LoginService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
	
	private final LoginService loginService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
		log.info("login " + loginDto);
		return ResponseEntity.ok(this.loginService.loginService(loginDto));
	}
	

}
