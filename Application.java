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


public class Application{



	//------------------ MAIN ---------------------------
    public static void main(String[] args) throws IOException {


    	
    	//-----------Test Methods--------------------------------------------------


        /*
    	DataConverter test = new DataConverter();
    	List<String> listOfPatients = new ArrayList<String>();
    	listOfPatients.add("DATA/patient.num");
    	listOfPatients.add("DATA/patient1.num");
    	listOfPatients.add("DATA/patient2.num");

    	test.initialiseTreshold("DATA/PARAMETERS/parameter_phase_II.csv");
    	//test.convertToNumFormat("DATA/data_REAL_PHASE_II.csv", "II", "DATA/realPatient.num");
    	//test.assembleCohorte(listOfPatients, "DATA/cohorte.num");
    	//test.enumerate("DATA/cohorte.num", "DATA/cohorteToMine.num");

    	test.convertPatientFile("DATA/data_REAL_PHASE_II.csv", "II", "DATA/realPatient.num");
        */


        DataConverter test = new DataConverter();
        //test.toEnumeratedParameter("DATA/INPUT/VIRTUAL_PATIENT_3.dat");
        //test.checkEnumeratedConversion();

        test.generateVirtualData(3, "DATA/INPUT/test_cohorte.data");        

    	//-----------Test Procedure------------------------------------------------
    	

    	/*------------/*
		| Procedure 1 | => [APPROVED]
    	/*-----------*/

    	/*
    	| Homemade procedure,
		| Not tested on experimental data
    	*/


    	/*
    	DataManager precedure1 = new DataManager();
    	precedure1.parralelProjection("DATA/initialDB.data", items); //Because it's working fine

    	ArrayList<ArrayList<String>> frequentPattern = new ArrayList<ArrayList<String>>();

    	for(String item : items){
    		String projectedDbFilename = "DATA/"+item+"_parralel_projected_database.data";
    		HashMap<String,Integer> conditionalTree = precedure1.buildFPtree(projectedDbFilename, 3);
    		ArrayList<ArrayList<String>> patternsToAdd = precedure1.getFrequentPatternFromConditionalFPtree(conditionalTree, item);

    		System.out.println(patternsToAdd + "||" + item);

    		//frequentPattern.add(patternsToAdd);
    	}
    	*/



		/*------------/*
		| Procedure 2 | => [APPROVED]
    	/*-----------*/

    	/*
		| Tested on experimental data, Memory issues
    	*/


        /*
        | Data initialisation
        */

        /*
        DataConverter manageFile = new DataConverter();
        manageFile.convertPatientFile("DATA/INPUT/VIRTUAL_PATIENT_1.dat", "II", "DATA/INPUT/data_converted_PHASE_II_1.data");
        manageFile.convertPatientFile("DATA/INPUT/VIRTUAL_PATIENT_2.dat", "II", "DATA/INPUT/data_converted_PHASE_II_2.data");
        manageFile.convertPatientFile("DATA/INPUT/VIRTUAL_PATIENT_3.dat", "II", "DATA/INPUT/data_converted_PHASE_II_3.data");
        manageFile.convertPatientFile("DATA/INPUT/VIRTUAL_PATIENT_4.dat", "II", "DATA/INPUT/data_converted_PHASE_II_4.data");
        manageFile.convertPatientFile("DATA/INPUT/VIRTUAL_PATIENT_5.dat", "II", "DATA/INPUT/data_converted_PHASE_II_5.data");

        List<String> listOfFilenames = new ArrayList<String>();
        listOfFilenames.add("DATA/INPUT/data_converted_PHASE_II_1.data");
        listOfFilenames.add("DATA/INPUT/data_converted_PHASE_II_2.data");
        listOfFilenames.add("DATA/INPUT/data_converted_PHASE_II_3.data");
        listOfFilenames.add("DATA/INPUT/data_converted_PHASE_II_4.data");
        listOfFilenames.add("DATA/INPUT/data_converted_PHASE_II_5.data");

        manageFile.assembleCohorte(listOfFilenames, "DATA/INPUT/VIRTUAL_COHORTE_1.data");

        //manageFile.generateVirtualPatientFile("DATA/INPUT/VIRTUAL_PATIENT_5.dat", "II", 5);

        */
    	/*
		| Parameters initialisation
    	*/
        /*
    	ArrayList<String> iniList = new ArrayList<String>();
    	Integer treshold = 3;
    	String inputFile = "DATA/initialDB2.data";
        */
        
    	/*
		| Mining Data
    	*/
        
        /*
    	DataManager procedure2 = new DataManager();
    	FPtree fpTree = procedure2.fpTreeConstruction(inputFile, treshold);
        System.out.println("\n*---Mining---*\n");
    	procedure2.frequentPatternGrowth(fpTree, iniList, treshold);
        */
    




        //Analyse procedureTest = new Analyse("DATA/INPUT/VIRTUAL_COHORTE_1.data", 3);
        //procedureTest.usePartitionProjection("DATA/INPUT/VIRTUAL_COHORTE_1.data");
        //procedureTest.useParralelProjection();



    }

}