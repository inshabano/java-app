#!/usr/bin/env groovy
def gv
pipeline{
  agent any
  environment{
     VERSION = '1.0'
//      SERVER_CREDENTIALS = credentials('server-credentials')
             MAVEN_HOME = tool name: 'maven-3', type: 'maven'
             PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"

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
     stage("init"){
       steps{
         script{
          gv = load "script.groovy"
         }
       }
     }
     stage("increment version"){
        steps{
           script{
            echo "Increment version"
            sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
//          (in case of versioning minor and major - parsedVersion.nextMajorVersion  & parsedVersion.nextMinorVersion
            def match = readFile('pom.xml') =~ '<version>(.+)</version>'
                def version = match[0][1]
                env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
           }
     }
     }
     stage("build"){
        steps{
           script{
              gv.build()
           }
           }
     }
     stage("build image"){
        when{
            expression {
              BRANCH_NAME == 'features'
            }
          }
        steps{
          script{
            gv.buildImage()
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
          script{
             gv.deploy()
          }
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
}

//checking for webhook trigger