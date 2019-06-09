pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
            withEnv( ["$MAVEN_HOME/bin"] ) {
              sh 'mvn clean install'
             }
            }
        }
    }
}
