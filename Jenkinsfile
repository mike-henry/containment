pipeline {
    

     agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Main Compile') {
            steps {
                echo 'Sample containtment stage' 
            }
        }
    }
}
