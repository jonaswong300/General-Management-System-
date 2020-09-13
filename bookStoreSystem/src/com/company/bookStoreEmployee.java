package com.company;

class bookStoreEmployee{

    private String name;
    private String password;
    private String position;
    private int outletId;
    private int id;

    public bookStoreEmployee(String name, String password, String position, int outletId, int id){
        this.name = name;
        this.password = password;
        this.position = position;
        this.id = id;
        this.outletId = outletId;
    }

    public void addInventory(bookStore bs, String title, double price){
        bs.addInventory(new book(title, price));
        bs.viewInventory();
    }

    public void minusInventory(bookStore bs, String title){
        bs.minusInventory(title);
        bs.viewInventory();
    }

    public void checkInventory(bookStore bs, String title){
        bs.checkInventory(title);
    }

    public void createEmployee(bookStore bs, String name, String password, String position, int outletId, int id){
        bs.addEmployee(new bookStoreEmployee(name, password, position, outletId, id));
        bs.viewEmployee();
    }

    public void removeEmployee(bookStore bs, String name){
        bs.removeEmployee(name);
        bs.viewEmployee();
    }

    public void viewEmployee(bookStore bs){
        bs.viewEmployee();
    }

    public void viewStoreValue(bookStore bs){
        System.out.println("Store value : " + bs.getStoreValue());
        bs.viewInventory();
    }

    public int getOutletId(){
        return outletId;
    }

    public String getname(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getPosition(){
        return position;
    }

    @Override
    public String toString(){
        return name + "\t\t" + position + "\t\t" + outletId + "\t\t" + id;
    }

}
