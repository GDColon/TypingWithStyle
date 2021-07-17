@echo off
echo This requires a JDK. If you see a bunch of errors, that's why.
echo.

rem Copying the paragraphs.txt file to the source directory. It will crash if we dont.
copy /a "paragraphs.txt" "src/paragraphs.txt" >nul

rem Build all of that java code to .class files. Since we dont build a .jar file here we dont need a manifest
cd src
javac *

rem Running the program duh
java Typing

rem This code go clean clean
del *.class
timeout 1 /nobreak > nul
del paragraphs.txt > nul
