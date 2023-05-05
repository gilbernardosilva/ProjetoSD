@REM ************************************************************************************
@REM Description: run
@REM Author: Rui S. Moreira
@REM Date: 10/04/2018
@REM ************************************************************************************
@REM Script usage: runclient <role> (where role should be: producer / consumer)
call setenv client

cd %ABSPATH2CLASSES%
java -cp %CLASSPATH% %JAVAPACKAGEROLE%.%OBSERVER_CLASS_PREFIX% %REGISTRY_HOST% %REGISTRY_PORT% %SERVICE_NAME_ON_REGISTRY% -Djava.rmi.server.codebase=%SERVER_CODEBASE% -Djava.rmi.server.hostname=%SERVER_RMI_HOST% -Djava.security.policy=%SERVER_SECURITY_POLICY% %JAVAPACKAGEROLE%.%SERVER_CLASS_PREFIX%%SERVER_CLASS_POSTFIX%

cd %ABSPATH2SRC%/%JAVASCRIPTSPATH%