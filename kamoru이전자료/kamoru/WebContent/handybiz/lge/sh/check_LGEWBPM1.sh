#!/bin/ksh

while :

do
	date                                                     > LGEWBPM1_date.txt
	ps -ef | grep bpmd | grep weblogic.Server | grep -v grep > LGEWBPM1_bpmd.txt
	ps -ef | grep bpmg | grep weblogic.Server | grep -v grep > LGEWBPM1_bpmg.txt
	df -k                                                    > LGEWBPM1_df.txt
	vmstat 1 1                                               > LGEWBPM1_vmstat.txt
	sleep 60
done

