@echo off

java -Dlog4j.configuration=../log4j/log4j-remote.lcf -classpath .;../lib/log4j-1.2.15.jar kamoru.remote.RemoteAgent 13
