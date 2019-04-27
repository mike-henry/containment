def app  
pipeline {
  agent  any
    environment {
      NEXUS_USER     = "jenkins"
      NEXUS_SECRET = credentials('jenkins-nexus-secret')
      branch = "${env.BRANCH_NAME}" ;
    }
  
  stages {
   

    stage('Build Jars') { 
      steps {
        sh 'chmod u+x ./gradlew && ./gradlew --no-daemon clean build' 
      }
    }
    stage('Build Image') { 
       steps {
        sh 'img build -t containment .'
      }
    }
    stage('publish jar') { 
      when {
            expression {branch == 'develop'}
      }
      steps {
          echo 'I only upload on the develop branch'
          sh ' ./gradlew --no-daemon upload' 
      }
    }
  }
}
