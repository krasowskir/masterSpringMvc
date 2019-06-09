pipeline {
    agent any
    triggers {
      pollScm('*/5 * * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}
