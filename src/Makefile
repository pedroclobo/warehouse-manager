PROJ = ggc
UILIB = po-uilib.jar

all::
	javac -cp $(UILIB):. `find $(PROJ) -name "*.java"`
clean::
	rm -f $(shell find . | egrep "*.class|*.diff|*.outhyp|*.dat")
run:
	java -cp $(UILIB):. $(PROJ).app.App
compile:
	jar cvf proj.jar $(shell find . -name "*.java")
test:
	./runtests.sh
