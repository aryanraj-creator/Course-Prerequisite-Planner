@echo off
mvn clean test
if errorlevel 1 exit /b 1
mvn exec:java
