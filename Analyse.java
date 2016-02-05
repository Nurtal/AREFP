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
    Integer memorySizeTreshold;



	// ---------------- CONSTRUCTORS ---------------------

	public Analyse(String filename, Integer t, Integer t2){

		initialDatabaseFileName = filename;
        treshold = t;
        memorySizeTreshold = t2;



	}


	// ------------------ METHODS ------------------------


    public void usePartitionProjection(String databaseFilename, ArrayList<String> frequentItemList){
        /*
        * Mining procedure using FP-growth Algorithm
        * on partition-projected-database
        *
        * => [ALGO]:
        *       -> Prepare Data 
        *       -> Initialise projection
        *       -> Perform Mining
        *
        * TODO: -> Find a better parameter for memory needs,
        *          before FP-tree construction
        *
        * [APPROVED]
        */


        /*
        |   [1] Prepare Data
        |       -> Set Tresholds
        |       -> Get order list of frequent item (descending frequency order)
        |       -> Create reverse order list of frequent item (ascending order)
        */

        //Set Tresholds
        Integer treshold = this.treshold;
        Integer sizeTreshold = this.memorySizeTreshold;

        //Create the reverse list of frequent item
        DataManager procedure3 = new DataManager();
        String sortedDB = databaseFilename;
        ArrayList<String> orderList = frequentItemList;
        ArrayList<String> reverseOrderList = new ArrayList<String>();    
        for(String item : frequentItemList){
            reverseOrderList.add(item);
        }
        Collections.reverse(reverseOrderList);

        //List all frequent items present in current database file
        ArrayList<String> orderListOfItemPresentInFileCandidat = procedure3.getOrderListOfFrequentItem(sortedDB, 1);
        ArrayList<String> orderListOfItemPresentInFile = new ArrayList<String>();
        for(String item : orderList){
            if(orderListOfItemPresentInFileCandidat.contains(item)){
                orderListOfItemPresentInFile.add(item);
            }
        }

        //Create the reverse list af all frequent item in current database file
        ArrayList<String> reverseOrderListOfItemPresentInFile = new ArrayList<String>();
        for(String item : orderListOfItemPresentInFile){
            reverseOrderListOfItemPresentInFile.add(item);
        }
        Collections.reverse(reverseOrderListOfItemPresentInFile);

    
    
        /*
        |   [2] Initialise projection
        |       -> Determine projection path
        |       -> Initialise partition-projected-database files
        |       -> Perform projection on initial database
        */

        // Determine projection path
        String projectedPath = "DATA/PROJECTED_DATABASE/";
        String savePath = "DATA/ALL_RESULTS/";
        String[] databaseFilenameInArray = sortedDB.split("/");
        ArrayList<String> pathInArray = new ArrayList<String>(Arrays.asList(databaseFilenameInArray));
                
        for(String directory : pathInArray){
            if(!(directory.equals("DATA")) && !(directory.equals("PROJECTED_DATABASE"))){
                String[] filenameToParseInArray = directory.split("_ppd");
                ArrayList<String> filenameParsedInArray = new ArrayList<String>(Arrays.asList(filenameToParseInArray));
                if(filenameParsedInArray.size() > 1){
                    String itemToInsertInPath = filenameParsedInArray.get(0);
                    itemToInsertInPath+="_ppdr";
                    projectedPath+=itemToInsertInPath+"/";
                    savePath+=itemToInsertInPath+"/";
                }
            }
        }


        //Initialise partition_projected-database files & perform projection on initial (input) database
        procedure3.initialiseProjectedDatabase("partition", orderListOfItemPresentInFile, projectedPath);
        procedure3.partitionProjection(sortedDB, orderList, projectedPath);
        

        /*
        |   [3] Perform Mining
        |       -> for each item in reverse ordeer list of frequent item:
        |           -> Construct fpTree from item - partition-projected-database
        |           -> if fp-tree too large for mining (i.e more nodes than treshold size):
        |               -> use partition projection on partition projected database file (recursivity)
        |           -> else: 
        |               -> Mine fpTree with the FP-Growth Algorithm
        |               -> Save results from fpTree mining (TO ADAPT)
        |           -> Perform projection on item-partition-projected-database
        */

        System.out.println("*-MINING*-");
        for(String item : reverseOrderListOfItemPresentInFile){

            //System.out.println("-------"+item+"-------"+"[INPUT] => "+ sortedDB + "|| [OUTPUT] => "+projectedPath+item+"_partition_projected_database.data");

            ArrayList<String> initList = new ArrayList<String>();
            String inputFile = projectedPath+item+"_ppd.data";


            /*
            | -> Have to find a way to test memory needs here, 
            |    before construction of FP-tree
            |       -> Pb with the value of treshold (have to be low), fp-tree construction seems low cost for now
            */


            //Build FP-tree
            //System.out.println("=> Building FP-tree");
            FPtree fpTree = procedure3.fpTreeConstruction(inputFile, treshold);
            
            if(fpTree.headerTable.size() >= sizeTreshold){
                //Going Recursive
                System.out.println("=> Going Recursive");
                
                usePartitionProjection(inputFile, orderList);
            }else{
                //Mining
                System.out.println("=> Mining FP-tree");

                procedure3.frequentPatternGrowth(fpTree, initList, treshold);
                procedure3.saveResults(savePath+item+"_ppdr");
            }

            procedure3.partitionProjection(inputFile, orderList, projectedPath);
        }
    }









    public void retreiveFrequentPattern(String directoryName){
        /*
        *
        * Scan ALL_RESULTS Directory
        * Write all frequent pattern in
        * a file (Currently DATA/OUTPUT/Frequent_Patterns.data)
        *
        *
        * [APPROVED]
        */

        //Search for a text frequentPattern.dat file in current directory
        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles.length > 0){
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()){
                    String itemToAdd = "";

                    //if directory is not root (i.e ALL_RESULTS here), scan directory name
                    if(!(directoryName.equals("DATA/ALL_RESULTS"))){

                        String[] directoryNameInArray = directoryName.split("/");
                        ArrayList<String> directoryNameInArrayList = new ArrayList<String>(Arrays.asList(directoryNameInArray));
                        
                        // Parse item name for each directory in path
                        for(String directory : directoryNameInArrayList){
                            String[] directoryNameWithoutPathInArray = directory.split("_partition_projected");
                            if(directoryNameWithoutPathInArray.length > 1){
                                itemToAdd+= " "+directoryNameWithoutPathInArray[0];
                            }
                        }
                    }

                    // Read file
                    String line = null;
                    try{
                        FileReader fileReader = new FileReader(directoryName+"/"+listOfFiles[i].getName());
                        BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
                        while((line = bufferedReader.readLine()) != null) {
                            
                            System.out.println(itemToAdd + " " + line);
                            
                            //Write results
                            String frequentPatternToWrite = itemToAdd + " " + line+"\n";
                            try{
                                FileWriter fw = new FileWriter("DATA/OUTPUT/Frequent_Patterns.data", true);
                                fw.write(frequentPatternToWrite);
                                fw.close();
                            }catch(IOException exception){
                                System.out.println(exception.getMessage());
                            }
                        }

                    }catch(IOException exception){
                        System.out.println("Error : " + exception.getMessage());
                    }

                }else if (listOfFiles[i].isDirectory()){
                    // Going recursive

                    //System.out.println("=>"+directoryName+"/"+ listOfFiles[i].getName());
                    retreiveFrequentPattern(directoryName +"/"+ listOfFiles[i].getName());
                }
            }

        }

    }








    // ------------------ WORK IN PROGRESS ------------------------



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
        * [NOT AVAILABLE FOR NOW]
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