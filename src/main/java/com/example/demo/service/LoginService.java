package com.example.demo.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeDto;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.EmployeeService;
import com.example.demo.entity.Login;
import com.example.demo.entity.LoginDto;
import com.example.demo.excaption.ErrorCode;
import com.example.demo.excaption.NotFoundException;
import com.example.demo.excaption.ResponseModal;
import com.example.demo.repository.LoginRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService {
	private final LoginRepository loginRepository;
	private final ModelMapper modelMapper;
	
	
//	@Transactional
	public LoginDto loginService(LoginDto loginDto) {
		Optional<Login> dataByUser = Optional.ofNullable(new Login());
		Optional<Login> login = Optional.ofNullable(new Login());
		int count = 0;
		int countFalse = 0;
//		if(login.isEmpty()) {
			try {

			    
			    
			    dataByUser = Optional.ofNullable(this.loginRepository.getDataUser(loginDto)
						.orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "User not found")));
				
			    login = Optional.ofNullable(this.loginRepository.getUser(loginDto)
						.orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "Password incorrect")));
			    if(dataByUser.get().getCount() == 3) {
			    	throw new NotFoundException("User Lock");
			    }
			    if(dataByUser.get().getCount() > 0 || dataByUser.get().getCount() < 3) {
			    	countFalse = calCount("success",count);
					dataByUser.get().setCount(countFalse);
					this.loginRepository.updateCount(dataByUser.get()); 
			    }
			} catch(NotFoundException e) {
				if(e.getMessage().equals("Password incorrect")) {
					System.out.println("err");
					
					count = dataByUser.get().getCount();
					if(count == 3){
				    	throw new NotFoundException("User Lock");
				    }else {
				    	countFalse = calCount("fail",count);
						dataByUser.get().setCount(countFalse);
						this.loginRepository.updateCount(dataByUser.get());
				    }
					
				}
				throw new NotFoundException(e.getMessage());
			}

		 return modelMapper.map(login, LoginDto.class);
	}

	
	public int calCount(String pass, int num) {
	
		if(pass.equals("fail")) {
			num++;
		} else {
			num = 0;
		}
		System.out.println("Count " + num);
		return num;
	}

}
