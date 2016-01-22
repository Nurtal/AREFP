/* ---------------------------------------------------------------------- */
/*                                                                        */
/*                        FP-TREE WITH PROJECTION                         */
/*                                                                        */
/*                           Nathan Foulquier                             */
/*                                                                        */
/*                       		21/01/2016                         */
/*                                                                        */
/*                                                                        */ 
/* ---------------------------------------------------------------------- */

import java.io.*;
import java.util.*;


public class Analyse{


	// ------------------- FIELDS ------------------------
	String initialDatabaseFileName;
    Integer treshold;



	// ---------------- CONSTRUCTORS ---------------------

	public Analyse(String filename, Integer t){

		initialDatabaseFileName = filename;
        treshold = t;


	}


	// ------------------ METHODS ------------------------

	public void usePartitionProjection(String databaseFilename){
		/*
		* Mining procedure using FP-growth Algorithm
		* on partition-projected-database
		*
		* => [ALGO]:
		*		-> Prepare Data 
		*		-> Initialise projection
		*		-> Perform Mining
		*
        * TODO: -> control results on real data
        *       -> Find a better parameter for memory needs,
        *          before FP-tree construction
        *
		* [APPROVED]
        * [HAVE TO CONTROL RESULTS WITH THE NEW RECUSRIVITY]
		*/


		/*
		|	[1] Prepare Data
        |       -> Set Tresholds
        |		-> Get order list of frequent item (descending frequency order)
        |		-> Create reverse order list of frequent item (ascending order)
        |		-> Reordering database according to order list of frequent item
		*/

        //Set Tresholds
        Integer treshold = this.treshold;
        Integer sizeTreshold = 20;

        //Get order list of frequent item, both order (decreasing and ascending)
		DataManager procedure3 = new DataManager();
        System.out.println("*-PREPARE DATA FOR MINING-*");
        ArrayList<String> orderList = new ArrayList<String>();
        ArrayList<String> reverseOrderList = new ArrayList<String>();
        String initialDB = databaseFilename;
        orderList = procedure3.getOrderListOfFrequentItem(initialDB, 3);
        reverseOrderList = procedure3.getOrderListOfFrequentItem(initialDB, 3);
        Collections.reverse(reverseOrderList);

        //reorder database
        procedure3.reorderDatabase(initialDB, orderList);
    	String sortedDB = initialDB.substring(0, initialDB.length()-5);
    	sortedDB+="_sorted.data";


        /*
		|	[2] Initialise projection
        |       -> Determine projection path
        |		-> Initialise partition-projected-database files
        |	    -> Perform projection on initial database
        */

        // Determine projection path
        String projectedPath = "DATA/PROJECTED_DATABASE/";
        String savePath = "DATA/ALL_RESULTS/";
        String[] databaseFilenameInArray = initialDB.split("/");
        ArrayList<String> pathInArray = new ArrayList<String>(Arrays.asList(databaseFilenameInArray));
        String filenameToParse = pathInArray.get(pathInArray.size()-1);
        String[] filenameToParseInArray = filenameToParse.split("_partition");
        ArrayList<String> filenameParsedInArray = new ArrayList<String>(Arrays.asList(filenameToParseInArray));
        if(filenameParsedInArray.size() > 1){
            String itemToInsertInPath = filenameParsedInArray.get(0);
            projectedPath+=itemToInsertInPath+"/";
            savePath+=itemToInsertInPath+"/";
        }

        //Initialise partition_projected-database files & perform projection on initial (input) database
        procedure3.initialiseProjectedDatabase("partition", orderList, projectedPath);
        procedure3.partitionProjection(sortedDB, orderList);
        

        /*
		|	[3] Perform Mining
        |		-> for each item in reverse ordeer list of frequent item:
        |			-> Construct fpTree from item - partition-projected-database
        |           -> if fp-tree too large for mining (i.e more nodes than treshold size):
        |               -> use partition projection on partition projected database file (recursivity)
        |           -> else: 
        |			    -> Mine fpTree with the FP-Growth Algorithm
        |               -> Save results from fpTree mining (TO ADAPT)
        |			-> Perform projection on item-partition-projected-database
        */

        System.out.println("*-MINING*-");
        for(String item : reverseOrderList){

            System.out.println("-------"+item+"-------");

            ArrayList<String> initList = new ArrayList<String>();
            String inputFile = projectedPath+item+"_partition_projected_database.data";

            //Build FP-tree            
            FPtree fpTree = procedure3.fpTreeConstruction(inputFile, treshold);
            
            if(fpTree.headerTable.size() >= sizeTreshold){
                //Going Recursive
                usePartitionProjection(inputFile);
            }else{
                //Mining
                procedure3.frequentPatternGrowth(fpTree, initList, treshold);
                procedure3.saveResults(savePath+item+"_partition_projected_database_results");
            }

            procedure3.partitionProjection(inputFile, orderList);
        }
	}








    public void useParralelProjection(){
        /*
        * Mining procedure using FP-growth Algorithm
        * on parralel-projected-database
        *
        * => [ALGO]:
        *       -> Prepare Data 
        *       -> Initialise projection
        *       -> Perform Mining
        *
        * [IN PROGRESS]
        */

        /*
        | Preparer Data
        */
        DataManager procedure3 = new DataManager();
        System.out.println("*-PREPARE DATA FOR MINING-*");
        Integer treshold = this.treshold;
        ArrayList<String> orderList = new ArrayList<String>();
        String initialDB = this.initialDatabaseFileName;
        orderList = procedure3.getOrderListOfFrequentItem(initialDB, 3);
        procedure3.reorderDatabase(initialDB, orderList);
        String sortedDB = initialDB.substring(0, initialDB.length()-5);
        sortedDB+="_sorted.data";

        /*
        | Initialise Projection
        */
        procedure3.parralelProjection(sortedDB, orderList);

        /*
        | Perform Mining
        */
        System.out.println("*-MINING*-");
        for(String item : orderList){
            
            System.out.println("-------"+item+"-------");

            ArrayList<String> initList = new ArrayList<String>();
            String inputFile = "DATA/PROJECTED_DATABASE/"+item+"_parralel_projected_database.data";


            FPtree fpTree = procedure3.fpTreeConstruction(inputFile, treshold);


            
            procedure3.frequentPatternGrowth(fpTree, initList, treshold);
            

            procedure3.saveResults(item+"_parralel_projected_database_results");

        }






    }





    








}