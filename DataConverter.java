/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                           Nathan Foulquier                             */
/*                                                                        */
/*                       		15/01/2016                         */
/*                                                                        */
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
		* [APPROVED]
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







	public void convertPatientFile(String inputFilename, String phase, String outputFile){
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
		* [APPROVED]
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

				/*
				| Remove undesirable charachter in itemName
				*/
				String itemName = lineInArray[1];
				itemName = itemName.replaceAll("/", "_over_");

				
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
					itemName += "_PROPORTION_over_"+ lineInArray[3]; 

					if(valueToTest == 0.0){
						itemName +="_absent";
					}else if(valueToTest < treshold){
						itemName +="_low";
					}else if(valueToTest > treshold){
						itemName+="_high";
					}else{
						itemName+="_normal";
					}
					patient += itemName+" ";
					currentParameter++;


				}else if(lineInArray[2].equals("ABSOLUTE")){
					int valueToTest = Integer.parseInt(lineInArray[4]);
					int treshold = Integer.parseInt(idForParameterToTreshold.get(currentParameter));
					itemName += "_ABSOLUTE"; 
					
					if(valueToTest == 0.0){
						itemName +="_absent";
					}else if(valueToTest < treshold){
						itemName +="_low";
					}else if(valueToTest > treshold){
						itemName+="_high";
					}else{
						itemName+="_normal";
					}
					patient += itemName+" ";
					currentParameter++;

				}else if(lineInArray[2].equals("MFI")){
					float valueToTest = Float.parseFloat(lineInArray[4]);
					float treshold = Float.parseFloat(idForParameterToTreshold.get(currentParameter));
					itemName += "_MFI";

					if(valueToTest == 0.0){
						itemName +="_absent";
					}else if(valueToTest < treshold){
						itemName +="_low";
					}else if(valueToTest > treshold){
						itemName+="_high";
					}else{
						itemName+="_normal";
					}
					patient += itemName+" ";
					currentParameter++;

				}else if(lineInArray[2].equals("TYPE")){
					// Header, do nothing
				}else if(lineInArray[2].equals("RATIO")){
					float valueToTest = Float.parseFloat(lineInArray[4]);
					float treshold = Float.parseFloat(idForParameterToTreshold.get(currentParameter));
					itemName += "_RATIO";
				
					if(valueToTest == 0.0){
						itemName +="_absent";
					}else if(valueToTest < treshold){
						itemName +="_low";
					}else if(valueToTest > treshold){
						itemName+="_high";
					}else{
						itemName+="_normal";
					}
					patient += itemName+" ";
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












	public void toEnumeratedParameter(String patientFilename){
		/*
		* Convert csv patient file to a more
		* understanding mining format
		*
		* =>[ALGO]:
		*		-> Initialise converted patient file
		*		-> open patient file, for each parameter (i.e line) in file:
		*			-> attribute a simple id to parameter
		*			-> write converted file with the new parameter id
		*		-> Write correspondance table (parameter to id)
		*
		*
		*
		* [APPROVED]
		*/

		//Iinitialise output file
		String outputFilename = "not defined";
		String[] inputFilenameInArray = patientFilename.split(".dat");
		ArrayList<String> inputFilenameInArrayList = new ArrayList<String>(Arrays.asList(inputFilenameInArray));
		if(inputFilenameInArrayList.isEmpty()){
			System.out.println("[toEnumeratedParameter] ERROR => can't parse patient filename : "+ patientFilename);
		}else{
			outputFilename = inputFilenameInArrayList.get(0)+"_enumerated_parameters.data";
		}
		try{
			FileWriter fw = new FileWriter(outputFilename);
			fw.close();
		}catch(IOException exception){
			System.out.println("Error : " + exception.getMessage());
		}

		// Parsing Patient File
		HashMap<String, String> newToOldParameters = new HashMap<String, String>();
		try{
			String line = null;
			Integer newParameterCmpt = 1;
			FileReader fileReader = new FileReader(patientFilename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				String[] lineInArray;
				lineInArray = line.split("\t");
				String lineToWrite = "not defined";

				// Check for first column to parse
				if(!(lineInArray[1].equals("POPULATION"))){
					String newParameter = "p"+newParameterCmpt.toString();
					String oldParameter = lineInArray[1];
					if(!(newToOldParameters.values().contains(oldParameter))){
						newToOldParameters.put(newParameter, oldParameter);
						newParameterCmpt++;
					}
				}else{
					// Catch Header
					lineToWrite = line;
				}

				// Check for optionnal 2nd column to parse
				if(lineInArray[2].equals("PROPORTION")){
					String newParameter = "p"+newParameterCmpt.toString();
					String oldParameter = lineInArray[3];

					if(!(newToOldParameters.values().contains(oldParameter))){
						newToOldParameters.put(newParameter, oldParameter);
						newParameterCmpt++;
					}
				}

				//Prepare line to write in the converted file
				if(!(lineInArray[1].equals("POPULATION"))){
					if(lineInArray[2].equals("PROPORTION")){
						String parameterToWrite1 = "not defined";
						String parameterToWrite2 = "not defined";
						for (Map.Entry<String, String> e : newToOldParameters.entrySet()) {
    						String key = e.getKey();
    						String value = e.getValue();
    						if(value.equals(lineInArray[1])){
    							parameterToWrite1 = key;
    						}
    						if(value.equals(lineInArray[3])){
    							parameterToWrite2 = key;
    						}
						}
						lineToWrite = lineInArray[0]+"\t"+parameterToWrite1+"\t"+lineInArray[2]+"\t"+parameterToWrite2+"\t"+lineInArray[4];
					}else{
						String parameterToWrite1 = "not defined";
						for (Map.Entry<String, String> e : newToOldParameters.entrySet()) {
    						String key = e.getKey();
    						String value = e.getValue();
    						if(value.equals(lineInArray[1])){
    							parameterToWrite1 = key;
    						}
						}
						lineToWrite = lineInArray[0]+"\t"+parameterToWrite1+"\t"+lineInArray[2]+"\t"+lineInArray[3]+"\t"+lineInArray[4];
					}
				}

				// Write converted file
				try{
					FileWriter fw = new FileWriter(outputFilename, true);
					fw.write(lineToWrite+"\n");
					fw.close();
				}catch(IOException exception){
					System.out.println("Error : " + exception.getMessage());
				}
			}
			bufferedReader.close();			
		}catch(IOException exception){
			System.out.println ("Error : " + exception.getMessage());
		}

		// Write correspondance table in PARAMETERS directory
		inputFilenameInArray = patientFilename.split("/");
		inputFilenameInArrayList = new ArrayList<String>(Arrays.asList(inputFilenameInArray));
		String correspondanceFileName = inputFilenameInArrayList.get(inputFilenameInArrayList.size()-1);
		String[] correspondanceFileNameInArray = correspondanceFileName.split(".dat");
		ArrayList<String> correspondanceFileNameInArrayList = new ArrayList<String>(Arrays.asList(correspondanceFileNameInArray));
		if(correspondanceFileNameInArrayList.isEmpty()){
			System.out.println("[toEnumeratedParameter] ERROR => can't parse patient filename : "+ patientFilename);
		}else{
			correspondanceFileName = correspondanceFileNameInArrayList.get(0)+"_table.tmp";
		}
		try{
			FileWriter fw = new FileWriter("DATA/PARAMETERS/"+correspondanceFileName);
			for (Map.Entry<String, String> e : newToOldParameters.entrySet()) {
    			String key = e.getKey();
    			String value = e.getValue();
    			fw.write(value+";"+key+"\n");
    		}
			fw.close();
		}catch(IOException exception){
			System.out.println("Error : "+ exception.getMessage());
		}
	}












	public void checkEnumeratedConversion(){
		/*
		* Check if toEnumeratedParameter() worked fine,
		* compare correspondanceFile
		* 
		*
		* [IN PROGRESS]
		*/


		// List all files to check
		File folder = new File("DATA/PARAMETERS/");
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> listOfFilesInFolder = new ArrayList<File>(Arrays.asList(listOfFiles));
		ArrayList<String> listOfFilesToCheck = new ArrayList<String>();

		for(File file : listOfFilesInFolder){

			String filename = file.toPath().toString();
			String[] filenameInArray = filename.split("_");
			ArrayList<String> filenameInArrayList = new ArrayList<String>(Arrays.asList(filenameInArray));

			if(filenameInArrayList.get(filenameInArrayList.size()-1).equals("table.tmp")){
				listOfFilesToCheck.add(filename);
			}
		}



		for(String filename : listOfFilesToCheck){
			System.out.println(filename);

		}



	}















	public void generateVirtualPatientFile(String virtualPatientFilename, String phase, Integer idPatient){
		/*
		* Write a csv file contening data for a virtual
		* Patient
		*
		* [ALGO] => Check phase
		*			Open file given in parameter
		*			Write random value for paremeter
		*			Close file
		*
		*
		* TODO :  -> adapat to phase I & Inception
		*		  -> Read min & max for random value generation
		*
		* [APPROVED]
		*/


		if(phase == "II"){

			try{
    		FileWriter fw = new FileWriter(virtualPatientFilename);

    		/*
			| Header
    		*/
			fw.write(idPatient+"\t"+"POPULATION\tTYPE\tREF\tVALUE\n");

			/*
			| Data - Panel 1
			*/

			int minimum = 0;
			int maximum = 180;
			int value = 0;
			float percentage = 0;

			value = minimum + (int)(Math.random() * maximum); 
			fw.write("PANEL_1\tLymphocytes\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum); 
			fw.write("PANEL_1\tLymphocytes\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tMonocytes\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tMonocytes\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tPMN\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tPMN\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum); 
			fw.write("PANEL_1\tCD3pos_Tcells\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3pos_Tcells\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3pos_Tcells\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3pos_Tcells\tMFI\tCD3\t"+percentage+"\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD4pos_Tcells\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD4pos_Tcells\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD4pos_Tcells\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD4pos_Tcells\tMFI\tCD4\t"+percentage+"\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD8pos_Tcells\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD8pos_Tcells\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD8pos_Tcells\tPROPORTION\tT_CD3pos\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD8pos_Tcells\tMFI\tCD8\t"+percentage+"\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD19pos_Tcells\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD19pos_Tcells\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD19pos_Tcells\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD19pos_Tcells\tMFI\tCD19\t"+percentage+"\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3negCD56pos_NKcells\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3negCD56pos_NKcells\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3negCD56pos_NKcells\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3negCD56pos_NKcells\tMFI\tCD56\t"+percentage+"\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3posCD56pos_NKneglikeTcells\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3posCD56pos_NKneglikeTcells\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD3posCD56pos_NKneglikeTcells\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56low_CD16high\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56low_CD16high\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56low_CD16high\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56low_CD16high\tPROPORTION\tNK_cells\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56high_CD16low\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56high_CD16low\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56high_CD16low\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD56high_CD16low\tPROPORTION\tNK_cells\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14pos_monocytes\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14pos_monocytes\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14pos_monocytes\tPROPORTION\tLymphocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14highCD16neg_classicalMonocytes\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14highCD16neg_classicalMonocytes\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14highCD16neg_classicalMonocytes\tPROPORTION\tMonocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14posCD16pos_intermediateMonocytes\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14posCD16pos_intermediateMonocytes\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14posCD16pos_intermediateMonocytes\tPROPORTION\tMonocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14lowCD16pos_nonclassicMonocytes\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14lowCD16pos_nonclassicMonocytes\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD14lowCD16pos_nonclassicMonocytes\tPROPORTION\tMonocytes\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15posCD14low_LDGs\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15posCD14low_LDGs\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15posCD14low_LDGs\tPROPORTION\tPBMC\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15lowCD16high_Neutrophils\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15lowCD16high_Neutrophils\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15lowCD16high_Neutrophils\tPROPORTION\tPMN\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15highCD16neg_Eosinophils\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15highCD16neg_Eosinophils\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD15highCD16neg_Eosinophils\tPROPORTION\tPMN\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_1\tCD4/CD8\tRATIO\t\t"+percentage+"\n");


			/*
			| Data - Panel 2
			*/
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cnegCD123pos_pDC\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cnegCD123pos_pDC\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cnegCD123pos_pDC\tPROPORTION\tDrpos_LINneg\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cposCD123negCD1cpos_mDC1\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cposCD123negCD1cpos_mDC1\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cposCD123negCD1cpos_mDC1\tPROPORTION\tmDC\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cposCD123negCD141pos_mDC2\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cposCD123negCD141pos_mDC2\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRposCD11cposCD123negCD141pos_mDC2\tPROPORTION\tmDC\t"+percentage+"%\n");
			value = minimum + (int)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRnegCD123pos_Basophils\tABSOLUTE\t\t"+value+"\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRnegCD123pos_Basophils\tPROPORTION\tLeukocytes\t"+percentage+"%\n");
			percentage = (float)(Math.random() * maximum);
			fw.write("PANEL_2\tLinnegDRnegCD123pos_Basophils\tPROPORTION\tmDC\t"+percentage+"%\n");




       	
    		fw.close();
		}catch (IOException exception){
    		System.out.println ("Error : " + exception.getMessage());
		}




		}

	}








	public void generateVirtualData(Integer numberOfPatients, String cohorteFilename){
        /*
        * Generate a virtual cohorte
        *
        *
        *
        * [IN PROGRESS]
        */

        Integer iteration = 1;
        ArrayList<String> listOfPatientFiles = new ArrayList<String>();
        while(iteration <= numberOfPatients){

            String patientFileName = "DATA/INPUT/VIRTUAL_PATIENT_"+iteration;
            String convertedPatientFileName = "DATA/INPUT/VIRTUAL_PATIENT_"+iteration+"_converted.data";
            generateVirtualPatientFile(patientFileName+".dat", "II", iteration);
            toEnumeratedParameter(patientFileName+".dat");
            convertPatientFile("DATA/INPUT/VIRTUAL_PATIENT_"+iteration+"_enumerated_parameters.data", "II", convertedPatientFileName);
            listOfPatientFiles.add(convertedPatientFileName);
            iteration++;
        }

        assembleCohorte(listOfPatientFiles, cohorteFilename);
    }








}