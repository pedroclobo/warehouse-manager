PROJ = ggc
UILIB = po-uilib.jar

all::
	javac -cp $(UILIB):. `find $(PROJ) -name "*.java"`
clean::
	rm -f $(shell find $(PROJ) -name "*.class")
run:
	java -cp $(UILIB):. $(PROJ).app.App
test:
	./runtests.sh
