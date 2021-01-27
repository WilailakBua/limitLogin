package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
	
	
	private final EmployeeService employeeService;
	
	
	@GetMapping
	public ResponseEntity<?> getEmployee() {
		
		return ResponseEntity.ok(this.employeeService.getEmployees());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id) throws Exception {
		log.info("id : " + id);
		return ResponseEntity.ok(this.employeeService.getEmployeesById(id));
	}

	@PostMapping
	public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
		log.info("Save Employee", employeeDto);
		this.employeeService.saveEmployee(employeeDto);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping
	public ResponseEntity<?> updateEmployeeAll(@RequestBody EmployeeDto employeeDto) throws Exception {
		log.info("updateEmployeeAll Employee", employeeDto);
		this.employeeService.updateEmployeeAll(employeeDto);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
		log.info("updateEmployee Employee", employeeDto);
		this.employeeService.updateEmployee(employeeDto);
		return ResponseEntity.noContent().build();
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws Exception {
		log.info("delete id : ", id);
		return ResponseEntity.ok(this.employeeService.deleteEmployee(id));
	}
	
	

}
