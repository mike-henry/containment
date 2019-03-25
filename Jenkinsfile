pipeline {
    
    agent any
    stages {
      stage('Build Only') { 
        steps {
          sh 'chmod u+x ./gradlew && ./gradlew  build' 
      }
      stage('publish ') { 
        steps {
          sh ' ./gradlew  deploy' 
      }
    }
  }
}
