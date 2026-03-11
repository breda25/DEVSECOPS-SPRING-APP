pipeline {
    agent any
    tools {
        maven 'maven' 
    }
    stages {
        stage('Compile') {
            steps { sh 'mvn clean compile' }
        }
        stage('Security Scan: OWASP') {
            steps {
                dependencyCheck additionalArguments: '--format HTML', odcInstallation: 'DP-Check'
            }
        }
        stage('Security Scan: SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Build & Package') {
            steps { sh 'mvn package -DskipTests' }
        }
    }
}