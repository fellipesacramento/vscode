@echo off
setlocal
set "VSCODE_EXTENSIONS=D:\Caches\VSCode"
set "JAVA_HOME=%~dp0..\jdk\jdk-21_windows-x64_bin\jdk-21.0.12.1"
set "MAVEN_HOME=%~dp0..\maven\apache-maven-3.9.16"
set "M2_HOME=%MAVEN_HOME%"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"
if not exist "%JAVA_HOME%\bin\java.exe" (
  echo Java 21 nao encontrado: %JAVA_HOME%
  exit /b 1
)
if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  echo Maven nao encontrado: %MAVEN_HOME%
  exit /b 1
)
if not exist "%~dp0..\workspaces\" (
  echo Workspaces nao encontrados: %~dp0..\workspaces
  exit /b 1
)
if not exist "%VSCODE_EXTENSIONS%\" (
  echo Pasta de extensoes nao encontrada: %VSCODE_EXTENSIONS%
  exit /b 1
)
set "WORKSPACE_ALVO=%~dp0desenvolvimento.code-workspace"
if /i "%~1"=="utilitarios" set "WORKSPACE_ALVO=%~dp0..\workspaces\workspace_utilitarios\ofx\ofx"
if not exist "%WORKSPACE_ALVO%" (
  echo Projeto nao encontrado: %WORKSPACE_ALVO%
  exit /b 1
)
call "%~dp0VSCode-win32-x64-1.140.0\bin\code.cmd" --extensions-dir "%VSCODE_EXTENSIONS%" --reuse-window "%WORKSPACE_ALVO%"
exit /b %ERRORLEVEL%
