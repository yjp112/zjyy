title="��ά����ƽ̨"
@echo off
echo [INFO] Use maven tomcat7-maven-plugin run the project.
cd %~dp0
cd ..

set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
call mvn -U tomcat7:run

cd bin
pause
