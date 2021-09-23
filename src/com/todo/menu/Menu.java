package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
    	System.out.println("<TodoList command menu>");
        System.out.println("1. add -> Add new item");
        System.out.println("2. del -> Delete an item ");
        System.out.println("3. edit -> Update an item ");
        System.out.println("4. ls -> List all items");
        System.out.println("5. ls_name_asc -> show list by ascending order of title");
        System.out.println("6. ls_name_desc -> show list by decsending order of title");
        System.out.println("7. ls_date -> show list by date");
        System.out.println("8. exit -> exit program");
    }
    
	public static void prompt() 
	{
		System.out.print("\nEnter your command > ");
	}
}
