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


    	

    	//----------- PROCEDURES ------------------------------------------------
    	


        /*----------------/*
        | Procedure Test  | => [APPROVED]
        /*---------------*/

        // Control recursivity on test data

        /*
        //Get order list of frequent item, both order (decreasing and ascending)
        Integer frequentTreshold = 3;
        Integer memorySizeTreshold = 2;
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
        
        procedureTest.usePartitionProjection(sortedDB, orderList);

        System.out.println("*-RETRIEVING FREQUENT PATTERN-*");
        procedureTest.retreiveFrequentPattern("DATA/ALL_RESULTS");
        */






        /*------------------/*
        | Procedure Virtual | => [APPROVED]
        /*-----------------*/

        // Control recursivity on virtual data



        //Generate Virtual Data
        DataConverter test = new DataConverter();
        test.generateVirtualData(1000, "DATA/INPUT/test_cohorte.data");


        long startTime = System.currentTimeMillis();

        //Get order list of frequent item, both order (decreasing and ascending)
        Integer frequentTreshold = 4;
        Integer memorySizeTreshold = 20;
        DataManager procedure3 = new DataManager();
        System.out.println("*-PREPARE DATA FOR MINING-*");
        ArrayList<String> orderList = new ArrayList<String>();
        String initialDB = "DATA/INPUT/VIRTUAL_COHORTE_1.data";
        orderList = procedure3.getOrderListOfFrequentItem(initialDB, frequentTreshold);

        //reorder database
        procedure3.reorderDatabase(initialDB, orderList);
        String sortedDB = initialDB.substring(0, initialDB.length()-5);
        sortedDB+="_sorted.data";

        //Use Partition projection
        Analyse procedureTest = new Analyse("DATA/INPUT/VIRTUAL_COHORTE_1.data", frequentTreshold, memorySizeTreshold);
        procedureTest.usePartitionProjection(sortedDB, orderList);

        
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
        

        long endTime = System.currentTimeMillis();
        long timer = endTime - startTime;
        System.out.println("[PERFORM IN] => "+timer);

        System.out.println("*-EOF-*");



    }

}