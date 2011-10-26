@echo off

java -Dlog4j.configuration=../log4j/log4j-remote.lcf -classpath .;../lib/log4j-1.2.15.jar kamoru.remote.RemoteCenter 165.243.166.132 13000 %1
