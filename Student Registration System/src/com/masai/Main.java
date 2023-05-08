package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.masai.admin.Course;
import com.masai.admin.Student;
import com.masai.admin.Transaction;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;
import com.masai.service.CourseService;
import com.masai.service.CourseServiceImpl;
import com.masai.service.StudentService;
import com.masai.service.StudentServiceImpl;
import com.masai.service.TransactionService;
import com.masai.service.TransactionServiceImpl;
import com.masai.utility.FileExists;
import com.masai.utility.IDGeneration;

public class Main {

	// admin functionality
	private static void adminFunctionality(Scanner sc, Map<Integer, Course> courses, Map<String, Student> students,
			List<Transaction> transactions) throws InvalidDetailsException, ProductException, TransactionException {
		// admin login

		adminLogin(sc);

		CourseService prodService = new CourseServiceImpl();
		StudentService cusService = new StudentServiceImpl();
		TransactionService trnsactionService = new TransactionServiceImpl();
		int choice = 0;
		try {
			do {
				System.out.println("Press 1 Add the Courses");
				System.out.println("Press 2 view all the Courses");
				System.out.println("Press 3 delete the Course");
				System.out.println("Press 4 update the Course");
				System.out.println("Press 5 view all Student");
				System.out.println("Press 6 to view all transactions");
				System.out.println("Press 7 to log out");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					String added = adminAddCourses(sc, courses, prodService);
					System.out.println(added);
					break;
				case 2:

					adminViewAllCourses(courses, prodService);
					break;
				case 3:

					adminDeleteCourse(sc, courses, prodService);
					break;
				case 4:

					String upt = adminUpdateProduct(sc, courses, prodService);
					System.out.println(upt);
					break;
				case 5:
					adminViewAllStudents(students, cusService);

					break;
				case 6:
					adminViewAllTransactions(transactions, trnsactionService);
					break;
				case 7:
					System.out.println("admin has successfully logout");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + choice);
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void adminLogin(Scanner sc) throws InvalidDetailsException {

		System.out.println("Enter the user name");
		String userName = sc.next();
		System.out.println("Enter the password");
		String password = sc.next();
		if (userName.equals("admin") && password.equals("123")) {

			System.out.println("successfully login");
		} else {
			throw new InvalidDetailsException("Invalid Admin Credentials");
		}
	}

	public static String adminAddCourses(Scanner sc, Map<Integer, Course> courses, CourseService prodService) {

		String str = null;
		System.out.println("please enter the Course details");
		System.out.println("Enter the Course name");
		String name = sc.next();
		System.out.println("Enter the class strength");
		int qty = sc.nextInt();
		System.out.println("Enter the Course fees");
		double price = sc.nextDouble();
		System.out.println("Enter the Course category");
		String cate = sc.next();

		
		Course prod = new Course(IDGeneration.generateId(), name, qty, price, cate);

		str = prodService.addProduct(prod, courses);

		return str;

	}

	public static void adminViewAllCourses(Map<Integer, Course> courses, CourseService prodService)
			throws ProductException {
		prodService.viewAllProducts(courses);
	}

	public static void adminDeleteCourse(Scanner sc, Map<Integer, Course> courses, CourseService prodService)
			throws ProductException {

		System.out.println("please enter the id of Course to be deleted");
		int id = sc.nextInt();
		prodService.deleteProduct(id, courses);
	}

	public static String adminUpdateProduct(Scanner sc, Map<Integer, Course> courses, CourseService prodService)
			throws ProductException {
		String result = null;
		System.out.println("please enter the id of the product which is to be updated");
		int id = sc.nextInt();
		System.out.println("Enter the updated details ");

		System.out.println("Enter the Course name");
		String name = sc.next();

		System.out.println("Enter the  class strength");
		int qty = sc.nextInt();

		System.out.println("Enter the Course fees");
		double price = sc.nextDouble();

		System.out.println("Enter the Course category");
		String cate = sc.next();

		Course update = new Course(id, name, qty, price, cate);

		result = prodService.updateProduct(id, update, courses);
		return result;
	}

	public static void adminViewAllStudents(Map<String, Student> students, StudentService cusService)
			throws ProductException {
		List<Student> list = cusService.viewAllCustomers(students);

		for (Student c : list) {
			System.out.println(c);
		}
	}

	public static void adminViewAllTransactions(List<Transaction> transactions, TransactionService trnsactionService)
			throws TransactionException {
		List<Transaction> allTransactions = trnsactionService.viewAllTransactions(transactions);

		for (Transaction tr : allTransactions) {
			System.out.println(tr);
		}

	}

	// customer functionality
	public static void StudentFunctionality(Scanner sc, Map<String, Student> students,
			Map<Integer, Course> courses, List<Transaction> transactions)
			throws InvalidDetailsException, TransactionException {

		StudentService cusService = new StudentServiceImpl();
		CourseService prodService = new CourseServiceImpl();
		TransactionService trnsactionService = new TransactionServiceImpl();

		// Student login
		System.out.println("please enter the following details to login");
		System.out.println("please enter the email");
		String email = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		customerLogin(email,pass, students, cusService);

		try {
			int choice = 0;
			do {
				System.out.println("Select the option of your choice");
				System.out.println("Press 1 to view all courses");
				System.out.println("Press 2 to buy a Course");
				System.out.println("Press 3 to pay fees");
//				System.out.println("Press 4 view wallet balance");
				System.out.println("Press 4 view my details");
				System.out.println("Press 5 view my transactions");
				System.out.println("Press 6 to logout");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					customerViewAllProducts(courses, prodService);
					break;
				case 2:
					String result = customerBuyProduct(sc, email, courses, students, transactions, cusService);
					System.out.println(result);
					break;
				case 3:
					String moneyAdded = customerAddMoneyToWallet(sc, email, students, cusService);
					System.out.println(moneyAdded);
					break;
//				case 4:
//					double walletBalance = customerViewWalletBalance(email, students, cusService);
//					System.out.println("Wallet balance is: " + walletBalance);
//					break;
				case 4:
					customerViewMyDetails(email, students, cusService);
					break;
				case 5:
					customerViewCustomerTransactions(email, transactions, trnsactionService);
					break;
				case 6:
					System.out.println("you have successsfully logout");
					break;
				default:
					System.out.println("invalid choice");
					break;
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void StudentSignup(Scanner sc, Map<String, Student> students) throws DuplicateDataException {
		System.out.println("please enter the following details to Signup");
		System.out.println("please enter the user name");
		String name = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		System.out.println("enter the address");
		String address = sc.next();
		System.out.println("Enter the email id");
		String email = sc.next();
		System.out.println("Enter the balance to be added into the wallet");
		double balance = sc.nextDouble();
		Student cus = new Student(balance, name, pass, address, email);

		StudentService cusService = new StudentServiceImpl();
		cusService.signUp(cus, students);
		System.out.println("customer has Succefully sign up");

	}

	public static void customerLogin(String email,String pass, Map<String, Student> students, StudentService cusService)
			throws InvalidDetailsException {
		cusService.login(email, pass,students);
		System.out.println("Student has successfully login");

	}

	public static void customerViewAllProducts(Map<Integer, Course> courses, CourseService prodService)
			throws ProductException {
		prodService.viewAllProducts(courses);
	}

	public static String customerBuyProduct(Scanner sc, String email, Map<Integer, Course> courses,
			Map<String, Student> students, List<Transaction> transactions, StudentService cusService)
			throws InvalidDetailsException, ProductException {
		System.out.println("Enter the product id");
		int id = sc.nextInt();
		System.out.println("enter the quantity you want to buy");
		int qty = sc.nextInt();
		cusService.buyProduct(id, qty, email, courses, students, transactions);

		return "You have successfully bought the product";

	}

	public static String customerAddMoneyToWallet(Scanner sc, String email, Map<String, Student> students,
			StudentService cusService) {
		System.out.println("please enter the amount");
		double money = sc.nextDouble();
		boolean added = cusService.addMoneyToWallet(money, email, students);

		return "Amount: " + money + " successfully added to your wallet";
	}

//	public static double customerViewWalletBalance(String email, Map<String, Student> students,
//			StudentService cusService) {
//		double walletBalance = cusService.viewWalletBalance(email, students);
//		return walletBalance;
//	}

	public static void customerViewMyDetails(String email, Map<String, Student> students,
			StudentService cusService) {
		Student cus = cusService.viewCustomerDetails(email, students);
		System.out.println("name : " + cus.getUsername());
		System.out.println("address : " + cus.getAddress());
		System.out.println("email : " + cus.getEmail());
		System.out.println("wallet balance : " + cus.getWalletBalance());
	}

	public static void customerViewCustomerTransactions(String email, List<Transaction> transactions,
			TransactionService trnsactionService) throws TransactionException {
		List<Transaction> myTransactions = trnsactionService.viewCustomerTransactions(email, transactions);

		for (Transaction tr : myTransactions) {
			System.out.println(tr);
		}
	}

	
	
	public static void main(String[] args) {
//file check
		Map<Integer, Course> courses = FileExists.productFile();
		Map<String, Student> students = FileExists.customerFile();
		List<Transaction> transactions = FileExists.transactionFile();
//		System.out.println(courses.size());
//		System.out.println(students.size());
//		System.out.println(transactions.size());

		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome , in Course Management System");

		try {

			int preference = 0;
			do {
				System.out.println("Please enter your preference, " + " '1' --> Admin login , '2' --> Student login , "
				+ "'3' for Student signup, '0' for exit");
				preference = sc.nextInt();
				switch (preference) {
				case 1:
					adminFunctionality(sc, courses, students, transactions);
					break;
				case 2:
					StudentFunctionality(sc, students, courses, transactions);
					break;

				case 3:
					StudentSignup(sc, students);
					break;

				case 0:
					System.out.println("successfully existed from the system");

					break;

				default:
					throw new IllegalArgumentException("Invalid Selection");
				}

			}

			while (preference != 0);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			// serialization (finally always executed)
			try {
				ObjectOutputStream poos = new ObjectOutputStream(new FileOutputStream("Course.ser"));
				poos.writeObject(courses);
				ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Student.ser"));
				coos.writeObject(students);

				ObjectOutputStream toos = new ObjectOutputStream(new FileOutputStream("Transactions.ser"));
				toos.writeObject(transactions);
			//	System.out.println("serialized..........");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}

	}

}
