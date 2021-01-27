package com.example.demo.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

@Slf4j
@Repository
@AllArgsConstructor
public class EmployeeRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	public Collection<Employee> getEmployees() {
		String sql = "SELECT * FROM TBL_EMPLOYEES WHERE ISDELETE = 'N'";
		return this.namedParameterJdbcTemplate.query(sql, new RowMapper<Employee>()
		{
			@Override
			public Employee mapRow(ResultSet rs, int i) throws SQLException {
				return Employee.builder()
						.id(rs.getLong("ID"))
						.firstName(rs.getString("FIRST_NAME"))
						.lastName(rs.getString("LAST_NAME"))
						.email(rs.getString("EMAIL"))
						.build();
			}

		});
		
	}
	public Optional<Employee> getEmployeesById(Long id) {
		String sql = "SELECT * FROM TBL_EMPLOYEES WHERE ID = :id";
		
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("id", id);
			
			return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<Employee>>() {
					@Override
					public Optional<Employee> mapRow(ResultSet rs, int i) throws SQLException {
						return Optional.of(Employee.builder()
								.id(rs.getLong("ID"))
								.firstName(rs.getString("FIRST_NAME"))
								.lastName(rs.getString("LAST_NAME"))
								.email(rs.getString("EMAIL"))
								.build());
						}
			});
			
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return Optional.empty();
		}
	}
	
	public int saveEmployee(Employee employee) {
		String sql = "INSERT INTO TBL_EMPLOYEES (first_name, last_name, email) VALUES\n" + 
					"(:firstName, :lastName, :email)";
	
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("firstName", employee.getFirstName());
			parameters.addValue("lastName", employee.getLastName());
			parameters.addValue("email", employee.getEmail());
			
			return this.namedParameterJdbcTemplate.update(sql, parameters);

	}
	public int updateEmployeeAll(Employee employee) {
		String sql = "UPDATE TBL_EMPLOYEES SET first_name = :firstName, last_name = :lastName, email = :email  WHERE ID= :id";
		
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("id", employee.getId());
			parameters.addValue("firstName", employee.getFirstName());
			parameters.addValue("lastName", employee.getLastName());
			parameters.addValue("email", employee.getEmail());
	
			
			return this.namedParameterJdbcTemplate.update(sql, parameters);
		
	}
	
	public int updateEmployee(Employee employee) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_EMPLOYEES SET ");
		if(StringUtils.hasText(employee.getFirstName())) {
			sql.append("first_name = :firstName ");
			parameters.addValue("firstName", employee.getFirstName());
		}
		if(StringUtils.hasText(employee.getLastName())) {
			sql.append(",last_name = :lastName ");
			parameters.addValue("lastName", employee.getLastName());
		}
		if(StringUtils.hasText(employee.getEmail())) {
			sql.append(",email = :email ");
			parameters.addValue("email", employee.getEmail());
		}
		
		sql.append("WHERE ID = :id");
		parameters.addValue("id", employee.getId());
		return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int deleteEmployee(Employee employee) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_EMPLOYEES SET ");
		
		sql.append("isDelete = :isDelete ");
		parameters.addValue("isDelete", employee.getIsDelete());

		sql.append("WHERE ID = :id");
		parameters.addValue("id", employee.getId());
		return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
	}
}

