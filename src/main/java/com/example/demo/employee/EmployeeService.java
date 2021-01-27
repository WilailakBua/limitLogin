package com.example.demo.employee;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.excaption.BadRequestException;
import com.example.demo.excaption.ErrorCode;
import com.example.demo.excaption.NoContentException;
import com.example.demo.excaption.NotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public List<EmployeeDto> getEmployees() {
		return this.employeeRepository.getEmployees().stream()
				.map(employee -> modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}
	
	public EmployeeDto getEmployeesById(Long id) throws Exception{
		Employee employee = this.employeeRepository.getEmployeesById(id).orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "employee not found"));
		return modelMapper.map(employee, EmployeeDto.class);
	}
	
	@Transactional
	public int saveEmployee(EmployeeDto employeeDto) throws Exception{
		return this.employeeRepository.saveEmployee(modelMapper.map(employeeDto, Employee.class));
	}
	
	@Transactional
	public void updateEmployeeAll(EmployeeDto employeeDto) throws Exception{
//		this.employeeRepository.getEmployeesById(employeeDto.getId()).orElseThrow(() -> new NotFoundException("not found"));
//		return this.employeeRepository.updateEmployeeAll(modelMapper.map(employeeDto, Employee.class));
		if (this.employeeRepository.updateEmployeeAll(modelMapper.map(employeeDto, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PUT.code, "employee ID not found");
        }
	}
	
//	@Transactional
//	public void updateEmployee(EmployeeDto employeeDto) throws Exception{
////		this.employeeRepository.getEmployeesById(employeeDto.getId()).orElseThrow(() -> new NotFoundException("not found"));
////		return this.employeeRepository.updateEmployee(modelMapper.map(employeeDto, Employee.class));
//		if (this.employeeRepository.updateEmployee(modelMapper.map(employeeDto, Employee.class)) == 0) {
//            throw new BadRequestException(ErrorCode.ERR_PATCH.code, "employee ID not found");
//        }
//	}
	
	public int updateEmployee(EmployeeDto employeeDto) throws NoContentException, NotFoundException{
	       this.employeeRepository.getEmployeesById(employeeDto.getId()).orElseThrow(() -> new NotFoundException("not found"));
	       if(!StringUtils.hasText(employeeDto.getEmail()) && 
	         !StringUtils.hasText(employeeDto.getFirstName()) && 
	         !StringUtils.hasText(employeeDto.getLastName())) {
	        throw new NoContentException("NO CONTENT NAJA");
	        
	       } 
	       return this.employeeRepository.updateEmployee(modelMapper.map(employeeDto, Employee.class));
	    }
	
	@Transactional
	public int deleteEmployee(Long id) throws Exception {
		Employee employee = this.employeeRepository.getEmployeesById(id).orElseThrow(() -> new NotFoundException("not found"));
		employee.setIsDelete("Y");
		return this.employeeRepository.deleteEmployee(modelMapper.map(employee, Employee.class));
	}
	
	
}
