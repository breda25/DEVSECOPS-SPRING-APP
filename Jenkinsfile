pipeline {
    agent {
        label 'jenkins-agent' // MUST match the label you set in Cloud settings
    }
    tools {
        maven 'maven' 
    }
    stages {
        stage('Compile') {
            steps { sh 'mvn clean compile' }
        }
        
        stage('Security Scan: OWASP') {
            steps {
                // Ensure the dir exists before scanning
                sh 'mkdir -p /home/jenkins/agent/nvd-data'
                withCredentials([string(credentialsId: 'NVD-API-KEY', variable: 'NVD_KEY')]) {
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
                withSonarQubeEnv('SonarQube') { 
                    // Use the Maven wrapper for sonar
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build & Package') {
            steps { sh 'mvn package -DskipTests' }
        }
    }
}