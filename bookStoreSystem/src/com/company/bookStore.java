package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class bookStore {

    private String name;
    private String address;
    private int storeID;
    private double storeValue;
    private HashMap<book, Integer> inventory;
    private HashSet<bookStoreEmployee> employees;

    public static final String LINE = "========================";

    public bookStore(String name, String address, int storeID){
        this.name = name;
        this.address = address;
        this.storeID = storeID;
        storeValue = 0.0f;
        inventory = new HashMap<book, Integer>();
        employees = new HashSet<bookStoreEmployee>();
    }

    public void checkInventory(String bookName){
        boolean found = false;

        for(book b : inventory.keySet()){
            if(b.getName().equals(bookName)){
                System.out.println(LINE + LINE);
                System.out.println("Title\tPrice\tStock");
                System.out.println(bookName + "\t\t" + b.getPrice() + "\t" + inventory.get(b));
                System.out.println(LINE + LINE);
                found = true;
            }
        }

        if(!found){
            System.out.println("Book, " + bookName + " does not exist");
        }
    }

    public void addInventory(book newBook){
        if(inventory.containsKey(newBook)){
            inventory.replace(newBook, inventory.get(newBook) + 1);
        }
        else{
            inventory.put(newBook, 1);
        }
    }

    public void minusInventory(String title){
        boolean found = false;

        for(book b : inventory.keySet()){
            if(b.getName().equals(title)){
                if(inventory.get(b) > 1){
                    inventory.replace(b, inventory.get(b) - 1);
                }else{
                    inventory.remove(b);
                }

                found = true;
            }
        }

        if(!found){
            System.out.println("Book, " + title + " does not exist");
        }
    }

    public void viewInventory(){
        System.out.println(LINE + name + " " + storeID + " inventory" + LINE);
        System.out.println("Name\tPrice\t\tQuantity");
        for(Map.Entry<book, Integer> hm : inventory.entrySet()){
            System.out.println(hm.getKey() + "\t\t" +hm.getValue());
        }
    }

    public void calculateStoreValue(){
        for(book b : inventory.keySet()){
            storeValue += b.getPrice();
        }
    }

    public double getStoreValue(){
        return storeValue;
    }

    public int getStoreID(){
        return storeID;
    }

    public void addEmployee(bookStoreEmployee employee){
        employees.add(employee);
    }

    public void removeEmployee(String name){
        employees.removeIf(bse -> bse.getname().equals(name));
    }

    public void viewEmployee(){
        System.out.println(LINE + name + " " + storeID + " employees"+ LINE);
        System.out.println("Name\t\tPosition\tOutletId\tID");
        for(bookStoreEmployee bse : employees){
            System.out.println(bse);
        }
    }

    public HashSet<bookStoreEmployee> getEmployees(){
        return employees;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(" ").append(address).append(" ").append(storeValue).append("\n");

        return builder.toString();
    }


}
