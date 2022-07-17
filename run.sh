echo "This requires a JDK. If you see a bunch of errors, that's why.
"
#Getting base directory
cd $0/..
#Compile java files if they aren't already compiled and copy paragraphs.txt to the source folder
cd src
if [[ $(find -name Typing.class) == "" ]]
then
	javac ParagraphCollection.java Paragraph.java Typing.java TypingUser.java
	cp ../paragraphs.txt ./
fi
#Execute the program
java Typing
