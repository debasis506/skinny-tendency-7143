package com.masai.service;

import java.util.List;
import java.util.Map;

import com.masai.admin.Course;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;

public interface CourseService {

	public String addProduct(Course pro, Map<Integer, Course> products);

	public void viewAllProducts(Map<Integer, Course> products) throws ProductException;

	public void deleteProduct(int id, Map<Integer, Course> products) throws ProductException;

	public String updateProduct(int id, Course prod, Map<Integer, Course> products) throws ProductException;

	
	
}
