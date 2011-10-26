@echo off

set _JAVA_OPTS=%JAVA_OPTS% -Xms32m -Xmx32m

rem 옵션 설정
rem backup_properties		WAS스레드별로 로그를 구분한 파일을 만들지 여부

set KAMORU_OPTS=-Dbackup_properties=F:\data\workspace\System_Backup\conf\backup.properties

rem Usage : SystemBackup
java %_JAVA_OPTS% %KAMORU_OPTS% -classpath .;./bin; kamoru.system.backup.SystemBackup
