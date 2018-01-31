title='运维管理平台 打包'
@echo off
echo [INFO] Package the war in target dir.

cd %~dp0
cd ..
call mvn -U clean package -Dmaven.test.skip=true
cd bin
pause
