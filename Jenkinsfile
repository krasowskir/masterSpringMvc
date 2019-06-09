pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
            withEnv( ["/usr/local/apache-maven-3.6.1/bin"] ) {
              sh 'mvn clean install'
             }
            }
        }
    }
}
