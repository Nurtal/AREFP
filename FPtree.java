/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                          Nathan Foulquier                             */
/*                                                                        */
/*                       Wednesday 13th January 2016                         */
/*                                                                        */
/*                                                                        */ 
/* ---------------------------------------------------------------------- */

import java.io.*;
import java.util.*;


public class FPtree{
    
    // ------------------- FIELDS ------------------------
    
  	//public ArrayList<Node> nodes;
  	public HashSet<Node> nodes;
  	//public ArrayList<String> headerTable;
  	public HashMap<String, Integer> headerTable;
  	public ArrayList<Node> branch;

    
    // ---------------- CONSTRUCTORS ---------------------
    
    
	public FPtree(HashSet<Node> setOfNodes, HashMap<String, Integer> itemToSupport){

		this.nodes = setOfNodes;
		this.headerTable = itemToSupport;
		this.branch = new ArrayList<Node>();
	}


    // ------------------ METHODS ------------------------

    public boolean containsASinglePrefixPath(){
    	/*
		* Scan the size of list of child
		* for each node in the FPtree, if
		* one of them contain more than 1 
		* child the FP-tree does not contain
		* a "single prefix path"
		*
		* [SEEMS TO BE OK - NOT TESTED ON SINGLE-PATH-PREFIX]
    	*/

		boolean returnValue = true;
    	for(Node node : this.nodes){
    		if(node.childLinks.size() > 1){
    			returnValue = false;
    			break;
    		}
    	}
    	return returnValue;
    }


    public void getPath(Node node){
    	/*
		* get the differents paths
		* of an FP-tree, containing item
		*
		*
		* [APPROVED]
    	*/

		//System.out.println(node.name + " => " + node.parentLinks.name);
		this.branch.add(node);
		Node parent = node.parentLinks;

		if(parent.name.equals("root")){
			//reach the root
		}else{
			getPath(parent);
		}						
    }


    public HashSet<ArrayList<Node>> getAllPath(ArrayList<String> pattern){
    	/*
		* Get all path from conteing item item
		* from a FP-tree
		*
		* => [ALGO]:
		*	-> Loop over nodes of the tree:
		*		-> if name of the node is in pattern :
		*			-> Construct path (i.e branch)
		*			-> for nodes in the branch:
		*				-> if all item in pattern are in nodes.name:
		*					-> Save Branch (i.e path)
		*
		*
		* [APPROVED]
    	*/

		HashSet<ArrayList<Node>> setOfBranch = new HashSet<ArrayList<Node>>();

		for(Node node : this.nodes){
			if(pattern.contains(node.name)){
				this.branch = new ArrayList<Node>();
				getPath(node);
				ArrayList<String> listOfNodeNameInBranch = new ArrayList<String>();
				boolean branchCanBeSave = true;
				String test = "true";
				for(Node nodeInBranch : this.branch){
					listOfNodeNameInBranch.add(nodeInBranch.name);
				}


				/*
				| This part is supposed to check
				| if the branch contain all item
				| in pattern.
				|
				| -> All elements can't be found because The tree currently mined
				|	 is a conditional Tree, so first element of patterns have already
				|	 been found, focus on the last element of pattern [TESTING]
				|
				*/


				String itemToRetrieve = pattern.get(0);

				if(!(listOfNodeNameInBranch.contains(itemToRetrieve))){
					branchCanBeSave = false;
				}

				//System.out.println(pattern +" => "+ itemToRetrieve +"\t"+ listOfNodeNameInBranch);


				if(branchCanBeSave){
					setOfBranch.add(this.branch);
				}
			}
		}

		return setOfBranch;
    }





}