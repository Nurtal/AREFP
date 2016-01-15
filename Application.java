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

    	DataConverter test = new DataConverter();
    	List<String> listOfPatients = new ArrayList<String>();
    	listOfPatients.add("DATA/patient.num");
    	listOfPatients.add("DATA/patient1.num");
    	listOfPatients.add("DATA/patient2.num");

    	test.initialiseTreshold("DATA/PARAMETERS/parameter_phase_II.csv");
    	test.convertToNumFormat("DATA/data_REAL_PHASE_II.csv", "II", "DATA/realPatient.num");
    	test.assembleCohorte(listOfPatients, "DATA/cohorte.num");
    	test.enumerate("DATA/cohorte.num", "DATA/cohorteToMine.num");




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
		| Not tested on experimental data, [IN PROGRESS]
    	*/



    	/*
		| Parameters initialisation
    	*/
    	ArrayList<String> iniList = new ArrayList<String>();
    	Integer treshold = 3;
    	String inputFile = "DATA/INPUT/initData2.data";

    	/*
		| Mining Data
    	*/
    	DataManager procedure2 = new DataManager();
    	FPtree fpTree = procedure2.fpTreeConstruction(inputFile, treshold);
    	procedure2.frequentPatternGrowth(fpTree, iniList, treshold);

    }

}