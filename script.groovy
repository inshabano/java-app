def build() {
    echo "building stage"
    sh 'mvn clean package'
}
def buildimage() {
    echo 'Building docker image'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PSWD', usernameVariable: 'USER' )]){
        sh 'docker build -t inshabano/demo-app:javamaven-2.0 .'
        sh "echo $PSWD | docker login -u $USER --password-stdin"
        sh 'docker push inshabano/demo-app:javamaven-2.0'
    }
}
def deploy() {
    echo "deploying stage"
    sh 'mvn deploy'
}
return this