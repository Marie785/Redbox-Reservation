//
//Marie-Claire Salha
//mjs170530
//
package Redbox;

import java.io.*;
import java.util.Scanner;

//prototypes: public static void checkAddRemove(String, String, PrintWriter, BST);
//            public static void checkRentReturn(String, String, PrintWriter, BST);

public class Main 
{
    public static void main(String[] args) throws IOException
    {
        File inventory = new File("inventory.dat");
        Scanner input = new Scanner(inventory);
        String line = "", title = "", available = "", rented = "";
        int a = 0, r = 0;
        BinarySearchTree<Movies> BST = new BinarySearchTree<>();
        
        //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        //if the inventory file exists, then we'll build our binary tree  *
        //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        if(inventory.exists())
        {
            //read until we reach the end of the file
            while(input.hasNext())
            {
                //gets the next line
                line = input.nextLine();
                
                //gets the title
                line = line.substring(1);
                title = line.substring(0, line.indexOf('\"'));
                line = line.substring(line.indexOf('\"') + 1);
                line = line.substring(line.indexOf(',') + 1);
                
                //gets the number available
                available = line.substring(0, line.indexOf(','));
                a = Integer.parseInt(available);
                line = line.substring(line.indexOf(',') + 1);
                
                //gets the number rented
                rented = line;
                r = Integer.parseInt(rented);
                
                //inserts the info into the movies object. 
                //Then calls the insert function to put it into the binary tree.
                Movies M = new Movies(title, a, r);
                BST.insert(M);
            }
            input.close();
        }
        else    //print out an error message if the file doesn't exist
            System.out.println("The inventory.dat file doesn't exist.");
        
        //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        //Now we're gonna make a new transcation file and open that up  *
        //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        File transaction = new File("transaction.log");
        input = new Scanner(transaction);
        PrintWriter output = new PrintWriter(new File("error.log"));
        
        //check to see if it exists
        if(transaction.exists())
        {
            //while there are still lines in the file, keep reading the file
            while(input.hasNext())
            {
                //take in the line from the transaction.log file
                line = input.nextLine();
                
                //if the line is isn't even as long as the word "add", then just
                //start the loop over
                if(line.length() < 3)
                {
                    output.println(line);
                    continue;
                }
                
                //if it starts with add
                if(line.substring(0, 3).equals("add"))
                    checkAddRemove(line, "add", output, BST);
                //if it starts with remove
                else if(line.substring(0, 6).equals("remove"))
                    checkAddRemove(line, "remove", output, BST);
                //if it starts with rent
                else if(line.substring(0, 4).equals("rent"))
                    checkRentReturn(line, "rent", output, BST);
                //if it starts with return
                else if(line.substring(0, 6).equals("return"))
                    checkRentReturn(line, "return", output, BST);
                else
                {
                    output.println(line);
                    continue;
                }
            }
            input.close();
            output.close();
            
            //now we're gonna write the final list of movies to the redbox_kiosk 
            //file
            output = new PrintWriter(new File("redbox_kiosk.txt"));
            
            output.printf("%-33s%-8s%-8s", "Title", "Av.", "Rented");
            output.println();
            BST.printTree(output);
            
            output.close();
        }
        //if file doesn't exist, show an error message
        else
            System.out.println("The transaction.log file doesn't exist.");
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This will check to see if the line that is asking whether   *
    //to add or remove a title is valid                           *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public static void checkAddRemove(String line, String AR, PrintWriter output, BinarySearchTree<Movies> BST)
    {
        boolean valid = false;
        String line2 = "", title = "", numAR = "";
        int num = 0;
        
        //this will cut the string to be the length it needs to be based on
        //whether the line started with "add" or "remove"
        if(AR.equals("add"))
            line2 = line.substring(4);
        else if(AR.equals("remove"))
            line2 = line.substring(7);
        
        //check to see if the line is empty
        if(line2.equals(""))
        {
            output.println(line);
            return;
        }
        //check to see if the line is valid
        else if(line2.charAt(0) == '\"')
        {
            line2 = line2.substring(1);
            if(line2.equals("")) //if the string is empty after cutting it
            {
                output.println(line);
                return;
            }
            if(line2.indexOf('\"') == -1)   //if there is no other quotation mark
            {
                output.println(line);
                return;
            }
            
            //if everything else is valid for the title, then add in title
            title = line2.substring(0, line2.indexOf('\"'));
            valid = true;
            
            if(line2.indexOf(',') == -1)    //if there is no comma in the line
            {
                output.println(line);
                return;
            }
            
            //if there is a comma, then cut the string up to the number they
            //want to add/remove
            line2 = line2.substring(line2.indexOf('\"') +1);
            line2 = line2.substring(line2.indexOf(',') + 1);
            
            if(line2.equals("")) //if the string is empty after cutting it
            {
                output.println(line);
                return;
            }
            
            
            //get the number that the person is trying ot add or remove
            numAR = line2;
            
            //if the rest of the line isn't a valid number, then we will output
            //the line to error.log
            try
            {
                num = Integer.parseInt(numAR);
            }
            catch(NumberFormatException E)
            {
                output.println(line);
                return;
            }
        }
        
        //if for some reason it's still false and it reaches this point, then
        //it will make sure that it takes care of that
        if(valid == false)
        {
            output.println(line);
            return;
        }
        
        //make a new object to hold this information in
        Movies M = new Movies(title, num);
        
        //if the line is valid, then check to see if it said to add or remove,
        //and then call that respective function for the tree
        if(AR.equals("add"))    //if we are adding movies  
        {
            //searches to see if the new movie is already in the binary tree or
            //not
            Movies search = BST.searchTree(M);
            if(search == null)  //if we didn't find it, then we'll insert it
                BST.insert(M);
            else    //if we did, then we'll just increase the number available
                search.add(num);
            
        }
        else    //if we're removing movies
        {
            Movies search = BST.searchTree(M);
            if(search == null)
            {
                output.println(line);
                return;
            }
            else
            {
                search.remove(num);
                
                //if it turns out that we have no more of that movie available
                //or rented, then we will delete it from the binary tree
                if(search.available <= 0 && search.rented <= 0)
                    BST.deleteNode(M);
            }
            
        }
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function will make sure that the line to rent or remove is *
    //valid, and then will call the respective functions to rent or   *
    //remove                                                          *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public static void checkRentReturn(String line, String RR, PrintWriter output, BinarySearchTree<Movies> BST)
    {
        boolean valid = false;
        String line2 = "", title = "";
        
        //This will cut the string up to the correct point depending on 
        //whether it starts with "rent" or "return"
        if(RR.equals("rent"))
            line2 = line.substring(5);
        else if(RR.equals("return"))
            line2 = line.substring(7);
        
        //check to see if the line is empty
        if(line2.equals(""))
        {
            output.println(line);
            return;
        }
        //check to see if the line is valid
        else if(line2.charAt(0) == '\"')
        {
            line2 = line2.substring(1);
            if(line2.equals("")) //if the string is empty after cutting it
            {
                output.println(line);
                return;
            }
            if(line2.indexOf('\"') == -1)   //if there is no other quotation mark
            {
                output.println(line);
                return;
            }
            
            //if everything else is valid for the title, then add in title
            title = line2.substring(0, line2.indexOf('\"'));
            line2 = line2.substring(line2.indexOf('\"') + 1);
            valid = true;
        }
        
        //if for some reason it's still false and it reaches this point, then
        //it will make sure that it takes care of that
        if(valid == false)
        {
            output.println(line);
            return;
        }
        
        //if the line is NOT empty by the time we're done parsing and taking in
        //the title of the movie
        if(!(line2.equals("")))
        {
            output.println(line);
            return;
        }
        
        Movies M = new Movies(title);
        
        
        //if the line is valid, then check to see if it said to add or remove,
        //and then call that respective function for the tree
        if(RR.equals("rent"))
        {
            //searches for the movie and checks to see if we can rent one or
            //not
            Movies search = BST.searchTree(M);
            if(search == null)
                output.println(line);
            else
                search.rent();
        }
        else
        {
            //searches for the movie to see if we can return it or not
            Movies search = BST.searchTree(M);
            if(search == null)
                output.println(line);
            else
                search.returned();
        }
        
    }
}