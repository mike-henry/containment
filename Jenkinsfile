pipeline {
    
  agent any
     environment {
        NEXUS_USER     = "jenkins"
        NEXUS_SECRET = credentials('jenkins-nexus-secret')
        branch = "${env.BRANCH_NAME}" ;
    }

  
  stages {
   

    stage('Build Only') { 
      steps {
        sh 'chmod u+x ./gradlew && ./gradlew --no-daemon build' 
      }
    }
    stage('publish') { 
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
