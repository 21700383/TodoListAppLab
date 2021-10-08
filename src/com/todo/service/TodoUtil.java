package com.todo.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, category, desc, due_date;
		int is_completed;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Create Item\n"
				+ "Title: ");
		
		title = sc.nextLine().trim();
		if (l.isDuplicate(title)) {
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
		
		is_completed = 0;
		TodoItem t = new TodoItem(title, category, desc, due_date, is_completed);
		if(l.addItem(t) > 0) System.out.println("Item added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Delete Item\n"
				+ "Enter item number to remove: ");
		int index = sc.nextInt();
		if (l.deleteItem(index)>0) 
			System.out.println("Item deleted");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String new_title, new_desc, new_category, new_due_date;
		int is_completed;
		System.out.print("Edit Item\n"
				+ "Enter item number to edit: ");
		int index = sc.nextInt();
		
		System.out.print("New title: ");
		new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.printf("Title can't be duplicate");
			return;
		}
		
		System.out.print("New category: ");
		new_category = sc.next();
		sc.nextLine();
		
		System.out.print("New description: ");
		new_desc = sc.nextLine().trim();
		
		System.out.print("New due date(yyyy/mm/dd): ");
		new_due_date = sc.nextLine().trim();
		is_completed = 0; // assume editing task sets completed status to 0, as it's different now
		TodoItem t = new TodoItem(new_title, new_category, new_desc, new_due_date, is_completed);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("Item updated");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item: l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("Found total of " + count + " items matching");
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item: l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("Found total of " + count + " items matching");
	}
	
	public static void listAll(TodoList l) {
		System.out.println("<Total List, total " + l.getCount() + ">");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("<Total List, toal %d>\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)){
			System.out.println(item.toString());
		}
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		
		System.out.println("\nFound total of " + count + " categories");
	}
	
	public static void completeItem(TodoList l, int id) {
		if(l.completeItem(id) > 0) {
			System.out.println("Task finish checked");
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
