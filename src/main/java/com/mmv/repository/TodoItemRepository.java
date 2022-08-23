package com.mmv.repository;

import org.springframework.data.repository.CrudRepository;

import com.mmv.model.TodoItem;


public interface TodoItemRepository extends CrudRepository<TodoItem, Long>{

}
