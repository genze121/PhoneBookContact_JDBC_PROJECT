package com.jdbc.phonebook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.phonebook.Entity.PhoneContact;

public class PhoneContactDAO {

	private static Connection con;

	@SuppressWarnings("static-access")
	public PhoneContactDAO(Connection con) {
		super();
		this.con = con;
	}

	// Insert Query
	private static final String INSERT_SQL_QUERY = "insert into phonebook" + "(name,phone_no)" + "values(?,?)";

	// Update Query
	private static final String UPDATE_SQL_QUERY = "update phonebook set name=?,phone_no=? where id=?";

	// Delete Query
	private static final String DELETE_SQL_QUERY = "delete from phonebook where id=?";

	// Select Query
	private static final String SELECT_SQL_QUERY = "select * from phonebook";

	// Logic for Insertion of records into the database table
	public static boolean saveContact(PhoneContact contact) {

		boolean flag = false;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(INSERT_SQL_QUERY);
			ps.setString(1, contact.getName());
			ps.setString(2, contact.getPhone_no());

			int insertedRecord = ps.executeUpdate();

			if (insertedRecord == 1 || insertedRecord > 0) {
				flag = true;
			}

			System.out.println("The no of records are inserted:- " + insertedRecord);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Logic for Updating the records from the database table
	public static boolean editContact(PhoneContact contact) {

		boolean flag = false;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(UPDATE_SQL_QUERY);
			ps.setString(1, contact.getName());
			ps.setString(2, contact.getPhone_no());
			ps.setInt(3, contact.getId());

			int updatedRecord = ps.executeUpdate();

			if (updatedRecord == 1 || updatedRecord > 0) {
				flag = true;
			}

			System.out.println("The no of records updated:- " + updatedRecord);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Logic for deleting the records from the database table
	public static boolean deleteContact(int id) {

		boolean flag = false;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(DELETE_SQL_QUERY);
			ps.setInt(1, id);

			int deletedRecord = ps.executeUpdate();

			if (deletedRecord == 1 || deletedRecord > 0) {
				flag = true;
			}

			System.out.println("The no of records deleted:- " + deletedRecord);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Logic for Retrieval of records from the database table
	public static List<PhoneContact> getAllInfo() {

		List<PhoneContact> lists = new ArrayList<PhoneContact>();
		PhoneContact contacts = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SELECT_SQL_QUERY);
			rs = ps.executeQuery();
			while (rs.next()) {
				contacts = new PhoneContact();
				contacts.setId(rs.getInt(1));
				contacts.setName(rs.getString(2));
				contacts.setPhone_no(rs.getString(3));
				lists.add(contacts);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lists;
	}

}
