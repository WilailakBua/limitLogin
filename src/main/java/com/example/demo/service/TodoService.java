package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TodoRepository;

import com.example.demo.entity.Todo;
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	public List<Todo> getAllTodos() {
		return this.todoRepository.findAll();
	}
	
	public void createNewTodo(String title) {
		Todo todo = new Todo();
		todo.setTitle(title);
		this.todoRepository.save(todo);
	}
	
	public ResponseEntity<?> completingTodo(Long id){
		Optional<Todo> todo = this.todoRepository.findById(id);
		if(todo.isPresent()) {
			todo.get().setCompleted(!todo.get().isCompleted());
			this.todoRepository.save(todo.get());
			return ResponseEntity.ok().body("Update Success");
		}else {
			return ResponseEntity.ok().body("Not found id");
		}
		
	}
	
	public ResponseEntity<?> deleteTodo(Long id){
		try {
			Optional<Todo> todo = this.todoRepository.findById(id);
			if(todo.isPresent()) {
			this.todoRepository.deleteById(id);
			return ResponseEntity.ok().body("Delete Todo");
			}else {
				return ResponseEntity.ok().body("Not found id");
			}
		}catch(Exception e){
			return ResponseEntity.ok().body(e);
		}
	}
	
	

}
