package com.masai.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.masai.admin.*;

import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;

public class StudentServiceImpl implements StudentService {

	@Override
	public void signUp(Student cus, Map<String, Student> customers) throws DuplicateDataException {

		if (customers.containsKey(cus.getEmail())) {
			throw new DuplicateDataException("Student already exists , please login");
		} else {

			customers.put(cus.getEmail(), cus);

		}

	}

	@Override
	public boolean login(String email,String password, Map<String, Student> customers) throws InvalidDetailsException {

		if (customers.containsKey(email) ) {
			
			if(customers.get(email).getPassword().equals(password)) {
				return true;
			}
			else {
				throw new InvalidDetailsException("Invalid Credentials");
			}
			
		} else {
			throw new InvalidDetailsException("you have not sign up yet, please signup");
		}

	}

	@Override
	public boolean buyProduct(int id, int qty, String email, Map<Integer, Course> products,
			Map<String, Student> customers, List<Transaction> transactions)
			throws InvalidDetailsException, ProductException {

		if (products.size() == 0)
			throw new ProductException("Course list is empty");

		if (products.containsKey(id)) {

			Course prod = products.get(id);

			if (prod.getQty() >= qty) {

				Student cus = customers.get(email);

				double buyingPrice = qty * prod.getPrice();

				if (cus.getWalletBalance() >= buyingPrice) {
					cus.setWalletBalance(cus.getWalletBalance() - buyingPrice);

					prod.setQty(prod.getQty() - qty);

					products.put(id, prod);

					Transaction tr = new Transaction(cus.getUsername(), email, id,prod.getName(), qty, prod.getPrice(),
							prod.getPrice() * qty, LocalDate.now());

					transactions.add(tr);

				} else {
					throw new InvalidDetailsException("wallet balance is not sufficient");
				}

			} else {
				throw new InvalidDetailsException("product quantity is not suffiecient");
			}

		} else {
			throw new InvalidDetailsException("product not available with id: " + id);
		}

		return false;
	}

	@Override
	public boolean addMoneyToWallet(double amount, String email, Map<String, Student> customers) {
		// TODO Auto-generated method stub

		Student cus = customers.get(email);

		cus.setWalletBalance(cus.getWalletBalance() + amount);

		customers.put(email, cus);

		return true;
	}

	@Override
	public double viewWalletBalance(String email, Map<String, Student> customers) {
		// TODO Auto-generated method stub

		Student cus = customers.get(email);

		return cus.getWalletBalance();
	}

	@Override
	public Student viewCustomerDetails(String email, Map<String, Student> customers) {

		if (customers.containsKey(email)) {

			return customers.get(email);

		}

		return null;
	}

	@Override
	public List<Student> viewAllCustomers(Map<String, Student> customers) throws ProductException {
		// TODO Auto-generated method stub
		List<Student> list = null;

		if (customers != null && customers.size() > 0) {
			Collection<Student> coll = customers.values();
			list = new ArrayList<>(coll);
		} else {
			throw new ProductException("Student list is empty");
		}

		return list;
	}

}
