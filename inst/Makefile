all: ltgcheck.jar
	rm -rf TBUILD
	mkdir TBUILD
	javac -d TBUILD Installer.java
	(cd .. ; git log -64 ) > TBUILD/changelog
	echo >> TBUILD/changelog
	echo --- Earlier changes not shown --- >> TBUILD/changelog
	cp ../ltgcheck.jar TBUILD/ltgcheck.jar
	cp /usr/share/java/libbzdev-base.jar TBUILD/libbzdev-base.jar
	cp ../ltgcheck.1 TBUILD/ltgcheck.1
	cp ../copyright TBUILD/copyright
	gzip -9 < TBUILD/ltgcheck.1 > TBUILD/ltgcheck.1.gz
	groff -T html ../ltgcheck.1 > TBUILD/ltgcheck.html
	echo '#!/bin/sh' > TBUILD/ltgcheck.sh
	echo 'JAVA -jar LTGCHECKDIR/ltgcheck.jar "$$@"' >> TBUILD/ltgcheck.sh
	echo '@JAVA -jar LTGCHECKDIR/ltgcheck.jar %*' > TBUILD/ltgcheck.cmd
	echo '@JAVAW -jar LTGCHECKDIR/ltgcheck.jar %*'> TBUILD/ltgcheckw.cmd
	jar cfm ltgcheck-install.jar install.mf -C TBUILD .

ltgcheck.jar: ../classes/LTGCheck.class manifest.mf
	jar cfm ltgcheck.jar manifest.mf  -C ../classes .

../classes/LTGCheck.class: ../src/LTGCheck.java 
	(cd .. ; make ltgcheck.jar)

clean:
	rm -rf TBUILD ltgcheck.jar

superclean: clean
	rm -f ltgcheck-install.jar
