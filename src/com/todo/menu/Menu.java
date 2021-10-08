package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
    	System.out.println("<TodoList command menu>");
        System.out.println("1. add -> Add new item");
        System.out.println("2. del -> Delete an item ");
        System.out.println("3. edit -> Update an item ");
        System.out.println("4. ls -> List all items");
        System.out.println("5. ls_name -> show list by ascending order of title");
        System.out.println("6. ls_name_desc -> show list by decsending order of title");
        System.out.println("7. ls_date -> show list by ascending order of date");
        System.out.println("8. ls_date_desc -> show list by decsending order of date");
        System.out.println("9. ls_cate -> show all categories from the items");
        System.out.println("10. find -> find an item containing this word from title or description");
        System.out.println("11. find_cate -> find an item containing this word from category");
        System.out.println("12. comp -> marks a task completed");
        System.out.println("13. exit -> exit program");
    }
    
	public static void prompt() 
	{
		System.out.print("\nEnter your command > ");
	}
}
