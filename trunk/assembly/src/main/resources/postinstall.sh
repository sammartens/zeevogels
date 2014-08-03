#!/bin/bash -e

# replace variables
sed -i \
    -e "s/%vlan%/${vlan}/g" \
    -e "s/%ZUIL%/${ZUIL}/g" \
    -e "s/%zuil%/${zuil}/g" \
    -e "s/%ZUILURL%/${ZUILURL}/g" \
    -e "s@%tomcat_apps_dir%@${tomcat_apps_dir}@g" \
	-e "s@%tomcat_home_dir%@${tomcat_home_dir}@g" \
    -e "s/%db_username%/${db_username}/g" \
    -e "s/%db_password%/${db_password}/g" \
    -e "s/%zeevogels_db_schema%/${zeevogels_db_schema}/g" \
    -e "s/%zeevogels_db_appname%/${zeevogels_db_appname}/g" \
	-e "s/%http_proxy_host%/${http_proxy_host}/g" \
	-e "s/%http_proxy_port%/${http_proxy_port}/g" \
	-e "s/%no_proxy_java%/${no_proxy_java}/g" \
	-e "s/%INBO_ZUILURL%/${INBO_ZUILURL}/g" \
    -e "s/%INBO_DB_MACHINE%/${INBO_DB_MACHINE}/g" \
    -e "s/%INBO_DB_PORT%/${INBO_DB_PORT}/g" \
    ${tomcat_apps_dir}/*.xml \
    ${tomcat_apps_dir}/application.properties \
    ${tomcat_apps_dir}/cleanup.sh \
	${tomcat_apps_dir}/setenv.sh
	
# Symlink
ln -f -s ${tomcat_apps_dir}/context.xml ${tomcat_home_dir}/conf/Catalina/localhost/zeevogels.xml
ln -f -s ${tomcat_apps_dir}/lib/jtds-1.2.4.jar ${tomcat_home_dir}/lib/jtds-1.2.4.jar
ln -f -s ${tomcat_apps_dir}/setenv.sh ${tomcat_home_dir}/bin/setenv.sh

#add URIEncoding to Connector in server.xml
sed -i -e 's@<Connector port="8080" protocol="HTTP/1.1".*@<Connector port="8080" protocol="HTTP/1.1" URIEncoding="UTF-8"@' ${tomcat_home_dir}/conf/server.xml