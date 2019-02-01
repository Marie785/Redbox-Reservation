//
//Marie-Claire Salha
//mjs170530
//
package Redbox;

import java.io.PrintWriter;

public class BinarySearchTree<E extends Comparable<E>>
{
    Node<E> root = null;
    
    //* * * * * * * * * * * * * * * * 
    //helper function for insertion *
    //* * * * * * * * * * * * * * * *
    public void insert(E info)
    {
        //makes a new node with the information that was passed into the
        //function
        //also makes a node called cur that holds onto the root, and passes
        //those two things into the insert function so that we can start
        //the recursive process
        Node<E> newNode = new Node(info);
        Node<E> cur = root;
        insert(newNode, cur);
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * *
    //recursive insert function                     *
    //inserts new nodes into the binary search tree *
    //* * * * * * * * * * * * * * * * * * * * * * * *
    public void insert(Node newNode, Node cur)
    {
        if(root == null)
        {
            root = newNode;
        }
        else
        {
            //if the number is negative, it goes to the left
            if(newNode.compareTo(cur) < 0)  //REMOVED ELSE IF AND TURNED IT INTO IF
            {
                //if the node on left is empty, then insert
                if(cur.left == null)
                    cur.left = newNode;
                //if the node is not empty, call the function again and do recursion
                else
                    insert(newNode, cur.left);
            }
            
            //if the number is positive, it goes to the right
            else if(newNode.compareTo(cur) > 0)
            {
                //if the node on the right is empty, then insert
                if(cur.right == null)
                    cur.right = newNode;
                //if node is not empty, do recursion
                else
                    insert(newNode, cur.right);
            }
        }
    }
    
    
    //* * * * * * * * * * * * * * * * * * * *
    //helper function for printing the tree *
    //* * * * * * * * * * * * * * * * * * * *
    public String printTree(PrintWriter output)
    {
        //make a string to finally return at the end of the recursion 
        String line = "";
        line += printTree(root, line, output);  //calls the recursive function
        return line;
    }
    
    //* * * * * * * * * * * * * *
    //recursive print function  *
    //* * * * * * * * * * * * * *
    public String printTree(Node<E> node, String tree, PrintWriter output)
    {
        //if the node is null, then don't do anything, and head back up the tree
        if(node == null)
            return "";
        
        //this will first go all the way down the left side of the tree, and
        //while it's going back up (when a node == null), it will go down each
        //right path and print out everything in alphabetical order
        printTree(node.left, tree, output);
        tree = node.data.toString() + '\n';
        output.print(tree);
        printTree(node.right, tree, output);
        
        return tree;
    }
    
    
    //* * * * * * * * * * * * * * * * * * * *
    //helper function for printing the tree *
    //* * * * * * * * * * * * * * * * * * * *
    public E searchTree(E info)
    {
        //creates a new node that holds the information of the movie we're
        //searching for
         Node<E> newNode = new Node(info);
        return searchTree(root, newNode);
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This is the search function that will search to see if a movie already  *
    //exists in the binary tree that we're trying to rent/remove/add/return   *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public E searchTree(Node<E> cur, Node<E> newNode)
    {
        //if the movie we were looking for wasn't in the tree
        if(cur == null)
            return null;
        //if the newNode is less than the cur pointer, then we'll move the cur
        //pointer to the left of the binary tree
        else if(newNode.compareTo(cur) < 0)
            return (E)searchTree(cur.left, newNode);
        //if the newNode is greater than the cur pointer, then we'll move the
        //cur pointer to the right of the binary tree
        else if(newNode.compareTo(cur) > 0)
            return (E)searchTree(cur.right, newNode);
        else if(newNode.compareTo(cur) == 0)
            return cur.data;
        
        return null;
    }
            
        
    //FIX YOUR HEADER!!!
    //CHANGED RETURN TYPE FROM E TO NODE
    //CHANGED PARAMETER FROM E TO NODE
    public E deleteNode(E info) //removed E info
    {
        Node<E> parent = null;
        Node<E> cur = root;
        
        //while cur isn't pointing to anything that's null, and isn't on the
        //node we're looking for
        while(cur != null && info.compareTo(cur.data) != 0)
        {
            parent = cur;
            
            //if what we're looking for is less than the cur node, then we'll
            //move cur to the left
            //otherwise, we'll move cur to the right
            if(info.compareTo(cur.data) < 0)
                cur = cur.left;
            else
                cur = cur.right;
        }
        
        //if the cur pointer isn't on null, then we'll delete in different ways
        //based on the number of children on the node
        if(cur != null)
        {
            //if the node has zero children (is a leaf)
            if(cur.left == null && cur.right == null)
            {
                //if the only thing in the tree is the root, then we just want
                //to delete the root
                if(parent == null)
                    root = null;
                //if the current node is on the right of the parent
                else if(cur.compareTo(parent) > 0)
                    parent.right = null;
                //if the current node is on the left of the parent
                else
                    parent.left = null;
            }     
            
            //if the node has one child
            else if(cur.left == null ^ cur.right == null)
            {
                //if the node we're deleting is on the right of cur
                if(cur.left == null)
                {
                    //if the node we were looking for was the root
                    if(parent == null)
                        root = cur.right;
                    //if the node we were looking for was on the right side of
                    //the parent node
                    else if(cur.compareTo(parent) > 0)
                        parent.right = cur.right;
                    //if the node we were looking for was on the left side of
                    //the parent node
                    else
                        parent.left = cur.right;
                }
                //if the node we're deleting is on the left side of cur
                else
                {
                    //if the node we were looking for was the root
                    if(parent == null)
                        root = cur.left;
                    //if cur is on the right side of parent
                    else if(cur.compareTo(parent) > 0)
                        parent.right = cur.left;
                    else
                        parent.left = cur.left;
                }
            }
            
            //if the node has two children
            else
            {
                //parent will hold the position where node will be copied
                //and then we will move cur to the left child
                parent = cur;
                cur = cur.left;
                
                //find the max value of the left subtree my moving cur down the
                //right branch of the left subtree until it hits right before 
                //null
                while(cur.right != null)
                    cur = cur.right;
                
                //call the delete function again to delete that node that was
                //copied
                parent.data = deleteNode(cur.data);
            }
        }
        
        return cur.data;
    }
}