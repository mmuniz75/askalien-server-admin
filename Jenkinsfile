node {
   def mvnHome
   stage('checkout') { // for display purposes
      git 'https://github.com/mmuniz75/askalien-server-admin.git'
      mvnHome = tool 'maven'
   }
   stage('build') {
     sh "'${mvnHome}/bin/mvn' clean compile"
   }

   stage('test') {
     env.LUCENE_INDEX_DIR="/lucene"
     sh "'${mvnHome}/bin/mvn' -DSPRING_DATASOURCE_URL=jdbc:postgresql://${POSTGRESQL_TEST_SERVICE_HOST}:5432/mythidb_test -DSPRING_DATASOURCE_USERNAME=${POSTGRESQL_TEST_USER} -DSPRING_DATASOURCE_PASSWORD=${POSTGRESQL_TEST_PASSWORD} -DSPRING_JPA_HIBERNATE_DDL_AUTO=none -DSPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect test"
   }

   stage('package') {
     sh "'${mvnHome}/bin/mvn' -Dmaven.test.skip=true package"
   }

   stage('config') {
     sh "sed -i -e 's|<POSTGRESQL_SERVICE_HOST>|${POSTGRESQL_SERVICE_HOST}|g' deploy/.ebextensions/environmentvariables.config"
     sh "sed -i -e 's|<POSTGRESQL_PASSWORD>|${POSTGRESQL_PASSWORD}|g' deploy/.ebextensions/environmentvariables.config"
     sh "sed -i -e 's|<POSTGRESQL_USER>|${POSTGRESQL_USER}|g' deploy/.ebextensions/environmentvariables.config"
   }
   
    stage('Set AWS archive') {
     sh "cp target/*.jar deploy"   
    } 
    
    stage('Deploy on AWS') {
      env.PATH="/usr/share/tomcat8/.local/bin:${env.PATH}"
      sh "cd deploy && eb deploy"
    }    
   
}