package com.company;

import java.util.HashSet;

class bookCompany {

    private String name;
    private String address;
    private double companyValue;
    private HashSet<bookStore> outlets;

    public bookCompany(String name, String address){
        this.name = name;
        this.address = address;
        companyValue = 0.0f;
        outlets = new HashSet<bookStore>();
    }

    public void addNewOutlet(bookStore newStore){
        outlets.add(newStore);
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public HashSet<bookStore> getOutlets(){
        return outlets;
    }

    public int getNumberOfOutlets(){
        return outlets.size();
    }

    private double getCompanyValue(){
        return companyValue;
    }

    public void addCompanyValue(){
        for(bookStore bs : outlets){
            companyValue += bs.getStoreValue();
        }
    }

    public void viewStoreInventory(int storeId){
        for(bookStore bs : outlets){
            if(bs.getStoreID() == storeId){
                bs.viewInventory();
            }
        }
    }

    public void viewAllInventory(){
        for(bookStore bs : outlets){
            bs.viewInventory();
        }
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(" ").append(address).append("\n");
        builder.append("Number of outlets: " + getNumberOfOutlets()).append("\n");
        builder.append("Total Company net worth: $" + getCompanyValue()).append("\n");

        for(bookStore bs : outlets){
            builder.append(bs);
        }

        return builder.toString();
    }

}
