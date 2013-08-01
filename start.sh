#!/bin/sh
# $Id: start.sh,v 1.5 2003/12/22 20:14:41 zero_data Exp $

export JAVA_HOME=/usr/java/j2sdk1.4.2_02
export JDK_HOME=/usr/java/j2sdk1.4.2_02
export CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib:/home/m0554/src
export PATH=$JAVA_HOME/bin:$PATH

java -Xprof -DCONFIGFILE=/home/m0554/src/eclipse_workdir/SortImagesGUI/sortimages.properties -jar $1 $2
