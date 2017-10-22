package ua.bellkross.bellnotebook.model;

import java.util.ArrayList;

import ua.bellkross.bellnotebook.database.DBHelper;


public class ArrayListOfContacts extends ArrayList<Contact> {

    private static ArrayList<Contact> instance;

    private ArrayListOfContacts(){
        this.instance = new ArrayList<Contact>();
    }

    public static ArrayList<Contact> getArrayList(){
        if(instance == null){
            new ArrayListOfContacts();
            return instance;
        } else
            return instance;
    }

    public static void sort(){
        instance = DBHelper.getDBHelper().sortContacts();
    }

    @Override
    public String toString() {
        String res = "{null}" + '\n';
        for (Contact contact : instance) {
            res += contact.toString() + '\n';
        }
        return res;
    }
}
