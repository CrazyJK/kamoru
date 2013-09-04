#!/bin/ksh

while :

do
	date                                          > LGEBPM1Q_date.txt
	ps -ef | grep 7201 | grep -v grep | sort -k 9 > LGEBPM1Q_bizflowd.txt
	ps -ef | grep 7202 | grep -v grep | sort -k 9 > LGEBPM1Q_bizflowg.txt
	df -k                                         > LGEBPM1Q_df.txt
	vmstat 1 1 				      > LGEBPM1Q_vmstat.txt
	tail /home/bizflowg/bin/runhistory.log        > LGEBPM1Q_runhistory.txt
	sleep 60
done
