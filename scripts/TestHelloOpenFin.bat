
REM start tests with standalone ChromeDrive.exe running on localhost:9515
java -DRemoteDriverURL=http://localhost:9515 -DExecPath=RunOpenFin.bat -DExecArgs="--config=%~dp0\app_local.json" -jar hello-openfin-jar-with-dependencies.jar sk.samplers.openfin.TestOpenFinApp
