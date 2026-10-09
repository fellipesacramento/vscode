@echo off
setlocal
if not exist "%~dp0..\workspace\" (
  echo Workspace nao encontrado: %~dp0..\workspace
  exit /b 1
)
call "%~dp0VSCode-win32-x64-1.140.0\bin\code.cmd" --reuse-window "%~dp0desenvolvimento.code-workspace"
exit /b %ERRORLEVEL%
