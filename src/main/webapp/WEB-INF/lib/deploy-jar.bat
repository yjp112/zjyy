mvn deploy:deploy-file -Dfile=menu_authority-3.0.0-SNAPSHOT.jar -DgroupId=com.supconit.honeycomb.business -DartifactId=menu_authority -Dversion=3.0.0-SNAPSHOT -Dpackaging=jar -Durl=http://10.10.100.211:8081/nexus/content/repositories/supconit-snapshot -DrepositoryId=supconit-snapshot

pause