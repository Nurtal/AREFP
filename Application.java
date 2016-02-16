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


        String mode = args[0];
    	

    	//----------- PROCEDURES ------------------------------------------------
    	

        if(mode.equals("Exemple")){

           
            /*-------------------/*
            | Procedure Exemple  |
            /*------------------*/

            //Get order list of frequent item, both order (decreasing and ascending)
            Integer frequentTreshold = 3;
            Integer memorySizeTreshold = 4;
            float confidenceTreshold = 0.75f;
            DataManager procedure3 = new DataManager();
            System.out.println("*-PREPARE DATA FOR MINING-*");
            ArrayList<String> orderList = new ArrayList<String>();
            String initialDB = "DATA/INPUT/initialDB2.data";
            orderList = procedure3.getOrderListOfFrequentItem(initialDB, frequentTreshold);

            //reorder database
            procedure3.reorderDatabase(initialDB, orderList);
            String sortedDB = initialDB.substring(0, initialDB.length()-5);
            sortedDB+="_sorted.data";

            Analyse procedureTest = new Analyse("DATA/INPUT/initialDB2.data", frequentTreshold, memorySizeTreshold);
        
            procedureTest.usePartitionProjection(initialDB);

            System.out.println("*-RETRIEVING FREQUENT PATTERN-*");
            procedureTest.retreiveFrequentPattern("DATA/ALL_RESULTS");
            System.out.println("*-GENERATE ASSOCIATION  RULES-*");
            procedureTest.generateAssociationRules("DATA/OUTPUT/Frequent_Patterns.data", confidenceTreshold);

            System.out.println("*-EOF-*");
    
        }else if(mode.equals("Virtual")){

            /*------------------/*
            | Procedure Virtual |
            /*-----------------*/

            Integer numberOfPatient = Integer.parseInt(args[1]);
            Integer frequentTreshold = Integer.parseInt(args[2]);
            float confidenceTreshold = Float.valueOf(args[3]);
            Integer memorySizeTreshold = Integer.parseInt(args[4]);



            System.out.println("=> Number of patient :"+numberOfPatient);
        
            //Generate Virtual Data
            DataConverter test = new DataConverter();
            test.generateVirtualData(numberOfPatient, "DATA/INPUT/VIRTUAL_COHORTE_1.data");

            //Get order list of frequent item, both order (decreasing and ascending)
            DataManager procedure3 = new DataManager();
            System.out.println("*-PREPARE DATA FOR MINING-*");
            ArrayList<String> orderList = new ArrayList<String>();
            String initialDB = "DATA/INPUT/VIRTUAL_COHORTE_1.data";
            orderList = procedure3.getOrderListOfFrequentItem(initialDB, frequentTreshold);

            //Use Partition projection
            Analyse procedureTest = new Analyse("DATA/INPUT/VIRTUAL_COHORTE_1.data", frequentTreshold, memorySizeTreshold);
            procedureTest.usePartitionProjection(initialDB);

            //Retrieve frequent Patterns
            System.out.println("*-RETRIEVING FREQUENT PATTERN-*");

            //Initialise Result File
            try{
                FileWriter fw = new FileWriter("DATA/OUTPUT/Frequent_Patterns.data");
                fw.close();
            }catch(IOException exception){
                System.out.println(exception.getMessage());
            }
            procedureTest.retreiveFrequentPattern("DATA/ALL_RESULTS");
            procedureTest.generateAssociationRules("DATA/OUTPUT/Frequent_Patterns.data", confidenceTreshold);
        
            System.out.println("*-EOF-*");
    


        }else if(mode.equals("List")){

            /*---------------/*
            | Procedure List |
            /*--------------*/

            //Work only on phase II for now
            //patient files have to be placed in DATA/INPUT

           
            //Parse arguments
            DataConverter converter = new DataConverter();
            String listOfPatient = args[1];
            String[] listOfPatientInArray = listOfPatient.split(",");
            List<String> listOfPatientInArrayList = new ArrayList<String>(Arrays.asList(listOfPatientInArray));
            Integer frequentTreshold = Integer.parseInt(args[2]);
            float confidenceTreshold = Float.valueOf(args[3]);
            Integer memorySizeTreshold = Integer.parseInt(args[4]);

            //Create Cohorte File from list of patient files
            ArrayList<String> listOfPatientFiles = new ArrayList<String>();
            for(String patientFile : listOfPatientInArrayList){
                String convertedPatientFileName = patientFile+"_converted.data";
                converter.toEnumeratedParameter(patientFile);
                converter.convertPatientFile(patientFile+"_enumerated_parameters.data", "II", convertedPatientFileName);
                listOfPatientFiles.add(convertedPatientFileName);
            }
            converter.assembleCohorte(listOfPatientFiles, "DATA/INPUT/VIRTUAL_COHORTE_1.data");

            //Get order list of frequent item, both order (decreasing and ascending)
            DataManager procedure3 = new DataManager();
            System.out.println("*-PREPARE DATA FOR MINING-*");
            ArrayList<String> orderList = new ArrayList<String>();
            String initialDB = "DATA/INPUT/VIRTUAL_COHORTE_1.data";
            orderList = procedure3.getOrderListOfFrequentItem(initialDB, frequentTreshold);

            //Use Partition projection
            Analyse procedureTest = new Analyse("DATA/INPUT/VIRTUAL_COHORTE_1.data", frequentTreshold, memorySizeTreshold);
            procedureTest.usePartitionProjection(initialDB);

            //Retrieve frequent Patterns
            System.out.println("*-RETRIEVING FREQUENT PATTERN-*");

            //Initialise Result File
            try{
                FileWriter fw = new FileWriter("DATA/OUTPUT/Frequent_Patterns.data");
                fw.close();
            }catch(IOException exception){
                System.out.println(exception.getMessage());
            }
            procedureTest.retreiveFrequentPattern("DATA/ALL_RESULTS");
            System.out.println("*-GENERATE ASSOCIATION RULES-*");
            procedureTest.generateAssociationRules("DATA/OUTPUT/Frequent_Patterns.data", confidenceTreshold);
        
            System.out.println("*-EOF-*");



        }else if(mode.equals("debug")){

            // Testing individual methods in dev version

            System.out.println("*-TEST MODE-*");

            //DataConverter converter = new DataConverter();
            //converter.backConversionFromEnumeration("DATA/OUTPUT/Frequent_Patterns.data", "DATA/PARAMETERS/VIRTUAL_PATIENT_3_table.tmp", "DATA/OUTPUT/Frequent_Patterns_converted.data");

            //Initialise Result File
            Analyse procedureTest = new Analyse("DATA/INPUT/VIRTUAL_COHORTE_1.data", 10, 4);
            try{
                FileWriter fw = new FileWriter("DATA/OUTPUT/Frequent_Patterns.data");
                fw.close();
            }catch(IOException exception){
                System.out.println(exception.getMessage());
            }
            procedureTest.retreiveFrequentPattern("DATA/ALL_RESULTS");
            procedureTest.generateAssociationRules("DATA/OUTPUT/Frequent_Patterns.data", 10);




        }

    }

    

}