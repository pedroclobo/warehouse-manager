PROJ = ggc
UILIB = po-uilib.jar

all::
	javac -cp $(UILIB):. `find $(PROJ) -name "*.java"` && \
	java -cp $(UILIB):. $(PROJ).app.App
clean:
	rm -f $(shell find $(PROJ) -name "*.class")
