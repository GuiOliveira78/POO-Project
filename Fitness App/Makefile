JFLAGS = -d ./out -sourcepath ./src
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = $(wildcard ./src/*.java)

default: classes

classes: $(CLASSES:.java=.class)

run:
	java -cp ./out Main

clean:
	$(RM) ./out/*.class