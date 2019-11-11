
REM start tests with standalone ChromeDrive.exe running on localhost:9515
java -DRemoteDriverURL=http://localhost:9515 -DExecPath=RunOpenFin.bat -DExecArgs="--config=%~dp0\app_local.json" -jar "..\target\hello-openfin-selenium-jar-with-dependencies.jar"
