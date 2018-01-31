title='运维管理平台'
@echo off
echo [INFO] Use maven jetty-maven-plugin run the project.

cd %~dp0
cd ..

set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
call mvn -U jetty:run -Djetty.port=8080

cd bin
pause
ause
