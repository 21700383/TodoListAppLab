package com.todo.main;

import java.util.Scanner;

import com.todo.menu.Menu;
import com.todo.service.TodoUtil;
import com.todo.dao.TodoList;

public class TodoMain {
	
	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
			
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
			
			case "find":
				String keyword = sc.next();
				TodoUtil.find(keyword, l);
				break;
			
			case "find_cate":
				String cate = sc.next();
				TodoUtil.find_cate(cate, l);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				break;
				
			default:
				System.out.println("please enter one of the above mentioned command");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
