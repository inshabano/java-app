pipeline{
  agent any
  environment{
     VERSION = '1.0'
//      SERVER_CREDENTIALS = credentials('server-credentials')
  }
  tool{
    maven 'maven-3'
  }
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
           sh 'mvn install'
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
       }
     }
     stage("deploy"){
        steps{
        echo "deploying stage"
//         withCredentials([
//          UsernamePassword(credentials: 'sever-credentials', usernameVariable: USER, passwordVariable: PASSWORD)
//         ]){
//         }
        }
     }
  }

}