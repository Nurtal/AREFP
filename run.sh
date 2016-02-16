#!/bin/bash

#
# Run the AREFP Application
#


echo "----COMPILATION----"
javac *java


if [[ $1 == "Liste" ]];then
	echo "Going With a List"
	mod="List"
	listOfFilename=$2
	minSupport=$3
	minConfidence=$4
	maxTreeSize=$5

	echo "----RUNNING AREFP (List Mod)----"
	java Application $mod $listOfFilename $minSupport $minConfidence $maxTreeSize


elif [[ $1 == "Cohorte" ]];then
	echo "Going with a Cohorte"
	mod="Cohorte"
	cohorteFilename=$2
	minSupport=$3
	minConfidence=$4
	maxTreeSize=$5	
	echo "----RUNNING AREFP (Cohorte Mod)----"
	java Application $mod $cohorteFilename $minSupport $minConfidence $maxTreeSize

elif [[ $1 == "Virtual" ]];then
	echo "Going Virtual"
	mod="Virtual"
	numberOfPatient=$2
	minSupport=$3
	minConfidence=$4
	maxTreeSize=$5
	echo "----RUNNING AREFP (Virtual Mod)----"
	java Application $mod $numberOfPatient $minSupport $minConfidence $maxTreeSize	



elif [[ $1 == "help" ]];then
	echo "Association Rules Extraction From Patients
=> This Script run the AREFP application,
=> Takes 5 parameters :
	-> 1 : the mode for running the application, could be:
	       List, Cohorte, Virtual or Exemple.
	-> 2 : the list of patient files (seperate by comma) in
	       List mode, the name of the cohorte file in Cohorte mode,
	       the number of virtual patient to generate in Virtual mode.
	-> 3 : the minimum support for an item to be considered frequent
	-> 4 : The minimum confidence for a rule to be accepted
	-> 5 : The maximum size (i.e number of nodes) authorized in a
	       Frequent Pattern tree.
	"

elif [[ $1 == "exemple" ]];then
	echo "----RUNNING AREFP (Exemple Mod)----"
	java Application Exemple

fi

