pipeline {
    agent any
    tools {
     maven 'meinMaven'
    }
    stages {
        stage('Build') {
            steps {
              sh 'mvn clean install'
            }
        }
    }
}
