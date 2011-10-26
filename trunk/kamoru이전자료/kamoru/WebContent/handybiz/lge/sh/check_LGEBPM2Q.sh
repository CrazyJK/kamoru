#!/bin/ksh

while :

do
	date                                          > LGEBPM2Q_date.txt
	ps -ef | grep 7202 | grep -v grep | sort -k 9 > LGEBPM2Q_bizflowg.txt
	df -k                                         > LGEBPM2Q_df.txt
	vmstat 1 1				      > LGEBPM2Q_vmstat.txt
	tail /home/bizflowg/bin/runhistory.log        > LGEBPM2Q_runhistory.txt
	sleep 60
done
