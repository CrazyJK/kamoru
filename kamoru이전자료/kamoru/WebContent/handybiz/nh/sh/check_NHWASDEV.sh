#!/bin/ksh

while :

do
	date                                          > nh_was_date_dev.txt
	ps -ef | grep 7201 | grep -v grep | sort -k 9 > nh_was_ps_dev.txt
	df -k                                         > nh_was_df_dev.txt
	vmstat 1 1 				                      > nh_was_vmstat_dev.txt
	tail /home/bizflowg/bin/runhistory.log        > nh_was_qh_dev.txt
	sleep 60
done
