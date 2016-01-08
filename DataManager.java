/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                           Nathan Foulquier                             */
/*                                                                        */
/*                       Tuesday 7th January 2016                         */
/*                                                                        */
/*                           	CERVVAL 		                          */
/*                                                                        */ 
/* ---------------------------------------------------------------------- */

import java.io.*;
import java.util.*;


public class DataManager {
    
    // ------------------- FIELDS ------------------------
    
    // None
    
    // ---------------- CONSTRUCTORS ---------------------
    
    
	public DataManager(){

	}


    
    // ------------------ METHODS ------------------------
    
    public void partitionProjection(String initialDatabase, ArrayList<String> orderListOfFrequentItems){
    	/*
		* Perform partition projection of an initial database
		* Initial database have to be order
		*
		* [APPROVED]
    	*/


		/*- Initialise Projected-Database files -*/
		for(String frequentItem : orderListOfFrequentItems){
			String projectedDatabaseFilename = "DATA/"+frequentItem+"_parttition_projected_database.data";
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
							String projectedDatabaseFilename = "DATA/"+item+"_parttition_projected_database.data";
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
			String projectedDatabaseFilename = "DATA/"+frequentItem+"_parralel_projected_database.data";
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
						
						if(item.equals(frequentItem)){
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

							/*- Write projection in file -*/
							String projectedDatabaseFilename = "DATA/"+item+"_parralel_projected_database.data";
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
		| -> Test if item is frequent
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





    public void mining(HashMap<String, Integer> fpTree){
    	/*
		* for mining FP-tree
		*
		* [IN PROGRESS]
    	*/


		if(fpTree.values().size() == 1){

			System.out.println("Tardis");

		}else{
			System.out.println(fpTree);
		}







    }







    //------------------ MAIN ---------------------------
    public static void main(String[] args) throws IOException {


    	ArrayList<String> items = new ArrayList<String>();
    	items.add("p");
    	items.add("m");
    	items.add("b");
    	items.add("a");
    	items.add("c");
    	items.add("f");

    	DataManager test = new DataManager();
    	test.partitionProjection("DATA/initialDB.data", items);
    	test.parralelProjection("DATA/initialDB.data", items);
    	HashMap<String,Integer> tree = test.buildFPtree("DATA/p_parralel_projected_database.data", 3);

    	System.out.println("=>"+tree+"<=");

    	test.mining(tree);


    }

}