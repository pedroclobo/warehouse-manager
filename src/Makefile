PROJ = ggc
UILIB = po-uilib.jar

all::
	javac -cp $(UILIB):. `find $(PROJ) -name "*.java"`
clean::
	find . | egrep '*.class|*.diff|*.outhyp|*.dat|esgotado*|proj.jar' | xargs \rm -f
run:
	java -cp $(UILIB):. $(PROJ).app.App
compile:
	jar cvf proj.jar $(shell find . -name "*.java")
test:
	./runtests.sh
