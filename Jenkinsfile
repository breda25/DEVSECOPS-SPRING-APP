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
        withCredentials([string(credentialsId: 'NVD-API-KEY', variable: 'NVD_KEY')]) {
            environment {
                JAVA_OPTS = "-Xmx1024m"
            }
            dependencyCheck additionalArguments: "--nvdApiKey ${NVD_KEY} --format HTML", odcInstallation: 'DP-Check'
        }
    }
}
        stage('Security Scan: SonarQube') {
    steps {
        withSonarQubeEnv('SonarQube') { // 'SonarQube' must match the name in System settings
            sh 'mvn sonar:sonar -Dsonar.token=$SONAR_AUTH_TOKEN'
        }
    }
}
        stage('Build & Package') {
            steps { sh 'mvn package -DskipTests' }
        }
    }
}