/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                           Nathan Foulquier                             */
/*                                                                        */
/*                       		15/01/2016                         */
/*                                                                        */
/*                           	CERVVAL 		                          */
/*                                                                        */ 
/* ---------------------------------------------------------------------- */

import java.io.*;
import java.util.*;


public class DataConverter{


	// ------------------- FIELDS ------------------------

		// None

    
    // ---------------- CONSTRUCTORS ---------------------
    
    
	public DataConverter(){

	}


    // ------------------ METHODS ------------------------



	public static HashMap<Integer, String> initialiseTreshold(String parameterFile){
    	/*
		* Initialise Treshold for all parameters
		* from a csv file
		*
		* [USED]
    	*/

		HashMap<Integer, String> idForParameterToTreshold = new HashMap<Integer, String>();

		String line = null;
		try {
			FileReader fileReader = new FileReader(parameterFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((line = bufferedReader.readLine()) != null) {
				String[] lineInArray;
				lineInArray = line.split(";");
				int idForParameter = Integer.parseInt(lineInArray[0]);
				String treshold = lineInArray[2];
				idForParameterToTreshold.put(idForParameter, treshold);
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + parameterFile + "'");                
      	}
      	catch(IOException ex) {
      		ex.printStackTrace();
      	}

      	return idForParameterToTreshold;
    }





    public void convertToNumFormat(String inputFilename, String phase, String outputFile){
    	/*
		* Convert patient data file to .num format
		* Last Update : 06/01/2016
		*
		* TODO:
		*	-> Add for phase I & Inception data
		*
		* [IN PROGRESS]
    	*/

		HashMap<Integer, String> idForParameterToTreshold = new HashMap<Integer, String>();
		String patient = "";

		/*- Get Treshold for Parameter-*/
		if(phase.equals("II")){
			idForParameterToTreshold = initialiseTreshold("DATA/PARAMETERS/parameter_phase_II.csv");
		}

		int currentParameter = 1;
		String line = null;
		try {
			FileReader fileReader = new FileReader(inputFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((line = bufferedReader.readLine()) != null) {
				String[] lineInArray;
				lineInArray = line.split("\t");
				
				/*- Parsing File & Test Treshold (discretization)
				* 	-> 1: absent 
				*	-> 2: low
				*	-> 3: normal
				*	-> 4: high
				* Each parameter is represented by a unique int = {1,2,3,4} - (id_of_parameter * 4) 
				-*/
				if(lineInArray[2].equals("PROPORTION")){
					String valueInString = lineInArray[4].substring(0, lineInArray[4].length()-1);
					float valueToTest = Float.parseFloat(valueInString);
					float treshold = Float.parseFloat(idForParameterToTreshold.get(currentParameter));
					
					int discreteValueOfParameter;
					if(valueToTest == 0.0){
						discreteValueOfParameter = (currentParameter * 4) - 3;
					}else if(valueToTest < treshold){
						discreteValueOfParameter = (currentParameter * 4) - 2;
					}else if(valueToTest > treshold){
						discreteValueOfParameter = 3 + currentParameter;
						discreteValueOfParameter = (currentParameter * 4);
					}else{
						discreteValueOfParameter = (currentParameter * 4) - 1;
					}
					patient += discreteValueOfParameter+" ";
					currentParameter++;

				}else if(lineInArray[2].equals("ABSOLUTE")){
					int valueToTest = Integer.parseInt(lineInArray[4]);
					int treshold = Integer.parseInt(idForParameterToTreshold.get(currentParameter));

					int discreteValueOfParameter;
					if(valueToTest == 0.0){
						discreteValueOfParameter = (currentParameter * 4) - 3;
					}else if(valueToTest < treshold){
						discreteValueOfParameter = (currentParameter * 4) - 2;
					}else if(valueToTest > treshold){
						discreteValueOfParameter = 3 + currentParameter;
						discreteValueOfParameter = (currentParameter * 4);
					}else{
						discreteValueOfParameter = (currentParameter * 4) - 1;
					}
					patient += discreteValueOfParameter+" ";
					currentParameter++;
					
				}else if(lineInArray[2].equals("MFI")){
					float valueToTest = Float.parseFloat(lineInArray[4]);
					float treshold = Float.parseFloat(idForParameterToTreshold.get(currentParameter));
					
					int discreteValueOfParameter;
					if(valueToTest == 0.0){
						discreteValueOfParameter = (currentParameter * 4) - 3;
					}else if(valueToTest < treshold){
						discreteValueOfParameter = (currentParameter * 4) - 2;
					}else if(valueToTest > treshold){
						discreteValueOfParameter = 3 + currentParameter;
						discreteValueOfParameter = (currentParameter * 4);
					}else{
						discreteValueOfParameter = (currentParameter * 4) - 1;
					}
					patient += discreteValueOfParameter+" ";
					currentParameter++;

				}else if(lineInArray[2].equals("TYPE")){
					// Header, do nothing
				}else if(lineInArray[2].equals("RATIO")){
					float valueToTest = Float.parseFloat(lineInArray[4]);
					float treshold = Float.parseFloat(idForParameterToTreshold.get(currentParameter));
					
					int discreteValueOfParameter;
					if(valueToTest == 0.0){
						discreteValueOfParameter = (currentParameter * 4) - 3;
					}else if(valueToTest < treshold){
						discreteValueOfParameter = (currentParameter * 4) - 2;
					}else if(valueToTest > treshold){
						discreteValueOfParameter = 3 + currentParameter;
						discreteValueOfParameter = (currentParameter * 4);
					}else{
						discreteValueOfParameter = (currentParameter * 4) - 1;
					}
					patient += discreteValueOfParameter+" ";
					currentParameter++;

				}else{
					System.out.println("Fuck Biology ... : '" + lineInArray[2] +"' Has nothing to do there, please check line : \n" + line);
				}				
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + inputFilename + "'");                
      	}
      	catch(IOException ex) {
      		ex.printStackTrace();
      	}

      	/*-Write Output-*/
      	try{
    		FileWriter fw = new FileWriter (outputFile);
    		patient = patient.substring(0, patient.length()-1);
       	 	fw.write(patient);
    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}
    }















    public void assembleCohorte(List<String> listOfPatients, String cohorteFilename){
    	/*
		* Concat all files in listOfPatients into
		* one cohorte file, assume 1 patient = 1 line.
		* 
		*
		* [IN PROGRESS]
    	*/


		/*- Initialise cohorte file -*/
		try{
    		FileWriter fw = new FileWriter (cohorteFilename);
    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}

		/*- Loop over patient files -*/
		for(String patientFilename : listOfPatients){
			String patient = null;
			String line = null;
			try {
				FileReader fileReader = new FileReader(patientFilename);
				BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
				while((line = bufferedReader.readLine()) != null) {
					patient = line;
				}
				bufferedReader.close();         
			}
			catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + patientFilename + "'");                
      		}
      		catch(IOException ex) {
      			ex.printStackTrace();
      		}

      		/*- Add in Cohorte file -*/
      		try{
    		FileWriter fw = new FileWriter (cohorteFilename, true);
       	 		fw.write(patient+"\n");
    			fw.close();
			}catch (IOException exception){
    			System.out.println ("Error : " + exception.getMessage());
			}
		}
    }










    public void enumerate(String cohorteFilename, String cohorteFileToMine){
    	/*
    	* Enumerate data in cohorteFilename
    	*
		*
		*
    	* [IN PROGRESS]
    	*/


    	/*- Initialise output file -*/
    	try{
    		FileWriter fw = new FileWriter (cohorteFileToMine);
    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}

		try{
    		FileWriter fw = new FileWriter ("DATA/PARAMETERS/enumerate_hashmap.tmp");
    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}


    	/*- Initialise enumeration to discrete value HashMap -*/
    	HashMap<Integer, Integer> enumerationToDiscreteParmaterValue = new HashMap<Integer, Integer>();
    	String patient = null;
    	Integer enumerator = 1;
    	try {
			FileReader fileReader = new FileReader(cohorteFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((patient = bufferedReader.readLine()) != null) {
				String patientInArray[] = patient.split(" ");
				for(String discreteValueOfParameterInString : patientInArray){

					Integer discreteValueOfParameter = Integer.parseInt(discreteValueOfParameterInString);

					if(!enumerationToDiscreteParmaterValue.keySet().contains(discreteValueOfParameter)){
						enumerationToDiscreteParmaterValue.put(discreteValueOfParameter, enumerator);


						try{
    						FileWriter fw = new FileWriter ("DATA/PARAMETERS/enumerate_hashmap.tmp", true);
       	 					fw.write(enumerator.toString()+ ";" + discreteValueOfParameter.toString()+ "\n");
    						fw.close();
						}catch (IOException exception){
    						System.out.println ("Error : " + exception.getMessage());
						}


						enumerator++;
					}
				}
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
		System.out.println("Unable to open file '" + cohorteFilename + "'");                
      	}
      	catch(IOException ex) {
      		ex.printStackTrace();
      	}


      	/*- Write new Cohorte file -*/
      	String line = null;
    	try {
			FileReader fileReader = new FileReader(cohorteFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
			while((line = bufferedReader.readLine()) != null) {
				String lineInArray[] = line.split(" ");
				String newLine = "";
				for(String discreteValueOfParameterInString : lineInArray){
					newLine+=enumerationToDiscreteParmaterValue.get(Integer.parseInt(discreteValueOfParameterInString))+" ";
				}
				newLine = newLine.substring(0, newLine.length()-1);

				String[] newLineInArray = newLine.split(" ");
				ArrayList<Integer> newLineInInt = new ArrayList<Integer>();
				for(String itemInString : newLineInArray){
					int itemInInt = Integer.parseInt(itemInString);
					newLineInInt.add(itemInInt);
				}
				Collections.sort(newLineInInt);
				newLine = "";
				for(Integer itemInInt : newLineInInt){
					String itemInString = itemInInt.toString();
					newLine+=itemInString+" ";
				}
				newLine = newLine.substring(0, newLine.length()-1);

				try{
    				FileWriter fw = new FileWriter (cohorteFileToMine, true);
       	 			fw.write(newLine+"\n");
    				fw.close();
				}catch (IOException exception){
    				System.out.println ("Error : " + exception.getMessage());
				}

			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
		System.out.println("Unable to open file '" + cohorteFilename + "'");                
      	}
      	catch(IOException ex) {
      		ex.printStackTrace();
      	}
    }







	public void convertPatientFile(){
		/*
		* Convert a patient file to a data file
		* that can be mined using FP-tree.
		*
		* => [ALGO]:
		*	-> open file, read lines
		*		-> if line is a parameter:
		*			-> identify type of parameter (i.e PROPORTION, ABSOLUTE, MFI, RATIO)
		*			-> identify value of parameter
		*			-> Create a discrete item
		*			-> Write item in result file
		*	-> close file
		*
		*
		* [IN PROGRESS]
		*/







	}





}