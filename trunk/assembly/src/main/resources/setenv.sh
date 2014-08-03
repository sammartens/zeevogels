#!/bin/sh
# {{ansible_managed}}
JAVA_OPTS="$JAVA_OPTS -Dproperties.location=file://%tomcat_apps_dir%/application.properties"
JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF8" 
JAVA_OPTS="$JAVA_OPTS -Dhttp.proxyHost=%http_proxy_host% -Dhttp.proxyPort=%http_proxy_port%"
JAVA_OPTS="$JAVA_OPTS -Dhttps.proxyHost=%http_proxy_host% -Dhttps.proxyPort=%http_proxy_port%"
JAVA_OPTS="$JAVA_OPTS -Dhttp.nonProxyHosts=%no_proxy_java%"