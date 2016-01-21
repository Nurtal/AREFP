/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                           Nathan Foulquier                             */
/*                                                                        */
/*                       Tuesday 7th January 2016                         */
/*                                                                        */
/*                                                                        */ 
/* ---------------------------------------------------------------------- */

import java.io.*;
import java.util.*;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.*;


public class DataManager{
    
    // ------------------- FIELDS ------------------------
    
    HashSet<Node> forFpTreeConstruction;
    
    // ---------------- CONSTRUCTORS ---------------------
    
    
	public DataManager(){

		forFpTreeConstruction = new HashSet<Node>();

	}


    
    // ------------------ METHODS ------------------------
    
    public void partitionProjection2(String initialDatabase, ArrayList<String> orderListOfFrequentItems){
    	/*
		* Perform partition projection of an initial database
		* Initial database have to be order
		*
		* Seems to  bug 
		*
		* [OBSOLETE]
    	*/


		/*- Initialise Projected-Database files -*/
		for(String frequentItem : orderListOfFrequentItems){
			String projectedDatabaseFilename = "DATA/PROJECTED_DATABASE/"+frequentItem+"_partition_projected_database.data";
			try{
    			FileWriter fw = new FileWriter (projectedDatabaseFilename);
    			fw.close();
			}catch (IOException exception){
    			System.out.println ("Error : " + exception.getMessage());
			}
		}


		/*- Partitionnement -*/
		/*
		| -> Pour chaque item dans las liste ordonnes des items frequents:
		| -> Parcourir les patients dans la database initial
		| -> Pour chaque patient parser les items du patient, les stocker dans une liste
		| -> Reverse la liste des items du patients, de maniere a iterer sur les moins frequents
		| 	 en premier (la database initiale est supposee etre ordonner)
		| -> Si item frequent courant est reconnu dans les items du patient:
		| 		=> Determiner les items du patient a partitioner (i.e ce qu'il reste a parcourir dans la liste)
		|		=> Ouvrir le fichier de la parttition-projected-DB correspondant a l'item frequent actuel
		|		=> Ecrire le resultat dans le fichier
		|		=> Retirer le patient de la liste des patients dispo
		| -> Sinon : 
		|		=> Passer au patient suivant
		*/

		ArrayList<Integer> idPatientProjected = new ArrayList<Integer>();
		for(String frequentItem : orderListOfFrequentItems){
			Integer currentIdPatient = 0; 
			String patient = null;
			try {
				FileReader fileReader = new FileReader(initialDatabase);
				BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
				while((patient = bufferedReader.readLine()) != null) {
					String[] patientInArray = patient.split("");
				
					// reverse the list
					for (int i = 0; i < patientInArray.length / 2; i++) {
            			String temp = patientInArray[i]; // swap numbers
            			patientInArray[i] = patientInArray[patientInArray.length - 1 - i];
            			patientInArray[patientInArray.length - 1 - i] = temp;

        			}

        			// loop over reverse list
        			int indexOfItemInPatient = 0;
					for(String item : patientInArray){

						if(item.equals(frequentItem) && !(idPatientProjected.contains(currentIdPatient))){

							String[] toPartition = Arrays.copyOfRange(patientInArray, indexOfItemInPatient, patientInArray.length);
							String toPartitionInString = "";
							
							// Reverse list of item
							for (int i = 0; i < toPartition.length / 2; i++) {
            					String temp = toPartition[i]; // swap numbers
            					toPartition[i] = toPartition[toPartition.length - 1 - i];
            					toPartition[toPartition.length - 1 - i] = temp;
        					}

        					// Convert to String
        					for(String eltToAdd : toPartition){
								toPartitionInString+=eltToAdd;
							}


							// Delete last item (i.e current item)
							toPartitionInString = toPartitionInString.substring(0, toPartitionInString.length()-1);
							idPatientProjected.add(currentIdPatient);



							/*- Write projection in file -*/
							String projectedDatabaseFilename = "DATA/PROJECTED_DATABASE/"+item+"_partition_projected_database.data";
							try{
    							FileWriter fw = new FileWriter (projectedDatabaseFilename, true);
    							fw.write(toPartitionInString+"\n");
    							fw.close();
							}catch (IOException exception){
    							System.out.println ("Error : " + exception.getMessage());
							}

							// Break the loop 
							break;
						}
						indexOfItemInPatient++;
					}
					currentIdPatient++;
				}
				bufferedReader.close();         
			}
			catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + initialDatabase + "'");                
      		}
      		catch(IOException ex) {
      			ex.printStackTrace();
      		}
      	}
    }




    public void parralelProjection(String initialDatabase, ArrayList<String> orderListOfFrequentItems){
    	/*
		* Perform parralel projection of an initial database
		* Initial database have to be order
		*
		* [APPROVED]
    	*/


		/*- Initialise Projected-Database files -*/
		for(String frequentItem : orderListOfFrequentItems){
			String projectedDatabaseFilename = "DATA/PROJECTED_DATABASE/"+frequentItem+"_parralel_projected_database.data";
			try{
    			FileWriter fw = new FileWriter (projectedDatabaseFilename);
    			fw.close();
			}catch (IOException exception){
    			System.out.println ("Error : " + exception.getMessage());
			}
		}


		/*- Partitionnement -*/
		/*
		| -> Pour chaque item dans las liste ordonnes des items frequents:
		| -> Parcourir les patients dans la database initial
		| -> Pour chaque patient parser les items du patient, les stocker dans une liste
		| -> Reverse la liste des items du patients, de maniere a iterer sur les moins frequents
		| 	 en premier (la database initiale est supposee etre ordonner)
		| -> Si item frequent courant est reconnu dans les items du patient:
		| 		=> Determiner les items du patient a partitioner (i.e ce qu'il reste a parcourir dans la liste)
		|		=> Ouvrir le fichier de la parttition-projected-DB correspondant a l'item frequent actuel
		|		=> Ecrire le resultat dans le fichier
		| -> Sinon : 
		|		=> Passer au patient suivant
		*/


		for(String frequentItem : orderListOfFrequentItems){
			Integer currentIdPatient = 0; 
			String patient = null;
			try {
				FileReader fileReader = new FileReader(initialDatabase);
				BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
				while((patient = bufferedReader.readLine()) != null) {
					String[] patientInArray = patient.split(" ");
				
					// reverse the list
					for (int i = 0; i < patientInArray.length / 2; i++) {
            			String temp = patientInArray[i]; // swap numbers
            			patientInArray[i] = patientInArray[patientInArray.length - 1 - i];
            			patientInArray[patientInArray.length - 1 - i] = temp;
        			}

        			// loop over reverse list
        			int indexOfItemInPatient = 0;
					for(String item : patientInArray){
						
						if(item.equals(frequentItem)){
							String[] toPartition = Arrays.copyOfRange(patientInArray, indexOfItemInPatient, patientInArray.length);
							String toPartitionInString = "";
							
							// Reverse list of item
							for (int i = 0; i < toPartition.length / 2; i++) {
            					String temp = toPartition[i]; // swap numbers
            					toPartition[i] = toPartition[toPartition.length - 1 - i];
            					toPartition[toPartition.length - 1 - i] = temp;
        					}



        					// Delete last item (i.e current item)
        					ArrayList<String> toPartition2 = new ArrayList<String>(Arrays.asList(toPartition));
        					toPartition2.remove(toPartition2.size() - 1);

        					// Convert to String
        					for(String eltToAdd : toPartition2){
								toPartitionInString+=eltToAdd+" ";
							}

							// Delete last item (i.e current item)
							//toPartitionInString = toPartitionInString.substring(0, toPartitionInString.length()-1);

							/*- Write projection in file -*/
							String projectedDatabaseFilename = "DATA/PROJECTED_DATABASE/"+item+"_parralel_projected_database.data";
							try{
    							FileWriter fw = new FileWriter (projectedDatabaseFilename, true);
    							fw.write(toPartitionInString+"\n");
    							fw.close();
							}catch (IOException exception){
    							System.out.println ("Error : " + exception.getMessage());
							}

							// Break the loop 
							break;
						}
						indexOfItemInPatient++;
					}
					currentIdPatient++;
				}
				bufferedReader.close();         
			}
			catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + initialDatabase + "'");                
      		}
      		catch(IOException ex) {
      			ex.printStackTrace();
      		}
      	}
    }




    public HashMap<String, Integer> buildFPtree(String databaseFilename, Integer supportTreshold){
    	/*
		* build FPtree from database
		* return hasmap <node, support>, unsorted
		*
		* homeMade implementation
		*
		* [SEEMS TO BE OK]
    	*/

		HashMap<String, Integer> itemToSupport = new HashMap<String, Integer>();

		/*
		| Determinate frequence of items
		*/
		String patient = null;
		try {
			FileReader fileReader = new FileReader(databaseFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((patient = bufferedReader.readLine()) != null) {
				String[] patientInArray = patient.split("");
				for(String item : patientInArray){

					if(!item.equals("")){
						if(itemToSupport.keySet().contains(item)){
							Integer support = itemToSupport.get(item);
							support++;
							itemToSupport.put(item, support);
						}else{
							Integer support = 1;
							itemToSupport.put(item, support);
						}
					}
				}
			}
		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + databaseFilename + "'");                
      	}catch(IOException ex) {
      		ex.printStackTrace();
      	}
      	//System.out.println(itemToSupport);


      	/*
		| -> Test if item is frequent (i.e if frequence >= treshold)
      	*/
		ArrayList<String> itemToRemove = new ArrayList<String>();
		for(String item : itemToSupport.keySet()){
			if(itemToSupport.get(item) < supportTreshold){
				itemToRemove.add(item);
			}


		}
		for(String key : itemToRemove){
			itemToSupport.remove(key);
		}

		return itemToSupport;
    }




    public ArrayList<String> sortHashMapByValues(HashMap<String, Integer> passedMap) {
    	/*
		* Sort HashMap<String, Integer> according to values
		* Return list of sorted items (keys)
		* [APPROVED]
    	*/
	
		HashMap<String, Integer> orderHashMap = new HashMap<String, Integer>();
		ArrayList<String> orderItems = new ArrayList<String>();
		ArrayList<String> mapKeys = new ArrayList<String>(passedMap.keySet());
		ArrayList<Integer> mapValues = new ArrayList<Integer>(passedMap.values());

		Collections.sort(mapValues);
		Collections.reverse(mapValues);

		ArrayList<String> removedElement = new ArrayList<String>();
		for(Integer orderSupport: mapValues){
			for(String item : mapKeys){
				Integer unOrderSupport = passedMap.get(item);
				if(orderSupport == unOrderSupport && !(removedElement.contains(item))){
					orderItems.add(item);
					removedElement.add(item);
				}
			}
		}
		return orderItems;
	}


	public void insertTree(ArrayList<String> frequentItemForPatient, Node t){
		/*
		* Insert tree function, part of the 
		* FP-tree construction process
		*
		*
		* [APPROVED]
		*/

		/* 
		| -> if t has a child n such n.name = p.name
		| where p is the first item of frequentItemForPatient:
		| 	-> n.count++
		| -> Else:
		|	-> create new node n 
		| TODO : complete commentary
		*/
		ArrayList<String> emptyList = new ArrayList<String>();
		Node n = new Node("default", emptyList);

		if(!(frequentItemForPatient.isEmpty())){
			String p = frequentItemForPatient.get(0);

			ArrayList<Node> childNodeToAdd = new ArrayList<Node>();
			for(Node child : t.childLinks){
				if(child.name.equals(p)){
					child.count++;

					//System.out.println(frequentItemForPatient + "|| [ name : " + child.name + " $$ parent: " + child.parentLinks+" ] => Increased");

					frequentItemForPatient.remove(p);
					if(!(frequentItemForPatient.isEmpty())){
						insertTree(frequentItemForPatient, child);
					}

				}else{

					if(!(frequentItemForPatient.isEmpty())){
						n = new Node(p, emptyList);
						n.setParent(t);
						childNodeToAdd.add(n);
						//System.out.println(frequentItemForPatient + "|| [ name : " + n.name + " $$ parent: " + n.parentLinks+" ] => Created 1");

						//Save node for FP-tree construction
						this.forFpTreeConstruction.add(n);
					}

			
					frequentItemForPatient.remove(p);
					if(!(frequentItemForPatient.isEmpty())){
						insertTree(frequentItemForPatient, n);
					}
				}
			}
			for(Node child: childNodeToAdd){
				t.childLinks.add(child);
			}

			if(t.childLinks.isEmpty()){
				ArrayList<String> empty = new ArrayList<String>();
				n = new Node(p, emptyList);
				n.setParent(t);
				t.childLinks.add(n);

				//System.out.println(frequentItemForPatient + "|| [ name : " + n.name + " $$ parent: " + n.parentLinks+" ] => Created 2");
			
				//Save node for FP-tree construction
				this.forFpTreeConstruction.add(n);

				frequentItemForPatient.remove(p);
				if(!(frequentItemForPatient.isEmpty())){
					insertTree(frequentItemForPatient, n);
				}
			}
		}
	}






    public FPtree fpTreeConstruction(String dbfilename, int supportTreshold){
    	/*
    	* Build a FP-tree,
		* Implementation from publication.
		*
		* [APPROVED]
    	*/

    	/* [1] Scan the databasee
		| -> Scan the database, collect the set of frequent itemset
		| and the support of each frequent item.
		| -> Sort the set of frequent item in support-descendong order,
		| store it in a list.
		*/
    	HashMap<String, Integer> itemToSupport = new HashMap<String, Integer>();
    	ArrayList<String> lisOfPatients = new ArrayList<String>();
    	String patient = null;
		try {
			FileReader fileReader = new FileReader(dbfilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((patient = bufferedReader.readLine()) != null) {
				lisOfPatients.add(patient);
				String[] patientInArray = patient.split(" ");
				for(String item : patientInArray){
					if(!item.equals("")){
						if(itemToSupport.keySet().contains(item)){
							Integer support = itemToSupport.get(item);
							support++;
							itemToSupport.put(item, support);
						}else{
							Integer support = 1;
							itemToSupport.put(item, support);
						}
					}
				}
			}
		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + dbfilename + "'");                
      	}catch(IOException ex) {
      		ex.printStackTrace();
      	}

      	// Test if item is frequent & Create 
      	ArrayList<String> itemToRemove = new ArrayList<String>();
		for(String item : itemToSupport.keySet()){
			if(itemToSupport.get(item) < supportTreshold){
				itemToRemove.add(item);
			}
		}
		for(String key : itemToRemove){
			itemToSupport.remove(key);
		}


		/*Sort frequent item in support-descending order and store
		| results in a list (there, fList) 
		*/
		ArrayList<String> fList = sortHashMapByValues(itemToSupport);



    	/* [2] Build FPtree
		| -> Create the root of an FP-tree label it as null.
		| -> for each patient in database:
		|		-> select the frequent item in patient and sort them
		|		   according to the sorted-frequent-items list
		|		-> insert_tree(sorted frequent item-list)
		|
		*/


		//Create root of FP-tree
		this.forFpTreeConstruction = new HashSet<Node>();
		ArrayList<Node> fpTree = new ArrayList<Node>();
		ArrayList<String> empty = new ArrayList<String>();
		Node root = new Node("root", empty);
		fpTree.add(root);

		for(String pat : lisOfPatients){
			String[] patientInArray = pat.split(" ");
			ArrayList<String> frequentItemForPatient = new ArrayList<String>();
			for(String frequentItem: fList){
				for(String item: patientInArray){
					if(frequentItem.equals(item)){
						frequentItemForPatient.add(item);
					}
				}
			}
			insertTree(frequentItemForPatient, root);
		}

		// Save FP-tree in an object
		FPtree constructFpTree = new FPtree(this.forFpTreeConstruction, itemToSupport);
		this.forFpTreeConstruction = new HashSet<Node>();

		return constructFpTree;
    }



    



    public ArrayList<ArrayList<String>> getFrequentPatternFromConditionalFPtree(HashMap<String,Integer> condtionalFPtree, String item){
    	/*
		* Generate combinaison from FP-tree
		* with corresponfing item
		*
		* -> HomeMade, work on exemple data
		*
		* [APPROVED]
    	*/

		ArrayList<ArrayList<String>> listOfPattern = new ArrayList<ArrayList<String>>();
		ArrayList<String> itemForCombination = new ArrayList<String>();
		itemForCombination.add(item);

		for(String key : condtionalFPtree.keySet()){
			itemForCombination.add(key);
		}

		String[] listOfelements = itemForCombination.toArray(new String[0]);
		int[] indices;		
		int startK = 2;
		int k = listOfelements.length;

		while(k - startK >= 0){

			CombinationGenerator x = new CombinationGenerator (listOfelements.length, k);
			ArrayList<String> combination;
			while (x.hasMore ()) {
				combination = new ArrayList<String> ();
				indices = x.getNext ();
				for (int i = 0; i < indices.length; i++) {
					combination.add(listOfelements[indices[i]]);
				}

				if(combination.contains(item)){
					listOfPattern.add(combination);	
				}			
			}
			k--;
		}
		return listOfPattern;
    }





    public void recursiveMiningProcess(FPtree tree, ArrayList<String> itemInTree, Integer treeshold){
    	/*
		* Mining FP-tree, implementation from publication
		* -> Unlike the frequentPatternGrowyh method, this method
		*    is based only on recursivity mining, there is no
		*	 different strategy for single prefix-path FP-tree.
		*
		* -> Implementation mostly for debugging, supposed to be less
		* 	 efficient than frequentPatternGrowth method.
    	*/


		ArrayList<String> table = sortHashMapByValues(tree.headerTable);
		Collections.reverse(table);

		for(String item : table){

			// Generate pattern beta
			ArrayList<String> pattern = new	ArrayList<String>();
			pattern.add(item);
			for(String element : itemInTree){
				if(!(element.equals(""))){
					pattern.add(element);
				}
			}
			String patternInString = "";
			for(String element : pattern){
				patternInString += element;
			}
				
			//Construct Beta conditional pattern-base
			createConditionalDB(tree, pattern);
			String conditionalDatabaseFilename = "DATA/CONDITIONAL_DATABASE/"+patternInString+"_conditionalDB.data";

			//construct Beta conditional FP-tree
			FPtree conditionalFPtree = fpTreeConstruction(conditionalDatabaseFilename, treeshold);

			if(!(conditionalFPtree.nodes.isEmpty())){
				recursiveMiningProcess(conditionalFPtree, pattern, treeshold);
			}
		}
    }









    public void frequentPatternGrowth(FPtree tree, ArrayList<String> itemInTree, Integer treeshold){
    	/*
		*
		* Mining FP-tree, according to publication
		*
		*
		* 	
		* [APPROVED]
    	*/

		ArrayList<String> table = sortHashMapByValues(tree.headerTable);
		Collections.reverse(table);

		/* 
		| Mining simple prefix - path FP-tree 
		| -> Generate Combination
		| -> Write Results in a Result File
		|
		| TODO : more documentation on this part
		|
		*/
		if(tree.containsASinglePrefixPath()){

			//System.out.println("=> Ready To Be Mined "+itemInTree+" <=");

			HashSet<ArrayList<String>> setOfFrequentPattern = new HashSet<ArrayList<String>>();
			ArrayList<String> itemForCombination = new ArrayList<String>();

			for(String element : itemInTree){
				itemForCombination.add(element);
			}
			



			for(Node node : tree.nodes){

				//No need to control frequency, according to Lemma 3.2
				/*
				if(node.count >= treeshold){ 
					itemForCombination.add(node.name);
				}
				*/
				itemForCombination.add(node.name);
			}





			String[] listOfelements = itemForCombination.toArray(new String[0]);
			int[] indices;		
			int startK = 1;
			int k = listOfelements.length;

			while(k - startK >= 0){
				CombinationGenerator x = new CombinationGenerator (listOfelements.length, k);
				ArrayList<String> combination;
				while (x.hasMore ()) {
					boolean combinationCanBeSave = true;
					combination = new ArrayList<String> ();
					indices = x.getNext ();
					for (int i = 0; i < indices.length; i++) {
						combination.add(listOfelements[indices[i]]);
					}

					for(String element : itemInTree){
						if(!(combination.contains(element))){
							combinationCanBeSave = false;
						}
					}

					if(combinationCanBeSave){
						setOfFrequentPattern.add(combination);
					}
				}
				k--;
			}

			//Writing Results
			String itemInTreeInString = "";
			for(String element : itemInTree){
				itemInTreeInString += element;
			}
			String resultFileName = "DATA/RESULTS/"+itemInTreeInString+"_frequentPattern.dat";

			try{
				FileWriter fw = new FileWriter(resultFileName);

				for (Iterator<ArrayList<String>> it = setOfFrequentPattern.iterator(); it.hasNext(); ) {
        			ArrayList<String> frequentPattern = it.next();
        			for(String itemInFrequentPattern : frequentPattern){
        				fw.write(itemInFrequentPattern+" ");

        			}
					fw.write("\n");        	
        		}

				fw.close();

			}catch(IOException exception){
				System.out.println ("Error : " + exception.getMessage());
			}


		}
		/*
		| Mining multipath FP-tree
		| -> for each item a in header table of tree:
		|		-> Generate pattern beta
		|		-> Construct Beta conditional pattern-base
		|		-> Construct conditional FP-tree for pattern
		|		-> if conditional FP-tree is not empty:
		|			-> call the function again
		|
		*/
		else{
			for(String item : table){

				// Generate pattern beta
				ArrayList<String> pattern = new	ArrayList<String>();
				pattern.add(item);
				for(String element : itemInTree){
					if(!(element.equals(""))){
						pattern.add(element);
					}
				}
				String patternInString = "";
				for(String element : pattern){
					patternInString += element;
				}
				
				//Construct Beta conditional pattern-base
				createConditionalDB(tree, pattern);
				String conditionalDatabaseFilename = "DATA/CONDITIONAL_DATABASE/"+patternInString+"_conditionalDB.data";

				//construct Beta conditional FP-tree
				FPtree conditionalFPtree = fpTreeConstruction(conditionalDatabaseFilename, treeshold);

				if(!(conditionalFPtree.nodes.isEmpty())){
					frequentPatternGrowth(conditionalFPtree, pattern, treeshold);
				}
			}
		}
    }










    public void createConditionalDB(FPtree tree, ArrayList<String> pattern){
    	/*
		* Construct conditional Database for a pattern
		* in a FP-tree
		*
		* => [ALGO]:
		*	-> Get All Paths in the tree where pattern is present
		*
		*
		*
		* TODO : more documentation
		*
		* [APPROVED]
    	*/



		/*
		| -> Create a String representation for pattern
		| -> Generating the set of branch (i.e Paths) containing
		| 	 pattern.
		| -> Prepare Conditional Database File.
		*/

		String patternInString = "";
		for(String itemInPattern : pattern){
			patternInString += itemInPattern;
		}
		ArrayList<HashMap<String, Integer>> listOfItemToSupport = new ArrayList<HashMap<String, Integer>>();
    	HashSet<ArrayList<Node>> setOfBranch = tree.getAllPath(pattern);
    	String conditionalDBFilename = "DATA/CONDITIONAL_DATABASE/"+patternInString+"_conditionalDB.data";

    	// Prepare Conditional DataBase File
    	try{
    		FileWriter fw = new FileWriter (conditionalDBFilename);
    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}



		/*
		| -> Loop over the branch containing pattern
		| -> Determine max occurence (i.e frequence of the less frequent item in pattern)
		*/

    	for (Iterator<ArrayList<Node>> it = setOfBranch.iterator(); it.hasNext(); ) {
        	ArrayList<Node> branch = it.next();
        	HashMap<String, Integer> itemToSupport = new HashMap<String, Integer>();
        	
        	//System.out.println("Tardis" + "\t[=> "+patternInString+ " <=]");

        	Integer maxOccurence = 0;
        	ArrayList<Integer> listOfItemOccurence = new ArrayList<Integer>();
        	for(Node node : branch){

        		for(String itemInPattern : pattern){
        			if(node.name.equals(itemInPattern)){
        				listOfItemOccurence.add(node.count);
        			}
        		}
        	}
        	maxOccurence = Collections.min(listOfItemOccurence);

        	for(Node node : branch){

        		if(node.count <= maxOccurence){
        			itemToSupport.put(node.name, node.count);
        		}else{
        			itemToSupport.put(node.name, maxOccurence);
        		}
        	}

        	for(String itemInPattern : pattern){
        		itemToSupport.remove(itemInPattern);
        	}
        	
        	listOfItemToSupport.add(itemToSupport);        	
    	}

    	//System.out.println(listOfItemToSupport + "\t[=> "+patternInString+ " <=]");


    	/*
		| Write Results in a conditional database file
    	*/

    	try{
    		FileWriter fw = new FileWriter (conditionalDBFilename);

    		for(HashMap<String, Integer> itemToSupport : listOfItemToSupport){
    			String lineToWrite = "";
    			Integer compteur = 0;
    			for(String item : itemToSupport.keySet()){
    				lineToWrite+=item+" ";
    				compteur = itemToSupport.get(item);
    			}

    			while(compteur > 0){
    				fw.write(lineToWrite+"\n");
    				compteur--;
    			}
    		}

    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}
    }








    public void partitionProjection (String initialDatabase, ArrayList<String> orderListOfFrequentItems){
    	/*
		* perform a partition projection
		*
		* -> The initial Database File need to be sorted according to the
		*	 orderListOfFrequentItems.
		*
		* => [ALGO]:
		*	-> initialise item-projected-database
		*	-> reverse the list of frequent item	
		*	-> for each patient:
		*		-> reverse the list of item in patient
		* 		-> for each item in reverse list of frequent item:
		*			-> if patient contain item:
		*				-> create a subList from patient (patient[0]-item)
		*				-> remove non-frequent item in patient {Not Necessery if initialDatabase is sorted}
		*				-> remove used item in patient
		*				-> write (add) patient in item-projected-database
		* 
		*
		* [APPROVED]
    	*/


		/*

		// Initialise item-projected-database
		for(String item : orderListOfFrequentItems){

			String projectedDatabaseFilename = "DATA/PROJECTED_DATABASE/"+item+"_partition_projected_database.data";
			try{
    			FileWriter fw = new FileWriter (projectedDatabaseFilename);
    			fw.close();
			}catch (IOException exception){
    			System.out.println ("Error : " + exception.getMessage());
			}

		}

		*/


    	// reverse the list of frequent item
		Collections.reverse(orderListOfFrequentItems);

		String patient = null;
    	try {
			FileReader fileReader = new FileReader(initialDatabase);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.

			//Loop over Patient
			while((patient = bufferedReader.readLine()) != null) {
				String[] lineInArray = patient.split(" ");
				ArrayList<String> patientInArray = new ArrayList<String>(Arrays.asList(lineInArray));

				// reverse the list of item in patient
				Collections.reverse(patientInArray);

        		//Loop over item in frequent item list
        		for(String frequentItem : orderListOfFrequentItems){
        			if(patientInArray.contains(frequentItem)){

        				//System.out.println(initialDatabase+" => "+ patient + " [ "+ frequentItem + " ]");
        				
        				// Create a subList from patient
        				ArrayList<String> sublist = new ArrayList<String>();
						sublist = new ArrayList<String>(patientInArray.subList(patientInArray.indexOf(frequentItem), patientInArray.size()));

						//Remove used item in patient
						sublist.remove(frequentItem);

						/*- Write projection in file -
						| -> Convert sublist in String & Reverse sublist, conserve descending order of frequency
						| -> Write String in result file
						| -> Break the loop, ensure that a patient ( i.e line in initial database file)
						| -> is only projected once into a partition-projected database
						*/
						
						String lineToWrite = "";
						Collections.reverse(sublist);
						for(String element : sublist){
							lineToWrite+=element+" ";
						}

						//System.out.println(patient + " [ "+ lineToWrite + " ] => "+ frequentItem);

						String projectedDatabaseFilename = "DATA/PROJECTED_DATABASE/"+frequentItem+"_partition_projected_database.data";
						try{
    						FileWriter fw = new FileWriter (projectedDatabaseFilename, true);
    						fw.write(lineToWrite+"\n");
    						fw.close();
						}catch (IOException exception){
    						System.out.println ("Error : " + exception.getMessage());
						}
						
        				break;
        			}
        		}		
			}

		/*- Close initial-database File */
			
			bufferedReader.close();         
		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + initialDatabase + "'");                
      	}catch(IOException ex) {
      		ex.printStackTrace();
      	}

      	// put the order list in the initial order
		Collections.reverse(orderListOfFrequentItems);
    }











    public void initialiseProjectedDatabase(String typeOfProjection, ArrayList<String> orderListOfFrequentItems, String path){
    	/*
		* Initiliase projected database file, according to
		* type of projection and list of frequent items.
		* 
		* -> For now only useful before using a partition
		*    projection method
		*
		* -> typeOfProjection could be : "parralel" or "partition" 
		*
		* [IN PROGRESS]
    	*/


		//Test if path exist, create it if not
		File folderToTest = new File(path);
		if (Files.notExists(folderToTest.toPath())) {
			new File(path).mkdirs();
		}


    	// Initialise item-projected-database
		for(String item : orderListOfFrequentItems){

			String projectedDatabaseFilename = path+item+"_"+typeOfProjection+"_projected_database.data";
			try{
    			FileWriter fw = new FileWriter (projectedDatabaseFilename);
    			fw.close();
			}catch (IOException exception){
    			System.out.println ("Error : " + exception.getMessage());
			}

		}


    }











    public ArrayList<String> getOrderListOfFrequentItem(String databaseFilename, Integer treshold){
    	/*
    	* Scan the database, return a list of frequent item,
    	* order in descending frequence value
    	*
    	*
    	* [APPROVED]
    	*/

    	/*
		| -> Scan the database, collect the set of frequent itemset
		| and the support of each frequent item.
		| -> Sort the set of frequent item in support-descendong order,
		| store it in a list.
		*/
    	HashMap<String, Integer> itemToSupport = new HashMap<String, Integer>();
    	ArrayList<String> lisOfPatients = new ArrayList<String>();
    	String patient = null;
		try {
			FileReader fileReader = new FileReader(databaseFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((patient = bufferedReader.readLine()) != null) {
				lisOfPatients.add(patient);
				String[] patientInArray = patient.split(" ");
				for(String item : patientInArray){
					if(!item.equals("")){
						if(itemToSupport.keySet().contains(item)){
							Integer support = itemToSupport.get(item);
							support++;
							itemToSupport.put(item, support);
						}else{
							Integer support = 1;
							itemToSupport.put(item, support);
						}
					}
				}
			}
		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + databaseFilename + "'");                
      	}catch(IOException ex) {
      		ex.printStackTrace();
      	}

      	// Test if item is frequent
      	ArrayList<String> itemToRemove = new ArrayList<String>();
		for(String item : itemToSupport.keySet()){
			if(itemToSupport.get(item) < treshold){
				itemToRemove.add(item);
			}
		}
		for(String key : itemToRemove){
			itemToSupport.remove(key);
		}


		/*Sort frequent item in support-descending order and store
		| results in a list (there, fList) 
		*/
		ArrayList<String> fList = sortHashMapByValues(itemToSupport);

		return fList;
    }





    public void reorderDatabase(String databaseFilename, ArrayList<String> orderListOfFrequentItems){
    	/*
    	* -> Reorder database according to the order list
    	*    of frequent items in descending order of frequency
    	* -> Write reordered database in a databaseFilename_sorted.data file 
    	*
    	*
    	* [APPROVED]
    	*/

    	String[] databaseFilenameInArray = databaseFilename.split(".");
    	String outputFileName = databaseFilename.substring(0, databaseFilename.length()-5);
    	outputFileName+="_sorted.data";

    	//Intialise output file (i.e sorted database file);
		try{
			FileWriter fw = new FileWriter(outputFileName);
			fw.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}


    	String patient = null;
    	try {
			FileReader fileReader = new FileReader(databaseFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.

			//Loop over Patient
			while((patient = bufferedReader.readLine()) != null) {
				String[] lineInArray = patient.split(" ");
				ArrayList<String> patientInArray = new ArrayList<String>(Arrays.asList(lineInArray));
				ArrayList<String> patientInArraySorted = new ArrayList<String>();

				//Reorder items in patient
				for(String orderedFrequentItem : orderListOfFrequentItems){
					if(patient.contains(orderedFrequentItem)){
						patientInArraySorted.add(orderedFrequentItem);
					}
				}

				//Write results
				String patientInArraySortedInString = "";
				for(String element : patientInArraySorted){
					patientInArraySortedInString+=element+" ";
				}

				try{
					FileWriter fw = new FileWriter(outputFileName, true);
					fw.write(patientInArraySortedInString+"\n");
					fw.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}				
			}

			bufferedReader.close();         
		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + databaseFilename + "'");                
      	}catch(IOException ex) {
      		ex.printStackTrace();
      	}
    }




    public void saveResults(String saveName){
    	/*
		* Save results files present in DATA/RESULTS
		* -> Create a directory in DATA/ALL_RESULTS
		* -> copy all files in DATA/RESULTS to created directory
		*
		*
		* [IN PROGRESS] ADAPAT TO NEW PARTITION METHOD
    	*/


		//test if directory already exists


		System.out.println(saveName);

		/*
		String[] saveFileInArray = saveName.split("/");
		ArrayList<String> saveFileInArrayList = new ArrayList<String>(Arrays.asList(saveFileInArray));
		String saveFolderPath = "";
		saveFileInArrayList.remove(saveFileInArrayList.size()-1);
		for(String element : saveFileInArrayList){
			saveFolderPath+=element+"/";
		}
		*/

		File saveFolder = new File(saveName);
		if(Files.notExists(saveFolder.toPath())){
			// Create directory
			saveFolder.mkdirs();
		}


	
		// List all files to save
		File folder = new File("DATA/RESULTS/");
		File[] listOfFiles = folder.listFiles();

		// Save files
    	for (int i = 0; i < listOfFiles.length; i++) {
      		if (listOfFiles[i].isFile()) {

        		File source = new File("DATA/RESULTS/"+listOfFiles[i].getName());
        		File dest = new File(saveName+"/"+listOfFiles[i].getName());

				try {
    				Files.copy(source.toPath(), dest.toPath());
				}catch (IOException e) {
   		 			e.printStackTrace();
				}
      		}
      	}
    }















    

}