package com.masai.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.masai.admin.Course;
import com.masai.admin.Student;
import com.masai.admin.Transaction;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;

public interface StudentService {

	public boolean login(String email,String password, Map<String, Student> customers) throws InvalidDetailsException;

	public void signUp(Student cus, Map<String, Student> customers) throws DuplicateDataException;

	public boolean buyProduct(int id, int qty, String email, Map<Integer, Course> products,
			Map<String, Student> customers, List<Transaction> transactions)
			throws InvalidDetailsException, ProductException;

	public boolean addMoneyToWallet(double amount, String email, Map<String, Student> customers);

	public double viewWalletBalance(String email, Map<String, Student> customers);

	public Student viewCustomerDetails(String email, Map<String, Student> customers);

	public List<Student> viewAllCustomers(Map<String, Student> customers) throws ProductException;

}
