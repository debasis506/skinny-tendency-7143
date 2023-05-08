package com.masai.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.masai.admin.Course;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;

public class CourseServiceImpl implements CourseService {

	@Override
	public String addProduct(Course prod, Map<Integer, Course> products) {
		// TODO Auto-generated method stub
//as our ids are always unique thats why directly putting into products
		products.put(prod.getId(), prod);

		return "Course added successfully";

	}

	@Override
	public void viewAllProducts(Map<Integer, Course> products) throws ProductException {
		// TODO Auto-generated method stub
		if (products != null && products.size() > 0) {
			for (Map.Entry<Integer, Course> me : products.entrySet()) {
				System.out.println(me.getValue());
			}

		} else {
			throw new ProductException("Course List is empty");
		}
	}

	@Override
	public void deleteProduct(int id, Map<Integer, Course> products) throws ProductException {

		// System.out.println(products);
		if (products != null && products.size() > 0) {

			if (products.containsKey(id)) {
				products.remove(id);
				System.out.println("product deleted successfully");

			} else {
				throw new ProductException("Course not found");
			}

		} else {
			throw new ProductException("Course list is empty");
		}

	}

	@Override
	public String updateProduct(int id, Course prod, Map<Integer, Course> products) throws ProductException {

		if (products != null && products.size() > 0) {

			if (products.containsKey(id)) {
				products.put(id, prod);
				return "Course has successfully updated";
			} else {
				throw new ProductException("Course not found");
			}

		} else {
			throw new ProductException("Course list is empty");
		}

	}

}
