package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.entity.Login;
import com.example.demo.entity.LoginDto;
import com.example.demo.excaption.ErrorCode;
import com.example.demo.excaption.NotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class LoginRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public Optional<Login> getUser(LoginDto loginDto) {
		String sql = "SELECT * FROM USER_LOGIN WHERE USER_NAME = :userName AND PASSWORD = :password";
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("userName", loginDto.getUserName());
			parameters.addValue("password", loginDto.getPassword());
			return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<Login>>() {
					@Override
					public Optional<Login> mapRow(ResultSet rs, int i) throws SQLException {
						return Optional.of(Login.builder()
								.id(rs.getLong("ID"))
								.userName(rs.getString("USER_NAME"))
								.password(rs.getString("PASSWORD"))
								.count(rs.getInt("COUNT"))
								.build());
						}
			});
			
			
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return Optional.empty();
		}
	}
	
	public Optional<Login> getDataUser(LoginDto loginDto) {
		String sql = "SELECT * FROM USER_LOGIN WHERE USER_NAME = :userName";
		
		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("userName", loginDto.getUserName());
			
			return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<Login>>() {
					@Override
					public Optional<Login> mapRow(ResultSet rs, int i) throws SQLException {
						return Optional.of(Login.builder()
								.id(rs.getLong("ID"))
								.userName(rs.getString("USER_NAME"))
								.password(rs.getString("PASSWORD"))
								.count(rs.getInt("COUNT"))
								.build());
						}
			});
			
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return Optional.empty();
		}
	}
	
	public int updateCount(Login login) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE USER_LOGIN  SET ");
			sql.append("count = :count ");
			parameters.addValue("count", login.getCount());
		
		
		sql.append("WHERE ID = :id");
		parameters.addValue("id", login.getId());
			
			String result = "success";
		return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
	}
	

}
