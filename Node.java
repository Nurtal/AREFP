/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                          Nathan Foulquier                             */
/*                                                                        */
/*                       Wednesday 13th January 2016                        */
/*                                                                        */
/*                           	CERVVAL 		                          */
/*                                                                        */ 
/* ---------------------------------------------------------------------- */

import java.io.*;
import java.util.*;


public class Node{
    
    // ------------------- FIELDS ------------------------
    
    public String name;
  	public Integer count;
  	public Node parentLinks;
  	public ArrayList<String> nodesLinks;
    public ArrayList<Node> childLinks;

    
    // ---------------- CONSTRUCTORS ---------------------
    
    
	public Node(String name, ArrayList<String> nodesLink){

		this.count = 1;
		this.name = name;
		//this.parentLinks = parentLink;
		this.nodesLinks = nodesLink;
    this.childLinks = new ArrayList<Node>();

	}


    // ------------------ METHODS ------------------------
    public void increaseCount(){
    	this.count++;
    }

    public void setParent(Node node){
      this.parentLinks = node;
    }

}