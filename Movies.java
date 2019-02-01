//
//Marie-Claire Salha
//mjs170530
//
package Redbox;

public class Movies implements Comparable<Movies>
{
    String title = "";
    int available = 0;
    int rented = 0;
    
    //default constructor
    Movies(){}
    
    //overloaded constructor
    Movies(String t, int a, int r)
    {
        title = t;
        available = a;
        rented = r;
    }
    
    //overloaded constructor for renting and returning
    Movies(String t)
    {
        title = t;
    }
    
    //overloaded constructor for adding and removing
    Movies(String t, int a)
    {
        title = t;
        available = a;
    }
    
    //mutators
    public void setTitle(String t) {title = t;}
    public void setAvailable(int a) {available = a;}
    public void setRented(int r) {rented = r;}
    
    //accessors
    public String getTitle() {return title;}
    public int getAvailable() {return available;}
    public int getRented() {return rented;}
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //The final class where the compareTo function will be called from  *
    //BinarySearchTree --> Node --> Movies                              *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    @Override
    public int compareTo(Movies t)
    {
        int value = 0;
        value = title.compareTo(t.title);
        return value;
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * *
    //toString method                                 *
    //returns the contents of each node, aka, E data  *
    //* * * * * * * * * * * * * * * * * * * * * * * * *
    @Override
    public String toString()
    {
        return String.format("%-33s%-8d%-8d", title, available, rented);
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function increases the number of available movies in a node  *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public void add(int num)
    {
       available += num;
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function decreases the number of available movies in a node  *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public void remove(int num)
    {
       available -= num;
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function increases the number of rented movies in a node     *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public void rent()
    {
       rented++;
       available--;
    }
      
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function decreases the number of rented movies in a node     *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public void returned()
    {
       rented--;
       available++;
    }
}
