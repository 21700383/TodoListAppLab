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
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Create Item\n"
				+ "Title: ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("Title can't be duplicate");
			return;
		}
		
		System.out.print("Description: ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.println("Delete Item\n"
				+ "Item to remove: ");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Edit Item Section\n"
				+ "Title of the item you want to update: ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("Title doesn't exist");
			return;
		}

		System.out.println("New title: ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Title can't be duplicate");
			return;
		}
		
		System.out.println("New description: ");
		String new_description = sc.next();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("Item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] " + item.getDesc() + " -Time: " + item.getCurrent_date());
		}
	}
	
	public static void loadList(TodoList l, String string){
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(string));
			
			String inputline;
			int i = 0;
			while((inputline = br.readLine()) != null) {
				System.out.println(inputline);
				StringTokenizer st = new StringTokenizer(inputline, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
			
				TodoItem t = new TodoItem(title, desc);
				t.setCurrent_date(current_date);
				l.addItem(t);
				i = i + 1;
			}
			
			
			br.close();
			System.out.println("Read " + i + " columns from the file\n");
			
		} catch (FileNotFoundException e) {
			System.out.println(string +" does not exist");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void saveList(TodoList l, String string) {
		// TODO Auto-generated method stub
		try {
			Writer w = new FileWriter(string);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println("All data has been saved");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
