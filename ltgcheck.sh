#!/bin/sh
LIB=/usr/share/java/libbzdev.jar
java -classpath LTGCHECKDIR/ltgcheck.jar:$LIB LTGCheck "$@"

