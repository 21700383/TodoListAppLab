package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Create Item\n"
				+ "Title: ");
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("Title can't be duplicate");
			return;
		}
		
		System.out.print("Category: ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("Description: ");
		desc = sc.nextLine().trim();
		
		System.out.print("Due_date(yyyy/mm/dd): ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.println("Item added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Delete Item\n"
				+ "Enter item number to remove: ");
		int item_num = sc.nextInt();
		
		TodoItem item = l.getItem(item_num);
		list(item_num, item);
		
		System.out.print("Do you wish to delete this item? (y/n) > ");
		char yes_no = (char) sc.next().charAt(0);
		if (yes_no == 'y') {
			l.deleteItem(item);
			System.out.println("Item deleted");
		}
		else {
			System.out.println("Item not deleted");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Edit Item\n"
				+ "Enter item number to edit: ");
		int item_num = sc.nextInt();
		

		TodoItem item = l.getItem(item_num);
		list(item_num, item);
		
		System.out.print("New title: ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("Title can't be duplicate");
			return;
		}
		
		System.out.print("New category: ");
		String new_category = sc.next();
		sc.nextLine();
		
		System.out.print("New description: ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("New due date(yyyy/mm/dd): ");
		String new_due_date = sc.next();
		l.deleteItem(item);
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		l.addItem(t);
		System.out.println("Item updated");
		
	}
	
	public static void find(String keyword, TodoList l) {
		int count = 0;
		for (TodoItem item: l.getList()) {
			String title = item.getTitle();
			String desc = item.getDesc();
			if(title.contains(keyword) == true || desc.contains(keyword) == true) {
				int current_num = l.indexOf(item) + 1;
				list(current_num, item);
				count++;
			}
		}
		System.out.println("Found total of " + count + " items matching");
	}
	
	public static void find_cate(String keyword, TodoList l) {
		int count = 0;
		for (TodoItem item: l.getList()) {
			String category = item.getCategory();
			if(category.contains(keyword) == true) {
				int current_num = l.indexOf(item) + 1;
				list(current_num, item);
				count++;
			}
		}
		System.out.println("Found total of " + count + " items matching");
	}
	
	public static void listAll(TodoList l) {
		int total_num = l.getSize();
		System.out.println("<Total List, total " + total_num + ">");
		for (TodoItem item : l.getList()) {
			int current_num = l.indexOf(item) + 1;
			list(current_num, item);
		}
	}
	
	public static void list(int current_num, TodoItem item) {
		System.out.println(current_num + ". [" + item.getCategory() + "] " + item.getTitle() + " - " 
				+ item.getDesc() + " -" + item.getDue_date() + " -Time written: " + item.getCurrent_date());
	}
	
	public static void listCate(TodoList l) {
		HashSet<String> set = new HashSet();
		for (TodoItem item : l.getList()) {
			String category = item.getCategory();
			set.add(category);
		}
		
		Iterator iter = set.iterator();
		int count = 0;
		while(iter.hasNext()) {
		    System.out.print(iter.next());
		    if (iter.hasNext() == true) {
		    	System.out.print(" / ");
		    }
		    count++;
		}
		
		System.out.println("\nFound total of " + count + " categories");
	}
	
	public static void loadList(TodoList l, String string) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(string));
			
			String inputline;
			int count = 0;
			while((inputline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(inputline, "##");
				String title = st.nextToken();
				String category = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
			
				TodoItem t = new TodoItem(title, category, desc, due_date);
				t.setCurrent_date(current_date);
				l.addItem(t);
				count = count + 1;
			}
			
			
			br.close();
			System.out.println("Read " + count + " items from the file\n");
			
		} catch (FileNotFoundException e) {
			System.out.println(string +" does not exist");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void saveList(TodoList l, String string) {
		try {
			Writer w = new FileWriter(string);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println("All data has been saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
