/*======================================================================
EVENT BOOKING MANAGEMENT SYSTEM

======================================================================*/
package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.Random;

class event
{
    public String eventName;
    private String eventDate;
    private String eventTime;
    private int capacity;
    private int price;
    private String promocode;

    event()
    {

    }
    event(String eventName, String eventDate, String eventTime, int capacity, int price, String promocode)
    {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.capacity = capacity;
        this.price = price;
        this.promocode = promocode;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice()
    {
        return price;
    }

    public String getPromocode()
    {
        return promocode;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    @Override
    public String toString() {
        String words = "Name of Event: " + eventName + "\n";
        words += "Date of Event: " + eventDate + "\n";
        words += "Time of Event: " + eventTime + "\n";
        words += "Capacity: " + capacity + "\n";
        words += "Price: " + price + "\n";
        words += "Promocode: " + promocode;
        words += "\n\n";

        return String.format(words);

    }
}

class eventDatabase
{

    public ArrayList<event> eventList = new ArrayList<event>();

    public void addEvent(event e)
    {
        eventList.add(e);
    }

    public void displayEvent()
    {
        System.out.println("\n===============Events===============");
        for(event e: eventList)
            System.out.println(e.toString());
        System.out.println("=====================================");
    }

}

class bookingDatabase
{
    ArrayList<book> bookingList = new ArrayList<book>();

    bookingDatabase()
    {

    }

    public void addBooking(book b)
    {
        bookingList.add(b);
    }

    public void displayBooking()
    {
        System.out.println("================BOOKING================");
        for(book b : bookingList)
            System.out.println(b + "\n");
        System.out.println("=======================================");
    }

}

class book
{
    public String eventName;
    public String userWhoBook;

    public int quantity;
    public int price;
    public int bookingID;

    book()
    {

    }

    public book(String eventName,String userWhoBook, int quantity, int price, int bookingID) {
        this.eventName = eventName;
        this.userWhoBook = userWhoBook;
        this.quantity = quantity;
        this.price = price;
        this.bookingID = bookingID;
    }

    @Override
    public String toString()
    {
        String word = "";

        word += "Booking reference: " + bookingID + "\n";
        word += "Event name: " + eventName + "\n";
        word += "Booking name: " + userWhoBook + "\n";
        word += "Quantity book: " + quantity + " tickets.";

        return String.format(word);
    }
}

class user
{
    private static Scanner input = new Scanner(System.in);

    public String username;
    public String password;

    public String firstName;
    public String lastName;

    public int ID_general = 100000;
    public int running_counter = 0;
    public int studentID;

    public ArrayList<LocalDateTime> loginTime = new ArrayList<>();
    public ArrayList<LocalDateTime> logOutTime = new ArrayList<>();

    private int option;

    user()
    {

    }

    user(String username, String password, String firstName, String lastName)
    {
        this.studentID = ID_general + (++running_counter);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFN()
    {
        return firstName;
    }

    public String getLN()
    {
        return lastName;
    }

    public void setfirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setlastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setUserPassword(String password)
    {
        this.password = password;
    }

    public int displayUserMenu()
    {
        System.out.println("\n==========================================");
        System.out.println("Welcome to User Menu");
        System.out.println("==========================================");

        System.out.println("(1) Display Event");
        System.out.println("(2) Book Event");
        System.out.println("(3) Change account password");
        System.out.println("(4) View Booking");
        System.out.println("(5) Manage Booking");
        System.out.println("(6) Exit\n");

        System.out.println("(0) Log Out");

        boolean optionCheck = false;

        do {
            try{
                System.out.println("Enter an option: ");
                option = input.nextInt();
                optionCheck = true;
            }
            catch (InputMismatchException e)
            {
                input.nextLine();
                System.out.println("Error. Enter option in integer only.\n");
            }
        }
        while(!optionCheck);


        return option;
    }

    public void displayEventForUser(eventDatabase eventDB)
    {
        eventDB.displayEvent();
    }

    public void bookEvent(eventDatabase eventDB, bookingDatabase bookDB)
    {
        Integer quantity = null;

        boolean eventFound = false;

        System.out.println("==========================================");
        System.out.println("Booking an Event");
        System.out.println("Enter the following information");
        System.out.println("==========================================");
        input.nextLine();

        System.out.print("Name of Event you would like to Book: ");
        String eventName = input.nextLine();

        for(event e : eventDB.eventList)
        {
            if(e.eventName.equals(eventName))
            {
                eventFound = true;
                System.out.println("\nThe tickets remaining for " + e.eventName);
                System.out.println(e.getCapacity());

                boolean quantityCheck = false;

                do {
                    try{
                        System.out.print("\nEnter number of tickets you would like to purchase:");
                        quantity = input.nextInt();
                        input.nextLine();
                        if(quantity <= 0)
                            System.out.println("Error, please enter a positive integer!");
                        else
                            quantityCheck = true;
                    }
                    catch (InputMismatchException a)
                    {
                        input.nextLine();
                        System.out.println("Error. Enter option in integer only.");
                    }
                }
                while(!quantityCheck);

                if(quantity <= e.getCapacity())
                {
                    int remainingCapacity = e.getCapacity() - quantity;

                    e.setCapacity(remainingCapacity);

                    makePayment(eventName, quantity, e.getPrice(), e.getPromocode(), bookDB);

                }
                else
                {
                    System.out.println("Insufficient tickets.\nPlease book a quantity within the " +
                            "current number of seats available.");
                }
            }
        }

        if(!eventFound)
        {
            System.out.println("Please enter a valid event name. Thank you");
        }
    }

    public void makePayment(String eventName, int quantity, int price, String promocode, bookingDatabase bookDB)
    {
        System.out.println("\n\n============================================");
        System.out.println("PAYMENT MODE");
        System.out.println("============================================");
        double amount = quantity * price;


        boolean check = false;
        boolean promoCheck = false;
        int counter = 0;

        do {
            System.out.println("Do you have a promocode (y/n) ?");
            String answer = input.nextLine();

            if(answer.equals("y") || answer.equals("Y") || answer.equals("Yes") || answer.equals("yes"))
            {
                check = true;
                do{
                    System.out.println("Please enter promocode: ");
                    String code = input.nextLine();
                    if(code.equals(promocode))
                    {
                        amount = amount * 0.9;
                        System.out.println("You have received a 10% discount off the total bill");
                        System.out.printf("Your current total is %.2f", amount);
                        promoCheck = true;
                    }
                    else
                    {
                        System.out.println("Please enter a valid promocode.\n");
                        counter++;

                        if(counter == 3)
                        {
                            System.out.println("Error. Bot detected. No promo code allowed. \n");
                            System.out.printf("Your current total is %.2f", amount);
                            break;
                        }
                    }
                }while(!promoCheck);
            }
            else if(answer.equals("n") || answer.equals("N") || answer.equals("no") || answer.equals("No"))
            {
                check = true;
                System.out.println("Total Amount: $" + amount);
            }
            else
            {
                System.out.println("Error: Invalid input. Please enter either (y/n).");
            }
        }while(!check);


        creditCardDetails(eventName, quantity, price, bookDB);

    }

    public void creditCardDetails(String eventName, int quantity, int price, bookingDatabase bookDB)
    {

        long cardNumber;
        String cvv;
        String name, cardExpiry;

        System.out.println("\n============================================");
        System.out.println("Credit Card Information");
        System.out.println("============================================\n");

        System.out.println("********************************************");
        System.out.println("Please enter your credit card details: ");
        System.out.println("********************************************");

        //Validation for Card Number
        boolean cardNumberCheck = false;
        do {
            System.out.println("Enter your 16 digit CardNumber: ");
            if(input.hasNextLong())
            {
                cardNumber = input.nextLong();

                int num = countDigit(cardNumber);

                if(num == 16)
                    cardNumberCheck = true;
                else
                    System.out.println("Error. Enter 16 digits for card number\n");
            }
            else
            {
                input.nextLine();
                System.out.println("Error. Please enter only integer for card number.\n");
            }

        }while(!cardNumberCheck);

        input.nextLine();//Clear Buffer

        //Validation for Name
        boolean nameCheck = false;
        do {
            System.out.println("\nEnter your Name on card: ");

            try
            {
                name = input.nextLine();

                if(name.equals(""))
                    nameCheck = true;

                for(int k = 0; k < name.length(); k++)
                {
                    if (Character.isDigit(name.charAt(k)))
                    {
                        nameCheck = true;
                    }
                    else
                        nameCheck = false;
                }
                if(nameCheck == true)
                    System.out.println("Error. Enter only characters for your name.\n");
            }
            catch (NumberFormatException e)
            {
                nameCheck = true;
            }
        }while(nameCheck);


        //Validation for Expiry Date
        boolean dateCheck = false;
        do {
            System.out.println("\nEnter your Card Expiry Date(mm/yy): ");
            cardExpiry = input.nextLine();

            if(validateCardExpiryDate(cardExpiry))
                dateCheck = true;
            else
                System.out.println("Error. Enter your expiry in this format (mm/yy).\n");

        }while(!dateCheck);


        //Validation for CVV number
        boolean cvvCheck = false;

        do {
            System.out.println("\nEnter your CVV Number: ");
            if(input.hasNextInt())
            {
                cvv = input.nextLine();


                int num = countCVV(cvv);

                if(num == 3)
                    cvvCheck = true;
                else
                    System.out.println("Error. Please enter 3 digits for CVV number.\n");
            }
            else
            {
                input.nextLine();
                System.out.println("Error. Please enter integer values only for CVV number.\n");
            }

        }while(!cvvCheck);

        //processLoading();
        displayPaymentSuccessful(eventName, quantity, price, bookDB);
    }

    private boolean validateCardExpiryDate(String expiryDate)
    {
        return expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    public void displayPaymentSuccessful(String eventName, int quantity, int price, bookingDatabase bookDB)
    {
        Random rand = new Random();
        int bookingID = rand.nextInt(9999) + rand.nextInt(1234);
        String name = getFN() + " " + getLN();

        System.out.println("========================================================");
        System.out.println("Payment Successful");
        System.out.println("========================================================");
        System.out.println("Purchase details:");
        System.out.printf("Booking ID:%12d\n", bookingID);
        System.out.printf("Event Name:%13s\n", eventName);
        System.out.printf("Name:%24s\n", name);
        System.out.printf("Tickets book:%8d\n", quantity);
        System.out.println("Thank you for purchasing. Hope you have a great event :)");
        System.out.println("========================================================");

        String userWhoBook = this.firstName + " " + this.lastName;
        book b = new book(eventName,userWhoBook, quantity, price, bookingID);
        bookDB.addBooking(b);
    }

    public int countDigit(long n)
    {
        int count = 0;
        while(n != 0)
        {
            n = n / 10;
            count++;
        }
        return count;
    }

    public int countCVV(String n)
    {
        return n.length();
    }

    public void processLoading()
    {
        int timeToProcess = 3;
        System.out.print("\nConnecting to Server");
        try {
            for (int i=0; i<timeToProcess ; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        }

        int timeToWait = 10;
        System.out.print("\nProcessing Transaction");
        try {
            for (int i=0; i<timeToWait ; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        }
    }

    public void manageBooking(eventDatabase eventDB, bookingDatabase bookDB)
    {
        Integer choice = null;

        System.out.println("==========================================");
        System.out.println("Managing an Event");
        System.out.println("Enter the following information");
        System.out.println("==========================================");

        System.out.println("(1) Purchase additional tickets");
        System.out.println("(2) Cancel Booking");

        boolean optionCheck = false;

        do {
            try{
                System.out.println("Enter an option: ");
                choice = input.nextInt();
                optionCheck = true;
            }
            catch (InputMismatchException e)
            {
                input.nextLine();
                System.out.println("Error. Enter option in integer only.\n");
            }
        }
        while(!optionCheck);

        if(choice == 1)
            bookEvent(eventDB, bookDB);
        else if(choice == 2)
            cancelBooking(eventDB, bookDB);
    }

    public void cancelBooking(eventDatabase eventDB, bookingDatabase bookDB)
    {
        Integer numberOfTicketsToReduce;
        String nameOfEventToBeCancel;
        Integer referenceNumber = null;

        input.nextLine();

        boolean eventBookMatch = false;

        do {
            System.out.println("Enter name of event you would like to cancel? ");
            nameOfEventToBeCancel = input.nextLine();

            for(book b : bookDB.bookingList)
            {
                if(b.eventName.equals(nameOfEventToBeCancel))
                    eventBookMatch = true;
            }

            if(!eventBookMatch)
                System.out.println("Error. Please enter an event that you have booked to make changes.");

        }while(!eventBookMatch);

        boolean referenceMatch = false;

        do {
            try {
                System.out.println("Enter your booking reference: ");
                referenceNumber = input.nextInt();

                for (int i = 0; i < bookDB.bookingList.size(); i++) {
                    if (bookDB.bookingList.get(i).bookingID == referenceNumber &&
                            bookDB.bookingList.get(i).eventName.equals(nameOfEventToBeCancel)) {
                        referenceMatch = true;
                        System.out.println("\nYour booking record has been found.\n");
                        System.out.println("===========================================");
                        System.out.println(bookDB.bookingList.get(i));
                        System.out.println("===========================================\n");

                        boolean quantityMatch = false;

                        do {
                            try {
                                System.out.println("How many tickets would you like to cancel? ");
                                numberOfTicketsToReduce = input.nextInt();


                                if (numberOfTicketsToReduce <= bookDB.bookingList.get(i).quantity && numberOfTicketsToReduce > 0) {
                                    quantityMatch = true;


                                    if (bookDB.bookingList.get(i).quantity == numberOfTicketsToReduce) {
                                        bookDB.bookingList.remove(i);
                                        System.out.println("\nYou have canceled attending the event completely.");
                                        System.out.println("Hope to see you at the event in the near future.\n");
                                    } else {
                                        bookDB.bookingList.get(i).quantity -= numberOfTicketsToReduce;
                                        System.out.println("You have canceled " + numberOfTicketsToReduce);
                                        System.out.println("You currently have " + bookDB.bookingList.get(i).quantity + " tickets left.");
                                    }

                                    for (event e : eventDB.eventList) {
                                        if (e.eventName.equals(nameOfEventToBeCancel))
                                            e.setCapacity(e.getCapacity() + numberOfTicketsToReduce);
                                    }

                                } else {
                                    System.out.println("Error. Please enter a quantity within what you have booked.\n");
                                }
                            }catch(InputMismatchException e)
                            {
                                System.out.println("Error. Please enter an integer only!");
                            }
                        } while (!quantityMatch);
                    }
                }
                if (!referenceMatch)
                    System.out.println("Error. No record of your reference is found. Please try again.\n");
            }catch(InputMismatchException e)
            {
                System.out.println("Error. Please enter the reference number.");
                input.next();
            }
        }while(!referenceMatch);

    }

    public void viewBooking(bookingDatabase bookDB)
    {
        bookDB.displayBooking();
    }

    public void changeUserPassword()
    {
        boolean passwordCheck;

        String oldP, newP, newP2;

        System.out.println("\n==========================================");
        System.out.println("Password Management System");
        System.out.println("==========================================");
        System.out.println("Your password must contain at least 8 characters.");
        System.out.println("You cannot reuse your previous password");
        System.out.println("Please include at least 1 Capital and 1 numeric in your password.");
        System.out.println("Please also include one special character, @#$%^&+=");
        System.out.println("==========================================\n");

        input.nextLine();

        do {
            System.out.print("Enter your previous password: ");
            oldP = input.nextLine();

            if((!oldP.equals(this.password)))
            {
                System.out.println("Please enter your previous password.\n");
            }

        }while(!oldP.equals(this.password));


        do {
            System.out.println("\nEnter your new Password: ");
            newP = input.nextLine();

            passwordCheck = checkPassword(newP, oldP);
        }while(!passwordCheck);

        do {
            System.out.println("Enter your new Password again: ");
            newP2 = input.nextLine();

            if(!newP.equals(newP2))
            {
                System.out.println("Please enter the same password.\n");
            }
        }while(!newP.equals(newP2));

        if(newP.equals(newP2))
        {
            setUserPassword(newP2);
            System.out.println("\nYou have successfully change your password.");
            System.out.println("Exiting Password Management System.");
        }
    }

    public boolean checkPassword(String newPassword, String oldPassword)
    {
        boolean passwordCheck = true;

        boolean isAtLeast8   = newPassword.length() >= 8;//Checks for at least 8 characters
        boolean hasUppercase = !newPassword.equals(newPassword.toLowerCase());

        if(newPassword.equals(oldPassword))
        {
            passwordCheck = false;
            System.out.println("Error. Do not reuse your old password.");
        }

        else if(!isAtLeast8)
        {
            passwordCheck = false;
            System.out.println("Error. Minimum length for password is 8.");
        }

        else if(!hasUppercase)
        {
            passwordCheck = false;
            System.out.println("Error. Password should contain at least 1 Captial letter.");
        }

        //((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})
        //(([A-Z].*[0-9])|([0-9].*[A-Z]))
        //^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
        else if (!newPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
        {
            passwordCheck = false;
            System.out.println("Error. Password should contain at least 1 Capital and 1 Numeric.");
        }

        return passwordCheck;
    }

    @Override
    public String toString()
    {
        String words = "";
        words += "Name: " + firstName + " " + lastName;
        words += "\nStudent ID: " + studentID;
        words += "\n";

        return String.format(words);
    }
}

class admin
{
    public String username;
    public String password;

    public String firstName;
    public String lastName;

    public ArrayList<LocalDateTime> loginTime = new ArrayList<>();
    public ArrayList<LocalDateTime> logOutTime = new ArrayList<>();

    public int ID_general = 200000;
    public int running_counter = 0;
    public int adminID;

    private int option;

    private static Scanner input = new Scanner(System.in);

    admin()
    {

    }

    admin(String username, String password, String firstName, String lastName)
    {
        adminID = ID_general + (++running_counter);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public void setfirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setlastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setAdminUsername(String username)
    {
        this.username = username;
    }

    public void setAdminPassword(String password)
    {
        this.password = password;
    }

    public int displayAdminMenu()
    {
        System.out.println("\n==========================================");
        System.out.println("Welcome to Staff Menu");
        System.out.println("==========================================");

        System.out.println("(1) Create Event");
        System.out.println("(2) Manage Event");
        System.out.println("(3) Display Event");
        System.out.println("(4) Change Account Password");
        System.out.println("(5) Exit\n");

        System.out.println("(0) Log Out");

        boolean optionCheck = false;

        do {
            try{
                System.out.println("Enter an option: ");
                option = input.nextInt();
                optionCheck = true;
            }
            catch (InputMismatchException e)
            {
                input.nextLine();
                System.out.println("Error. Enter option in integer only.\n");
            }
        }
        while(!optionCheck);


        return option;
    }

    public void displayCreateEventMenu(eventDatabase eventDB)
    {
        String newEventName, newEventDate, newEventTime, newEventPromo;
        int newEventCapacity, newEventPrice;

        System.out.println("==========================================");
        System.out.println("Creating an Event");
        System.out.println("Enter the following information");
        System.out.println("==========================================");

        input.nextLine();
        System.out.println("(1) Name of Event: ");
        newEventName = input.nextLine();

        System.out.println("(2) Date of Event (dd/mm/yy): ");
        boolean dateCheck = false;
        do {
            newEventDate = input.nextLine();

            if(validateDate(newEventDate))
                dateCheck = true;
            else
                System.out.println("Error. Enter your expiry in this format (dd/mm/yy).");

        }while(!dateCheck);

        System.out.println("(3) Time Of Event (Please enter in 24hr format, HH:MM): ");
        boolean timeCheck = false;
        do{
            newEventTime = input.nextLine();

            if(validateTime(newEventTime))
                timeCheck = true;
            else
                System.out.println("Error. Please enter in correct format. Eg. 13:00. ");
        }while(!timeCheck);


        System.out.println("(4) Capacity: ");
        boolean capacityCheck = false;
        do {
            newEventCapacity = input.nextInt();

            if(newEventCapacity > 0)
                capacityCheck = true;
            else
                System.out.println("Error. Enter a positive integer.");

        }while(!capacityCheck);

        System.out.println("(5) Price: ");
        boolean priceCheck = false;
        do{
            newEventPrice = input.nextInt();
            if (newEventPrice > 0)
                priceCheck = true;
            else
                System.out.println("Error. Enter a positive integer or 0.");
        }while(!priceCheck);

        input.nextLine();
        System.out.println("(6) Promo Code: ");
        newEventPromo = input.nextLine();

        event event1 = new event(newEventName, newEventDate, newEventTime, newEventCapacity, newEventPrice, newEventPromo);
        eventDB.eventList.add(event1);

    }

    public void displayManageEventMenu(eventDatabase eventDB)
    {
        String selectedEvent, word = "";

        String newEventName, newEventDate, newEventTime, newEventPromo;
        int newEventCapacity, newEventPrice;
        int choice;

        boolean eventFound = false;

        input.nextLine();
        System.out.println("\n==========================================");
        System.out.println("Editing an Event");
        System.out.println("Enter the following information");
        System.out.println("==========================================");

        System.out.print("Name of Event would you like to edit: ");
        selectedEvent = input.nextLine();

        for(event e : eventDB.eventList)
        {
            if(selectedEvent.equals(e.eventName))
            {
                eventFound = true;

                System.out.println("\nPlease select what would you like to change: ");
                System.out.println("(1) Name of Event: ");
                System.out.println("(2) Date of Event: ");
                System.out.println("(3) Time Of Event: ");
                System.out.println("(4) Capacity: ");
                System.out.println("(5) Price: ");
                System.out.println("(6) Promo Code: ");
                System.out.println("(7) Return to Home Page");

                choice = input.nextInt();
                input.nextLine();

                if(choice == 1)
                {
                    System.out.println("Please enter new name for event: ");
                    newEventName = input.nextLine();
                    e.setEventName(newEventName);
                    word = "name";
                }

                else if(choice == 2)
                {
                    System.out.println("Please enter new date for event: ");
                    boolean dateCheck = false;
                    do {
                        newEventDate = input.nextLine();

                        if(validateDate(newEventDate))
                            dateCheck = true;
                        else
                            System.out.println("Error. Enter your expiry in this format (dd/mm/yy).");

                    }while(!dateCheck);
                    e.setEventDate(newEventDate);
                    word = "date";
                }

                else if(choice == 3)
                {
                    System.out.println("Please enter new time for event: ");
                    boolean timeCheck = false;
                    do{
                        newEventTime = input.nextLine();

                        if(validateTime(newEventTime))
                            timeCheck = true;
                        else
                            System.out.println("Error. Please enter in correct format. Eg. 13:00. ");
                    }while(!timeCheck);
                    e.setEventTime(newEventTime);
                    word = "time";
                }

                else if(choice == 4)
                {
                    System.out.println("Please enter new capacity for event: ");
                    boolean capacityCheck = false;
                    do {
                        newEventCapacity = input.nextInt();

                        if(newEventCapacity > 0)
                            capacityCheck = true;
                        else
                            System.out.println("Error. Enter a positive integer.");

                    }while(!capacityCheck);
                    e.setCapacity(newEventCapacity);
                    word = "capacity";
                }

                else if(choice == 5)
                {
                    System.out.println("Please enter new price for event: ");
                    boolean priceCheck = false;
                    do{
                        newEventPrice = input.nextInt();
                        if (newEventPrice > 0)
                            priceCheck = true;
                        else
                            System.out.println("Error. Enter a positive integer or 0.");
                    }while(!priceCheck);
                    e.setPrice(newEventPrice);
                    word = "price";
                }

                else if(choice == 6)
                {
                    System.out.println("Please enter new promo code for event: ");
                    newEventPromo = input.nextLine();
                    e.setPromocode(newEventPromo);
                    word = "promo";
                }
                else if(choice == 7)
                {
                    System.out.println("Returning to main menu page.");
                    break;
                }
                else
                {
                    System.out.println("Please enter a valid choice. ");
                }
            }
        }

        if(!eventFound)
        {
            System.out.println("Please enter an existing event to be edited");
            displayManageEventMenu(eventDB);
        }

        System.out.println("\nEvent " + word + " successfully change\nReturning to main page. ");
    }

    private boolean validateDate(String dateConfirm)
    {
        String regex = "^((3[01]|[12][0-9]|0[1-9])/1[0-2]|0[1-9])/[0-9]{2}$";

        return dateConfirm.matches(regex);
    }
    private boolean validateTime(String timeConfirm)
    {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        return timeConfirm.matches(regex);
    }

    public void adminDisplayEvent(eventDatabase eventDB)
    {
        eventDB.displayEvent();
    }

    public void changeAdminPassword()
    {
        boolean passwordCheck;
        String oldP, newP, newP2;

        System.out.println("\n==========================================");
        System.out.println("Password Management System");
        System.out.println("==========================================");
        System.out.println("Your password must contain at least 8 characters.");
        System.out.println("You cannot reuse your previous password");
        System.out.println("Please include at least 1 Capital and 1 numeric in your password.");
        System.out.println("==========================================\n");

        input.nextLine();

        do {
            System.out.print("Enter your previous password: ");
            oldP = input.nextLine();

            if((!oldP.equals(this.password)))
            {
                System.out.println("Please enter your previous password.\n");
            }

        }while(!oldP.equals(this.password));


        do {
            System.out.println("\nEnter your new Password: ");
            newP = input.nextLine();

            passwordCheck = checkPassword(newP, oldP);
        }while(!passwordCheck);

        do {
            System.out.println("Enter your new Password again: ");
            newP2 = input.nextLine();

            if(!newP.equals(newP2))
            {
                System.out.println("Please enter the same password.\n");
            }

        }while(!newP.equals(newP2));

        if(newP.equals(newP2))
        {
            setAdminPassword(newP2);
            System.out.println("\nYou have successfully change your password.");
            System.out.println("Exiting Password Management System.");
        }
    }

    public boolean checkPassword(String newPassword, String oldPassword)
    {
        boolean passwordCheck = true;

        boolean isAtLeast8   = newPassword.length() >= 8;//Checks for at least 8 characters
        boolean hasUppercase = !newPassword.equals(newPassword.toLowerCase());

        if(newPassword.equals(oldPassword))
        {
            passwordCheck = false;
            System.out.println("Error. Do not reuse your old password.");
        }

        else if(!isAtLeast8)
        {
            passwordCheck = false;
            System.out.println("Error. Minimum length for password is 8.");
        }

        else if(!hasUppercase)
        {
            passwordCheck = false;
            System.out.println("Error. Password should contain at least 1 Captial letter.");
        }

        else if (!newPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
        {
            passwordCheck = false;
            System.out.println("Error. Password should contain at least 1 Capital and 1 Numeric.");
        }

        return passwordCheck;
    }

    @Override
    public String toString()
    {
        String words = "";
        words += "Name: " + firstName + " " + lastName;
        words += "\nStaff ID: " + adminID;
        words += "\n";

        return String.format(words);
    }

}

class staff
{
    private static Scanner input = new Scanner(System.in);

    private int option;

    public String username;
    public String password;

    staff(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public boolean isNumber(String strNum)
    {
        boolean k = false;
        try
        {
            for(int i = 0; i < strNum.length(); i++)
            {
                if (Character.isDigit(strNum.charAt(i)))
                {
                    k = true;
                }
            }
        }
        catch (NumberFormatException | NullPointerException nfe)
        {
            k = true;
        }
        return k;
    }

    public void createUserAccount(database userDatabase)
    {
        for(user u : userDatabase.userList)
            System.out.println(u.username);
        System.out.println();


        String firstName, lastName, username, password;

        System.out.println("==========================================");
        System.out.println("Creating a Student Account");
        System.out.println("Enter the following information");
        System.out.println("==========================================");
        input.nextLine();

        System.out.println("First Name: ");
        firstName = input.nextLine();
        while(isNumber(firstName))
        {
            System.out.println("Numbers detected. Please re-enter first name: ");
            firstName = input.nextLine();
        }

        System.out.println("Last Name: ");
        lastName = input.nextLine();
        while(isNumber(lastName))
        {
            System.out.println("Numbers detected. Please re-enter last name: ");
            lastName = input.nextLine();
        }

        boolean usernameExist = false;

        do {
            System.out.println("Username for User: ");
            username = input.nextLine();

            for(user u : userDatabase.userList)
            {
                if(u.username.equals(username))
                {
                    System.out.println("Error. Username: " + username + " already exist in database. Please create another.\n");
                    usernameSuggestion(username);
                }
                else
                    usernameExist = true;
            }

        }while(!usernameExist);

        System.out.println("Password for User");
        password = input.nextLine();

        user u = new user(username, password, firstName, lastName);
        userDatabase.addUserName(u);

        System.out.println();
        System.out.println("Account for student " + firstName + " " + lastName + " Successfully created");
        System.out.println("UserName: " + username);
        System.out.println("Password: " + password);

    }

    public void usernameSuggestion(String username)
    {
        Random rand = new Random();

        String suggesteduser = "";

        System.out.println("==========Suggested Username==========");
        for(int i = 1; i < 6; i++)
        {
            suggesteduser = username + rand.nextInt(2000);
            System.out.printf("%d: %s\n", i, suggesteduser);
        }
        System.out.println("======================================\n");
    }

    public void createAdminAccount(database userDatabase)
    {
        String firstName, lastName, username, password;

        System.out.println("==========================================");
        System.out.println("Creating a Staff Account");
        System.out.println("Enter the following information");
        System.out.println("==========================================");
        input.nextLine();

        System.out.println("First Name: ");
        firstName = input.nextLine();
        while(isNumber(firstName))
        {
            System.out.println("Numbers detected. Please re-enter first name: ");
            firstName = input.nextLine();
        }

        System.out.println("Last Name: ");
        lastName = input.nextLine();
        while(isNumber(lastName))
        {
            System.out.println("Numbers detected. Please re-enter last name: ");
            lastName = input.nextLine();
        }

        boolean usernameExist = false;

        do {
            System.out.println("Username for Admin: ");
            username = input.nextLine();

            for(admin a : userDatabase.adminList)
            {
                if(a.username.equals(username))
                {
                    System.out.println("Error. Username: " + username + " already exist in database. Please create another.\n");
                    usernameSuggestion(username);
                }
                else
                    usernameExist = true;
            }

        }while(!usernameExist);

        System.out.println("Password for Admin");
        password = input.nextLine();

        admin a = new admin(username, password, firstName, lastName);

        userDatabase.addAdmin(a);
        System.out.println();
        System.out.println("Account for staff " + firstName + " " + lastName + " Successfully created");
        System.out.println("UserName: " + username);
        System.out.println("Password: " + password);
    }

    public void manageUserAccount(database userDatabase)
    {
        String name, newUsername;
        int choice;
        boolean userFound = false;

        System.out.println("==========================================");
        System.out.println("Editing a Student Account");
        System.out.println("Enter the following information");
        System.out.println("==========================================");

        System.out.println("------------------------------------------");
        System.out.println("Displaying all Users");
        System.out.println("------------------------------------------");
        for(user u : userDatabase.userList)
        {
            System.out.println("Name: " + u.firstName);
            System.out.println("UserName: " + u.username);
            System.out.println();
        }
        System.out.println("------------------------------------------");

        input.nextLine();
        System.out.println("\nEnter username of Student you would like to edit: ");
        name = input.nextLine();

        for(user u : userDatabase.userList)
        {
            if(u.username.equals(name))
            {
                userFound = true;

                System.out.println("Please select what would you like to change: ");
                System.out.println("(1) Username");
                System.out.println("(2) Password");
                System.out.println("(3) First Name");
                System.out.println("(4) Last Name");

                choice = input.nextInt();
                input.nextLine();

                if(choice == 1)
                {
                    System.out.println("Enter new username: ");
                    newUsername = input.nextLine();
                    u.setUsername(newUsername);
                    System.out.println("Student's username successfully changed.");
                }

                else if(choice == 2)
                {
                    String p1, p2;

                    do {
                        System.out.println("Enter new password: ");
                        p1 = input.nextLine();

                        System.out.println("Renter new password: ");
                        p2 = input.nextLine();

                        if(p1.equals(p2))
                        {
                            System.out.println("Student password successfully changed.");
                            u.setUserPassword(p2);
                        }
                        else
                        {
                            System.out.println("Please enter similar password for confirmation. ");
                        }
                    }while(!p1.equals(p2));
                }
                else if(choice == 3)
                {
                    System.out.println("Enter new first name: ");
                    String newfname = input.nextLine();
                    while (isNumber(newfname))
                    {
                        System.out.println("Numbers detected. Please re-enter first name: ");
                        newfname = input.nextLine();
                    }
                    u.setfirstName(newfname);
                    System.out.println("Student's first name successfully changed.");
                }
                else if(choice == 4)
                {
                    System.out.println("Enter new last name: ");
                    String newlname = input.nextLine();
                    while (isNumber(newlname))
                    {
                        System.out.println("Numbers detected. Please re-enter last name: ");
                        newlname = input.nextLine();
                    }
                    u.setlastName(newlname);
                    System.out.println("Student's last name successfully changed.");
                }
                else
                {
                    System.out.println("Invalid choice. Please try again");
                }
            }
        }
        if(!userFound)
        {
            System.out.println("Enter a existing student.");
        }
    }

    public void manageAdminAccount(database userDatabase)
    {
        String name, newUsername;
        int choice;
        boolean adminFound = false;

        System.out.println("==========================================");
        System.out.println("Editing a Staff Account");
        System.out.println("Enter the following information");
        System.out.println("==========================================");

        System.out.println("------------------------------------------");
        System.out.println("Displaying all Staff");
        System.out.println("------------------------------------------");
        for(admin a : userDatabase.adminList)
        {
            System.out.println("Name: " + a.firstName);
            System.out.println("UserName: " + a.username);
        }
        System.out.println("------------------------------------------");

        input.nextLine();
        System.out.println("\nEnter username of user you would like to edit: ");
        name = input.nextLine();

        for(admin a : userDatabase.adminList)
        {
            if(a.username.equals(name))
            {
                adminFound = true;

                System.out.println("Please select what would you like to change: ");
                System.out.println("(1) Username");
                System.out.println("(2) Password");
                System.out.println("(3) First Name");
                System.out.println("(4) Last Name");

                choice = input.nextInt();
                input.nextLine();

                if(choice == 1)
                {
                    System.out.println("Enter new username: ");
                    newUsername = input.nextLine();
                    a.setAdminUsername(newUsername);
                    System.out.println("Staff's username successfully changed.");
                }

                else if(choice == 2)
                {
                    String p1, p2;

                    do {
                        System.out.println("Enter new password: ");
                        p1 = input.nextLine();

                        System.out.println("Renter new password: ");
                        p2 = input.nextLine();

                        if(p1.equals(p2))
                        {
                            System.out.println("Staff password successfully changed.");
                            a.setAdminPassword(p2);
                        }
                        else
                        {
                            System.out.println("Please enter similar password for confirmation. ");
                        }
                    }while(!p1.equals(p2));
                }
                else if(choice == 3)
                {
                    System.out.println("Enter new first name: ");
                    String newfname = input.nextLine();
                    while (isNumber(newfname))
                    {
                        System.out.println("Numbers detected. Please re-enter first name: ");
                        newfname = input.nextLine();
                    }
                    a.setfirstName(newfname);
                    System.out.println("Staff's first name successfully changed.");
                }
                else if(choice == 4)
                {
                    System.out.println("Enter new last name: ");
                    String newlname = input.nextLine();
                    while (isNumber(newlname))
                    {
                        System.out.println("Numbers detected. Please re-enter last name: ");
                        newlname = input.nextLine();
                    }
                    a.setlastName(newlname);
                    System.out.println("Staff's last name successfully changed.");
                }
                else
                {
                    System.out.println("Invalid choice. Please try again");
                }
            }
        }
        if(!adminFound)
        {
            System.out.println("Enter a existing staff.");
        }
    }

    public void viewActivityHistory(database db)
    {
        System.out.println("=====================================");
        System.out.println("View Activity History");
        System.out.println("=====================================\n");

        System.out.println("----------------STUDENTS----------------");
        for(user u : db.userList)
        {
            System.out.println("*************************************");
            System.out.println("Student: " + u.firstName + " " + u.lastName);
            System.out.println("Last Login");
            for(LocalDateTime t : u.loginTime)
            {
                System.out.println(t);
            }

            System.out.println("\nLast LogOut");
            for(LocalDateTime t : u.logOutTime)
            {
                System.out.println(t);
            }
            System.out.println("*************************************");
        }
        System.out.println("-------------------------------------\n");

        System.out.println("----------------STAFF----------------");
        for(admin a : db.adminList)
        {
            System.out.println("*************************************");
            System.out.println("Staff: " + a.firstName + " " + a.lastName);
            System.out.println("Last Login");
            for(LocalDateTime t : a.loginTime)
            {
                System.out.println(t);
            }

            System.out.println("\nLast LogOut");
            for(LocalDateTime t : a.logOutTime)
            {
                System.out.println(t);
            }
            System.out.println("*************************************");
            System.out.println();
        }
        System.out.println("-------------------------------------");
    }

    public int displayStaffMenu()
    {
        System.out.println("\n==========================================");
        System.out.println("Welcome to Staff Administrator Menu");
        System.out.println("==========================================");

        System.out.println("(1) Create Student Account");
        System.out.println("(2) Create Staff Account");
        System.out.println("(3) Manage Student Account");
        System.out.println("(4) Manage Staff Account");
        System.out.println("(5) Display User Activity History");
        System.out.println("(6) Exit\n");

        System.out.println("(0) Log Out");

        boolean optionCheck = false;

        do {
            try{
                System.out.println("Enter an option: ");
                option = input.nextInt();
                optionCheck = true;
            }
            catch (InputMismatchException e)
            {
                input.nextLine();
                System.out.println("Error. Enter option in integer only.\n");
            }
        }
        while(!optionCheck);

        return option;
    }

}

class database
{

    public ArrayList<user> userList = new ArrayList<user>();
    public ArrayList<admin> adminList = new ArrayList<admin>();
    public ArrayList<staff> staffList = new ArrayList<staff>();

    database() {}

    public void addUserName(user userID)
    {
        userList.add(userID);
    }

    public void addAdmin(admin adminID)
    {
        adminList.add(adminID);
    }

    public void addStaff(staff staffID) {staffList.add(staffID);}
}

class Main
{
    private static Scanner input = new Scanner(System.in);
    public static database userDatabase = new database();
    public static eventDatabase eventDB = new eventDatabase();
    public static bookingDatabase bookDB = new bookingDatabase();

    public static String username;
    public static String password;

    public static Integer option;

    public static void displayLogin()
    {
        System.out.println("============================================");
        System.out.println("Welcome to UOW Event Booking Management System.");
        System.out.println("============================================\n");

        System.out.println("Enter username: ");
        username = input.nextLine();

        System.out.println("Enter Password: ");
        password = input.nextLine();
    }

    public static void processExit()
    {
        System.exit(0);
    }

    public static void processLogin(database userDatabase)
    {
        boolean userLogin = false , adminLogin = false, staffLogin = false;
        boolean login = false;

        Integer userIndex = null;
        Integer adminIndex = null;
        Integer staffIndex= null;

        do
        {
            for(int i = 0; i < userDatabase.userList.size(); i++)
            {
                if (userDatabase.userList.get(i).username.equals(username)
                        && userDatabase.userList.get(i).password.equals(password))
                {
                    userLogin = true;
                    login = true;
                    userIndex = i;

                    LocalDateTime t = LocalDateTime.now();
                    userDatabase.userList.get(i).loginTime.add(t);
                }
            }

            for(int i = 0; i < userDatabase.adminList.size(); i++)
            {
                if (userDatabase.adminList.get(i).username.equals(username)
                        && userDatabase.adminList.get(i).password.equals(password))
                {
                    adminLogin = true;
                    login = true;
                    adminIndex = i;

                    LocalDateTime t = LocalDateTime.now();
                    userDatabase.adminList.get(i).loginTime.add(t);
                }
            }

            for(int i = 0; i < userDatabase.staffList.size(); i++)
            {
                if(userDatabase.staffList.get(i).username.equals(username)
                        && userDatabase.staffList.get(i).password.equals(password))
                {
                    staffLogin = true;
                    login = true;
                    staffIndex = i;
                }
            }

            if(!login)
            {
                System.out.println("Access denied. Invalid username or password. ");
                System.out.println("Please enter your credentials again.\n");
                displayLogin();
            }
        }
        while(!login);

        if(userLogin)
        {
            processUser(userDatabase, userIndex);
        }

        if(adminLogin)
        {
            processAdmin(userDatabase, adminIndex);
        }

        if (staffLogin)
        {
            processStaff(userDatabase, staffIndex);
        }

    }

    public static void processUser(database userDatabase, int i)
    {
        System.out.println("Student Access granted");

        boolean exit = false;

        option = userDatabase.userList.get(i).displayUserMenu();

        do
        {
            if(option == 1)
            {
                userDatabase.userList.get(i).displayEventForUser(eventDB);
                option = userDatabase.userList.get(i).displayUserMenu();
            }

            else if(option == 2)
            {
                userDatabase.userList.get(i).bookEvent(eventDB, bookDB);
                option = userDatabase.userList.get(i).displayUserMenu();
            }
            else if(option == 3)
            {
                userDatabase.userList.get(i).changeUserPassword();
                option = userDatabase.userList.get(i).displayUserMenu();
            }
            else if (option == 4)
            {
                userDatabase.userList.get(i).viewBooking(bookDB);
                option = userDatabase.userList.get(i).displayUserMenu();
            }

            else if(option == 5)
            {
                if(bookDB.bookingList.isEmpty())
                {
                    System.out.println("No Booking records found. Please book a event first.");
                    option = userDatabase.userList.get(i).displayUserMenu();
                }
                else
                {
                    userDatabase.userList.get(i).manageBooking(eventDB, bookDB);
                    option = userDatabase.userList.get(i).displayUserMenu();
                }
            }

            else if(option == 6)
            {
                exit = true;
                System.out.println("Thank you for using UOW Event booking system");
                processExit();
            }
            else if(option == 0)
            {
                LocalDateTime t = LocalDateTime.now();
                userDatabase.userList.get(i).logOutTime.add(t);

                loginMenu();
            }
            else
            {
                System.out.println("Please enter a valid option. ");
                option = userDatabase.userList.get(i).displayUserMenu();
            }
        }
        while(!exit);
    }

    public static void processAdmin(database userDatabase, int i)
    {
        System.out.println("Staff Access granted");

        boolean exit = false;

        option = userDatabase.adminList.get(i).displayAdminMenu();

        do
        {
            if(option == 1)
            {
                userDatabase.adminList.get(i).displayCreateEventMenu(eventDB);
                option = userDatabase.adminList.get(i).displayAdminMenu();
            }
            else if(option == 2)
            {
                userDatabase.adminList.get(i).displayManageEventMenu(eventDB);
                option = userDatabase.adminList.get(i).displayAdminMenu();
            }
            else if(option == 3)
            {
                userDatabase.adminList.get(i).adminDisplayEvent(eventDB);
                option = userDatabase.adminList.get(i).displayAdminMenu();
            }
            else if(option == 4)
            {
                userDatabase.adminList.get(i).changeAdminPassword();
                option = userDatabase.adminList.get(i).displayAdminMenu();
            }
            else if(option == 5)
            {
                exit = true;
                System.out.println("Thank you for using UOW Event booking system");
                processExit();
            }
            else if(option == 0)
            {
                LocalDateTime t = LocalDateTime.now();
                userDatabase.adminList.get(i).logOutTime.add(t);

                loginMenu();
            }
            else
            {
                System.out.println("Please enter a valid option");
                option = userDatabase.adminList.get(i).displayAdminMenu();
            }
        }
        while(!exit);
    }

    public static void processStaff(database userDatabase, int i)
    {
        System.out.println("Staff Administrator Access granted");

        boolean exit = false;

        option = userDatabase.staffList.get(i).displayStaffMenu();

        do
        {
            if(option == 1)
            {
                userDatabase.staffList.get(i).createUserAccount(userDatabase);
                option = userDatabase.staffList.get(i).displayStaffMenu();
            }
            else if(option == 2)
            {
                userDatabase.staffList.get(i).createAdminAccount(userDatabase);
                option = userDatabase.staffList.get(i).displayStaffMenu();
            }
            else if(option == 3)
            {
                userDatabase.staffList.get(i).manageUserAccount(userDatabase);
                option = userDatabase.staffList.get(i).displayStaffMenu();
            }
            else if(option == 4)
            {
                userDatabase.staffList.get(i).manageAdminAccount(userDatabase);
                option = userDatabase.staffList.get(i).displayStaffMenu();
            }
            else if(option == 5)
            {
                userDatabase.staffList.get(i).viewActivityHistory(userDatabase);
                option = userDatabase.staffList.get(i).displayStaffMenu();
            }
            else if(option == 6)
            {
                exit = true;
                System.out.println("Thank you for using UOW Event booking system");
                processExit();
            }
            else if(option == 0)
            {
                loginMenu();
            }
            else
            {
                System.out.println("Please enter a valid option");
                option = userDatabase.staffList.get(i).displayStaffMenu();
            }
        }
        while(!exit);
    }

    public static void loginMenu()
    {
        displayLogin();
        processLogin(userDatabase);
    }

    public static void main(String [] args)
    {
        user user1 = new user("user", "password", "Jonas"
                , "Wong");
        admin admin1 = new admin("admin", "password", "Jonas", "Wong");
        staff staff1 = new staff("staff", "password");

        event table = new event("Table", "21122019", "12pm",
                100, 12, "table");

        event chair = new event("Chair", "25122019", "11pm",
                200, 20, "chair");

        /*
        admin admin1 = new admin("staff", "password", "Jonas", "Wong");
        staff staff1 = new staff("admin", "password");
        */


        eventDB.addEvent(table);
        eventDB.addEvent(chair);

        userDatabase.addUserName(user1);
        userDatabase.addAdmin(admin1);
        userDatabase.addStaff(staff1);

        loginMenu();
    }
}



