package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class functionDriver {
    public static bookCompany company1 = new bookCompany("Jo's Book", "Downtown");
    public static bookStore store1 = new bookStore("Jo's Book", "Punggol", 1);
    public static bookStore store2 = new bookStore("Jo's Book", "Sengkang", 2);

    public static HashMap<Integer, bookStore> stores = new HashMap<>();

    public static Scanner input = new Scanner(System.in);

    public static final String LINE = "========================";

    public static void create(){

        book book1 = new book("A", 12.0);
        book book2 = new book("B", 48.0);
        book book3 = new book("C", 25.0);
        book book4 = new book("D", 24.0);

        bookStoreEmployee emp1 = new bookStoreEmployee("Jonas", "123456","Manager", 1, 1);
        bookStoreEmployee emp2 = new bookStoreEmployee("Jo", "123456","Staff", 1, 2);

        stores.put(store1.getStoreID(), store1);
        stores.put(store2.getStoreID(), store2);

        company1.addNewOutlet(store1);
        company1.addNewOutlet(store2);

        store1.addInventory(book1);
        store1.addInventory(book1);
        store1.addInventory(book2);
        store1.addInventory(book3);
        store1.addInventory(book4);
        store1.calculateStoreValue();

        store2.addInventory(book1);
        store2.addInventory(book2);
        store2.addInventory(book3);
        store2.addInventory(book4);
        store2.calculateStoreValue();

        company1.addCompanyValue();

        store1.addEmployee(emp1);
        store1.addEmployee(emp2);

    }

    public static void promptLogin(){
        boolean authenticate = false;
        int wrongCounter = 0;
        int outletId;
        String userName = "" , password = "";

        System.out.println(LINE + "Welcome to " + company1.getName() + LINE);
        System.out.println("Please login to access resources");

        do{
            System.out.print("\nOutlet Id: ");
            outletId = input.nextInt();

            System.out.print("Username: ");
            userName = input.next();

            System.out.print("Password: ");
            password = input.next();

            if(stores.containsKey(outletId)){
                HashSet<bookStoreEmployee> temp = stores.get(outletId).getEmployees();

                for(bookStoreEmployee bse : temp){
                    if(bse.getOutletId() == outletId){
                        if(bse.getname().equals(userName) && bse.getPassword().equals(password)){
                            displayBookStoreInfo(stores.get(outletId));
                            if(bse.getPosition().equals("Manager")){
                                displayManagerMenu(bse, stores.get(outletId));
                            }else{
                                displayStaffMenu(bse, stores.get(outletId));
                            }
                            authenticate = true;
                            break;
                        }
                    }
                }
            }else{
                System.out.println("Error. Outlet ID does not exist.");
            }

            wrongCounter++;

            if(!authenticate){
                System.out.println("Error. Username or password do not exist. Please try again.");

                if(wrongCounter == 3){
                    System.out.println("Error. User does not exist in this outlet.");
                }
            }

        }while(!authenticate);
    }

    public static void mainMenu(){
        promptLogin();
    }

    public static void displayBookStoreInfo(bookStore bs){
        System.out.println("\n" +LINE + "BookStore Information" + LINE);
        System.out.println("Name\t  Address StoreValue($)");
        System.out.println(bs);
    }

    public static void displayManagerMenu(bookStoreEmployee bse, bookStore bs){
        int option;

        do{
            System.out.println(LINE + "Manager Menu" + LINE);
            System.out.println(LINE+"Admin Options"+LINE);
            System.out.println("(1) Create new Employee");
            System.out.println("(2) Remove Employee");
            System.out.println("(3) View All Employees");
            System.out.println("(4) View Store Value");
            System.out.println(LINE+"Options"+LINE);
            System.out.println("(5) Add Inventory");
            System.out.println("(6) Minus Inventory");
            System.out.println("(7) Check Inventory");
            System.out.println("(8) View Inventory");
            System.out.println("(9) Log Out");

            option = input.nextInt();

            switch(option){
                case 1: createEmployee(bse, bs);
                    break;
                case 2: removeEmployee(bse, bs);
                    break;
                case 3: viewEmployee(bse, bs);
                    break;
                case 4: viewStoreValue(bse, bs);
                    break;
                case 5: addInventory(bse, bs);
                    break;
                case 6: minusInventory(bse, bs);
                    break;
                case 7:
                    checkInventory(bse, bs);
                    break;
                case 8: bs.viewInventory();
                    break;
            }
        }while(option != 9);

        System.out.println("Successfully Log out of Staff Menu");

        promptLogin();
        System.exit(0);
    }

    public static void displayStaffMenu(bookStoreEmployee bse, bookStore bs){
        int option;

        do{
            System.out.println(LINE + "Staff Menu" + LINE);
            System.out.println(LINE+"Options"+LINE);
            System.out.println("(1) Add Inventory");
            System.out.println("(2) Minus Inventory");
            System.out.println("(3) Check Inventory");
            System.out.println("(4) View Inventory");
            System.out.println("(5) Log Out");

            option = input.nextInt();

            switch(option){
                case 1: addInventory(bse, bs);
                    break;

                case 2: minusInventory(bse, bs);
                    break;

                case 3:
                    checkInventory(bse, bs);
                    break;

                case 4: bs.viewInventory();
                    break;
            }
        }while(option != 5);

        System.out.println("Successfully Log out of Staff Menu");

        promptLogin();
        System.exit(0);
    }

    public static void addInventory(bookStoreEmployee bse, bookStore bs){
        String title = "";
        double price = 0.0f;

        System.out.println("\n" + LINE+"Adding inventory"+LINE);

        System.out.println("Enter title of book: ");
        title = input.next();

        System.out.println("Enter price of book: ");
        price = input.nextInt();

        bse.addInventory(bs, title, price);
    }

    public static void minusInventory(bookStoreEmployee bse, bookStore bs){
        String title = "";

        System.out.println("\n" + LINE+"Minus inventory"+LINE);

        System.out.println("Enter title of book: ");
        title = input.next();

        bse.minusInventory(bs, title);
    }

    public static void checkInventory(bookStoreEmployee bse, bookStore bs){
        System.out.println("\n" + LINE+"Viewing inventory"+LINE);
        System.out.println("Enter title of book: ");
        bse.checkInventory(bs, input.next());
    }

    public static  void createEmployee(bookStoreEmployee bse, bookStore bs){
        String name, password, position;
        int outletID, id;
        System.out.println("\n" + LINE+"Creating New Employee" + LINE);
        System.out.println("Enter name: ");
        name = input.next();

        System.out.println("Enter password: ");
        password = input.next();

        System.out.println("Enter position: ");
        position = input.next();

        System.out.println("Enter outlet ID: ");
        outletID = input.nextInt();

        id = (int) (Math.random() * 100);

        bse.createEmployee(bs, name, password, position, outletID, id);
    }

    public static  void removeEmployee(bookStoreEmployee bse, bookStore bs){
        System.out.println("\n" + LINE+"Removing Employee" + LINE);
        System.out.println("Enter name: ");
        String name = input.next();

        bse.removeEmployee(bs, name);
    }

    public static  void viewEmployee(bookStoreEmployee bse, bookStore bs){
        System.out.println("\n" + LINE+"Viewing All Employees"+LINE);
        bse.viewEmployee(bs);
    }

    public static void viewStoreValue(bookStoreEmployee bse, bookStore bs){
        System.out.println("\n" + LINE+"Viewing Store " + bs.getStoreID() + "value"+LINE);
        bse.viewStoreValue(bs);
    }

    public void driver(){
        create();
        mainMenu();
    }
}
