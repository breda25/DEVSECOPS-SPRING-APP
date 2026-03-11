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
            environment {
                JAVA_OPTS = "-Xmx1024m"
            }
            steps {
                withCredentials([string(credentialsId: 'NVD-API-KEY', variable: 'NVD_KEY')]) {
                    // Added --data parameter to point to the Volume Mount
                    dependencyCheck additionalArguments: """
                        --nvdApiKey ${NVD_KEY} 
                        --format HTML 
                        --data /home/jenkins/agent/nvd-data
                    """, odcInstallation: 'DP-Check'
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