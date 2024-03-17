pipeline{
  agent any
  environment{
     VERSION = '1.0'
//      SERVER_CREDENTIALS = credentials('server-credentials')
     environment {
             MAVEN_HOME = tool name: 'maven-3', type: 'maven'
             PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
         }
  }
//   tool {
//     maven 'maven-3'
//   }
  parameters{
//   string(name: 'VERsION', defaultValue: '1.0', description: 'The version')
// to use parameter = params.name
  choice(name: 'Version', choices: ['1.0', '1.1', '1,2'], description: 'choose version')
  booleanParam(name: 'Execute', defaultValue: true, description: '')
  }
  stages{
     stage("build"){
        steps{
           echo "building stage"
           echo "Building version ${VERSION}"
           sh 'mvn clean package'
           }
     }
     stage("build image"){
        steps{
          echo 'Building docker image'
          withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PSWD' )]){
            sh 'docker build -t inshabano/demo-app:javamaven-2.0 .'
            sh "echo ${PSWD} | docker login -u ${USER} --password-stdin"
            sh 'docker push inshabano/demo-app:javamaven-2.0'
          }
        }
     }
     stage("test"){
        when{
           expression {
             BRANCH_NAME == 'master' || BRANCH_NAME == 'main' &&
             params.Execute == true
           }
          }
        steps{
          echo "test stage"
          sh 'mvn test'
       }
     }
     stage("deploy"){
        steps{
        echo "deploying stage"
        sh 'mvn deploy'
//         withCredentials([
//          UsernamePassword(credentials: 'sever-credentials', usernameVariable: USER, passwordVariable: PASSWORD)
//         ]){
//         }
        }
     }
  }
  post {
          success {
              // Send notification or perform additional actions on success
              echo 'Build successful!'
          }
          failure {
              // Send notification or perform additional actions on failure
              echo 'Build failed!'
          }

}