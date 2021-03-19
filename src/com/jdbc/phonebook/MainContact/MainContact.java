package com.jdbc.phonebook.MainContact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

import com.jdbc.phonebook.Connection.DBConnectionProvider;
import com.jdbc.phonebook.DAO.PhoneContactDAO;
import com.jdbc.phonebook.Entity.PhoneContact;

public class MainContact {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		Contact contacts = new Contact("Iron Man", "IM001");
		contacts.showMenu();
	}

}

class Contact {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;

	public static String USER;
	public static String USERID;

	@SuppressWarnings("static-access")
	public Contact(String uname, String uid) {
		super();
		this.USER = uname;
		this.USERID = uid;
	}

	@SuppressWarnings("static-access")
	public static void showMenu() {

		int options;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.println(
				"======================================================================================================");
		System.out.println("Welcome to the PhoneBook Application:- " + USER);
		System.out.println("Your User ID is:- " + USERID);

		if (USER.equals("Iron Man") && USERID.equals("IM001")) {
			System.out.println(
					"Username and Userid is correct.You have the authorize to access the PhoneBook Application!!");

		} else {
			System.out.println("You are not Authorized user to access the PhoneBook Application!!!");
		}
		System.out.println(
				"======================================================================================================");

		// Using the Connection
		PhoneContactDAO dao = new PhoneContactDAO(DBConnectionProvider.getConnection());
		System.out.println(
				"======================================================================================================");

		// Developing multiple choices for the user to select according to their choice
		System.out.println("1.CREATE CONTACT");
		System.out.println("2.EDIT CONTACT");
		System.out.println("3.DELETE CONTACT");
		System.out.println("4.VIEW CONTACT");
		System.out.println("5.EXIT FROM THE CONTACT");

		System.out.println(
				"======================================================================================================");

		do {
			System.out.println("You can choose the options according to your choice!!");
			options = scanner.nextInt();
			System.out.println(
					"======================================================================================================");

			switch (options) {

			case 1:
				System.out.println("Creating / Inserting the contacts.");
				PhoneContact contact1 = new PhoneContact();
				while (true) {
					System.out.println("Enter the Name:-");
					String name = scanner.next();
					System.out.println("Enter the Phone Number:-");
					String number = scanner.next();

					contact1.setName(name);
					contact1.setPhone_no(number);

					boolean insertTest = dao.saveContact(contact1);
					if (insertTest) {
						System.out.println("Records are created or inserted successfully!!!");
					} else {
						System.out.println("Something went wrong!!!");
					}
					System.out.println("Do you want to insert more records [Yes|No]");
					String choice = scanner.next();
					if (choice.equalsIgnoreCase("NO")) {
						break;
					}
				}
				System.out.println(
						"======================================================================================================");

				DBConnectionProvider.closeResources_1(ps, con);
				break;

			case 2:
				System.out.println("Editing the Contacts.");
				PhoneContact contact2 = new PhoneContact();
				System.out.println("Enter the ID:-");
				int id = scanner.nextInt();
				System.out.println("Enter the Name:-");
				String name = scanner.next();
				System.out.println("Enter the Phone Number:-");
				String number = scanner.next();

				contact2.setName(name);
				contact2.setId(id);
				contact2.setPhone_no(number);

				boolean editTest = dao.editContact(contact2);
				if (editTest) {
					System.out.println("Records are updated successfully!!!");
				} else {
					System.out.println("Something went wrong!!!");
				}
				System.out.println(
						"======================================================================================================");

				DBConnectionProvider.closeResources_1(ps, con);
				break;

			case 3:
				System.out.println("Deleting the Records.");
				while (true) {
					System.out.println("Enter the Id to delete the contact:-");
					int delId = scanner.nextInt();

					boolean deleteTest = dao.deleteContact(delId);
					if (deleteTest) {
						System.out.println("Records deleted successfully!!");
					} else {
						System.out.println("Something went wrong!!!");
					}

					System.out.println("Do you want to delete more records [Yes|No]");
					String choices = scanner.next();

					if (choices.equalsIgnoreCase("NO")) {
						break;
					}

				}
				System.out.println(
						"======================================================================================================");

				DBConnectionProvider.closeResources_1(ps, con);
				break;

			case 4:
				System.out.println("Viewing the records.");

				List<PhoneContact> lists = dao.getAllInfo();
				for (PhoneContact items : lists) {
					System.out.println("ID:- " + items.getId());
					System.out.println("NAME:- " + items.getName());
					System.out.println("PHONE NUMBER:- " + items.getPhone_no());
					System.out.println("----------------------------------------");
				}
				System.out.println(
						"======================================================================================================");

				DBConnectionProvider.closeResources_2(rs, ps, con);
				break;

			case 5:
				System.out.println("Exiting the PhoneBook Application!");
				break;

			default:
				System.out.println("Invalid choice selected.Please try again!!");

			}

		} while (options != 5);

	}
}
