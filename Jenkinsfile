pipeline {
    
    agent any
    stages {
      stage('Pre-Build Only') { 
        steps {
          sh 'chmod u+x ./gradlew && ./gradlew  build' 
      }
      stage('Build Only') { 
        steps {
          sh './gradlew  build' 
      }
      stage('Publish') { 
        steps {
          sh './gradlew  deploy' 
      }
    }
  }
}
