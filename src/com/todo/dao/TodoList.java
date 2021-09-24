package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		System.out.println("List sorted by name");
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		for (TodoItem myitem : list) {
			System.out.println("[" + myitem.getTitle() + "] " + myitem.getDesc() + " -Time: " + myitem.getCurrent_date());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		System.out.println("List sorted by time");
		Collections.sort(list, new TodoSortByDate());
	}
	
	public int getSize() {
		return list.size();
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
	
	public TodoItem getItem(int index) {
		int curr_pos = 1;
		TodoItem curr_item = null;
		for (TodoItem item : list) {
			if (curr_pos == index) {
			curr_item = item;
			break;
			}
			else {
				curr_pos++;
			}
		}
		return curr_item;
	}
	
	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
