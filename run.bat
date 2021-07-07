@echo off
echo This requires a JDK. If you see a bunch of errors, that's why.
echo.

rem copying paragraphs
copy /a "paragraphs.txt" "src/paragraphs.txt" >nul

rem building
cd src
javac Paragraph.java
javac ParagraphCollection.java
javac Typing.java
javac TypingUser.java

rem running
java Typing

rem cleanup
del Paragraph.class >nul
del ParagraphCollection.class >nul
del Typing.class >nul
del TypingUser.class >nul
timeout 1 /nobreak >nul
del paragraphs.txt >nul
