#!/bin/sh

PROJECT_VERSION=0.1.2-SNAPSHOT
PATH_TO_JAR=./invoice-server-jsf-$PROJECT_VERSION.jar
echo "Starting H2 console ..."

start_console(){
    H2_DATABASE=`find ./h2*.jar  -printf "%f\n" | tail -1`
    echo 'Do not forget to close this window with CTRL-C after you are done with console'
    java -jar $H2_DATABASE -url 'jdbc:h2:file:./invoice-db/invoiceDB;AUTO_SERVER=TRUE' -user 'sa' -driver 'org.h2.Driver'
}

if [ ! -f h2*.jar ]; then
    unzip -d . $PATH_TO_JAR BOOT-INF/lib/h2*.jar
    mv BOOT-INF/lib/h2*.jar .
    rm -r BOOT-INF
    start_console
else
    start_console
fi
exit 0
