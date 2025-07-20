VERSION = 0.2
DATE = $(shell date -R)

SYS_BINDIR = /usr/bin
SYS_MANDIR = /usr/share/man
SYS_DOCDIR = /usr/share/doc/ltgcheck
SYS_LTGCHECKDIR = /usr/share/ltgcheck

SED_LTGCHECK = $(shell echo $(SYS_BINDIR)/ltgcheck | sed s/\\//\\\\\\\\\\//g)
SED_LTGCHECKDIR = $(shell echo $(SYS_LTGCHECKDIR) | sed  s/\\//\\\\\\\\\\//g)

BINDIR=$(DESTDIR)$(SYS_BINDIR)
MANDIR = $(DESTDIR)$(SYS_MANDIR)
DOCDIR = $(DESTDIR)$(SYS_DOCDIR)

LTGCHECKDIR = $(DESTDIR)$(SYS_LTGCHECKDIR)

all: deb

classes:
	mkdir -p classes

JFILES = $(wildcard src/*.java)
FLAGS = -Xlint:deprecation -Xlint:unchecked

ltgcheck.jar: $(JFILES) classes
	javac --release 11 $(FLAGS) -d classes \
	      -classpath /usr/share/java/libbzdev.jar $(JFILES)
	jar cfe ltgcheck.jar LTGCheck -C classes .

version:
	@echo $(VERSION)

install: ltgcheck.jar
	install -d $(BINDIR)
	install -d $(MANDIR)/man1
	install -d $(DOCDIR)
	install -d $(LTGCHECKDIR)
	install -m 0644 ltgcheck.jar $(LTGCHECKDIR)
	sed -e s/LTGCHECKDIR/$(SED_LTGCHECKDIR)/ < ltgcheck.sh > ltgcheck.tmp
	install -m 0755 -T ltgcheck.tmp $(BINDIR)/ltgcheck
	rm ltgcheck.tmp
	sed -e s/VERSION/$(VERSION)/ ltgcheck.1 | gzip -n -9 > ltgcheck.1.gz
	install -m 0644 ltgcheck.1.gz $(MANDIR)/man1
	gzip -n -9 < changelog > changelog.gz
	install -m 0644 changelog.gz $(DOCDIR)
	rm changelog.gz
	install -m 0644 copyright $(DOCDIR)

DEB = ltgcheck_$(VERSION)_all.deb

deb: $(DEB)

debLog:
	sed -e s/VERSION/$(VERSION)/ deb/changelog.Debian \
		| sed -e "s/DATE/$(DATE)/" \
		| gzip -n -9 > changelog.Debian.gz
	install -m 0644 changelog.Debian.gz $(DOCDIR)
	rm changelog.Debian.gz

$(DEB): deb/control copyright changelog deb/changelog.Debian \
		ltgcheck.jar ltgcheck.sh ltgcheck.1  Makefile
	mkdir -p BUILD
	(cd BUILD ; rm -rf usr DEBIAN)
	mkdir -p BUILD/DEBIAN
	$(MAKE) install DESTDIR=BUILD debLog
	sed -e s/VERSION/$(VERSION)/ deb/control > BUILD/DEBIAN/control
	fakeroot dpkg-deb --build BUILD
	mv BUILD.deb $(DEB)
	(cd inst; make)
	cp inst/ltgcheck-install.jar ltgcheck-install-$(VERSION).jar

