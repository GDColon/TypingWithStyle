@echo off
echo This requires a JDK. If you see a bunch of errors, that's why.
echo.
rem CD to src directory
cd %0/../src
if not exist Typing.class (

	rem Build all of that java code to .class files

	javac *

	cd ../
	rem Copying the paragraphs.txt file to the source directory. It will crash if we dont.
	copy /a "paragraphs.txt" "src/paragraphs.txt"
	cd src
)

rem Running the program duh
java Typing
