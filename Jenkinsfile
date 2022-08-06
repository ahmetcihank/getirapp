pipeline {

  agent any
  
  tools {
   maven 'mymaven'
  
  }
  
  stages {
    stage("build"){
      steps{
        sh 'mvn clean'
      }
    }
    
    stage("test"){
      steps{
        sh 'mvn test'
      }
    }        
  }
  
}
