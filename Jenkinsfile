pipeline {
    agent an
    tools {
     maven: 'apache-maven-3.6.1'
    }
    stages {
        stage('Build') {
            steps {
              sh 'mvn clean install'
            }
        }
    }
}
