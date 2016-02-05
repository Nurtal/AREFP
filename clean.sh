#!/bin/bash

rm -v DATA/RESULTS/*
rm -v DATA/CONDITIONAL_DATABASE/*
for patientFile in $(ls -ap DATA/INPUT | grep -v "/$"); do
	if [[ $patientFile =~ ^"VIRTUAL_PATIENT_"*  ]];then
		rm -v "DATA/INPUT/"$patientFile;
	fi

done

#listePatient2=( $(ls -ap DATA/PARAMETER | grep -v "/$") )
for patientFile in $(ls -ap DATA/PARAMETERS | grep -v "/$"); do
        if [[ $patientFile =~ ^"VIRTUAL_PATIENT_"*  ]];then
                rm -v "DATA/PARAMETERS/"$patientFile;
        fi
done




rm -rv DATA/PROJECTED_DATABASE/*
rm -rv DATA/ALL_RESULTS/*
rm -rv DATA/OUTPUT/*
