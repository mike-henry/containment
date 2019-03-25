pipeline {
    
    agent any
    stages {
      stage('Build Only') { 
        steps {
          sh 'chmod u+x ./gradlew && ./gradlew  build' 
      }
      stage('Build Only') { 
        steps {
          sh './gradlew  build' 
      }
      stage('Build Only') { 
        steps {
          sh './gradlew  deploy' 
      }
    }
  }
}
