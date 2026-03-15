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
        stage('Deploy to Nexus') {
    steps {
        // This wrapper handles the settings.xml creation automatically
        withCredentials([usernamePassword(credentialsId: 'nexus-credentials', 
                         passwordVariable: 'NEXUS_PWD', 
                         usernameVariable: 'NEXUS_USER')]) {
            sh """
                mvn deploy -DskipTests -s /dev/null \
                -DaltDeploymentRepository=nexus-snapshots::default::http://nexus-nexus-repository-manager:8081/repository/maven-snapshots/ \
                --settings <(echo "
                <settings>
                    <servers>
                        <server>
                            <id>nexus-releases</id>
                            <username>${NEXUS_USER}</username>
                            <password>${NEXUS_PWD}</password>
                        </server>
                        <server>
                            <id>nexus-snapshots</id>
                            <username>${NEXUS_USER}</username>
                            <password>${NEXUS_PWD}</password>
                        </server>
                    </servers>
                </settings>")
            """
        }
    }
}
    }
}