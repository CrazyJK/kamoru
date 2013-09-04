#!/bin/ksh

while :

do
	date                                          > nh_bpm_date_dev.txt
	ps -ef | grep 7201 | grep -v grep | sort -k 9 > nh_bpm_ps_dev.txt
	df -k                                         > nh_bpm_df_dev.txt
	vmstat 1 1 				                      > nh_bpm_vmstat_dev.txt
	tail /home/bizflowg/bin/runhistory.log        > nh_bpm_runhistory_dev.txt
	sleep 60
done
