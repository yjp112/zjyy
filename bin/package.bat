title='��ά����ƽ̨ ���'
@echo off
echo [INFO] Package the war in target dir.

cd %~dp0
cd ..
call mvn -U clean package -Dmaven.test.skip=true
cd bin
pause
