//
//Marie-Claire Salha
//mjs170530
//
package Redbox;


public class Node<E extends Comparable<E>>
{
    //these are the variables for our generic Node class
    //data is the payload
    E data;
    Node left = null;
    Node right = null;
    
    //default constructor
    Node(){}
    
    //overloaded constructor
    Node(E d, Node l, Node r)
    {
        data = d;
        left = l;
        right = r;
    }
    
    //overloaded constructor only for the payload
    Node(E d)
    {
        data = d;
    }
    
    //mutators
    public void setData(E d) {data = d;}
    public void setLeft(Node l) {left = l;}
    public void setRight(Node r) {right = r;}
    
    //accessors
    public E getData() {return data;}
    public Node getLeft() {return left;}
    public Node getRight() {return right;}
    
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    //This function calls the compareTo function of our payload               *
    //our payload is Movies                                                   *
    //when we create our BinarySearchTree object in main, it's going to be of *
    //type Movies, this will also set the generic variables of the Node class *
    //to type Movies as well. But, we won't be interacting with the Node class*
    //or the Movies class direction. All interaction will be done through the *
    //BinarySearchTree class                                                  *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    public int compareTo(Node<E> newNode)
    {
        int value = 0;
        value = data.compareTo(newNode.data);
        return value;
    }
}