#!/bin/sh
echo "*********************************************************"
echo "Running git pre-push hook. Running Static analysis... "
echo "*********************************************************"

./gradlew detekt ktlintCheck --daemon

status=$?

if [ "$status" = 0 ] ; then
    echo "Static analysis found no problems."
    exit 0
else
    echo "*********************************************************"
    echo "       ********************************************      "
    echo 1>&2 "Static analysis found violations it could not fix."
    echo "Run ./gradlew ktlintFormat to fix formatting related issues."
    echo "       ********************************************      "
    echo "*********************************************************"
    exit 1
fi



#echo "Running static code analysis..."
#
## Run Detekt, KtLint and Checkstyle static analysis
#./gradlew detekt ktlintCheck --daemon
#
#status=$?
#
#if [ "$status" = 0 ] ; then
#    echo "Static analysis found no problems."
#    exit 0
#else
#    echo 1>&2 "Static analysis found violations! Fix then before pushing your code!"
#    echo "See generated reports above or in /app/build/reports folder"
#    exit 1
#fi
