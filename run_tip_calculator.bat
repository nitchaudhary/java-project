@echo off
title Tip Calculator - Java Swing App
echo Compiling the Java program...

javac SwingTipCalculator.java

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed. Please check the code for errors.
    pause
    exit /b
)

echo.
echo Compilation successful!
echo Launching the application...
echo.

java SwingTipCalculator

pause
