#!/bin/ksh

while :

do
	date                                                     > LGEWBPM2_date.txt
	ps -ef | grep bpmg | grep weblogic.Server | grep -v grep > LGEWBPM2_bpmg.txt
	ps -ef | grep bpmg | grep qhandler | grep -v grep        > LGEWBPM2_qhandler.txt
	ls -l /array02/bpmg/bea/user_projects/domains/mydomain/QHANDLER_WORK_DIR/*.out        > LGEWBPM2_qhandler_log.txt
	df -k                                                    > LGEWBPM2_df.txt
	vmstat 1 1                                               > LGEWBPM2_vmstat.txt
#	ftp -n 156.147.36.113 < ./ftpscript
	sleep 60
done

