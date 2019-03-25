pipeline {
    
  agent any
     environment {
        NEXUS_USER     = "jenkins"
        NEXUS_SECRET = "publisher"
    }
  stages {
    stage('Build Only') { 
      steps {
        sh 'chmod u+x ./gradlew && ./gradlew  build' 
      }
    }
    stage('publish ') { 
      steps {
        sh ' ./gradlew  upload' 
      }
    }
  }
}
