package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;
	
	public TodoList() {
		this.conn =DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, category, desc, due_date, current_date)"
				+ " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getCategory());
			pstmt.setString(3,t.getDesc());
			pstmt.setString(4,t.getDue_date());
			pstmt.setString(5,t.getCurrent_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, category=?, desc=?, due_date=?, current_date=?"
				+" where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getCategory());
			pstmt.setString(3,t.getDesc());
			pstmt.setString(4,t.getDue_date());
			pstmt.setString(5,t.getCurrent_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() { // to return all data in list without keyword
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list = makeList(list, rs);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or desc like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list = makeList(list, rs);
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String cate) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list where category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cate);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list = makeList(list, rs);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list = makeList(list, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<TodoItem> makeList(ArrayList<TodoItem> list, ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String title = rs.getString("title");
		String category = rs.getString("category");
		String desc = rs.getString("desc"); // description
		String due_date = rs.getString("due_date");
		String current_date = rs.getString("current_date");
		int is_completed = rs.getInt("is_completed");
		TodoItem t = new TodoItem(title, category, desc, due_date, is_completed);
		t.setId(id);
		t.setCurrent_date(current_date);
		list.add(t);
		return list;
	} // makeList made as few methods use duplicate lines to make the list
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
			try {
				stmt = conn.createStatement();
				String sql = "SELECT DISTINCT category FROM list";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					String category = rs.getString("category");
					list.add(category);
				}
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return list;
	}
	
	public int completeItem(int id) {
		String sql = "update list set is_completed=1"
				+" where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Boolean isDuplicate(String title) {
		PreparedStatement pstmt;
		String sql = "select count(*) from list where title like ?;";
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("count(*)");
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count >= 1) return true;
		return false;
	}

	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, category, desc, due_date, current_date, is_completed)"
					+ " values (?,?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String category = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				String is_completed = st.nextToken();
			
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,title);
				pstmt.setString(2,category);
				pstmt.setString(3,desc);
				pstmt.setString(4,due_date);
				pstmt.setString(5,current_date);
				pstmt.setString(6,is_completed);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read !!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
