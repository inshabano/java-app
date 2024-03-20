def build() {
    echo "building stage"
    sh 'mvn clean package'
}
def buildimage() {
    echo 'Building docker image'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER' )]){
        sh "docker build -t inshabano/demo-app:${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push inshabano/demo-app:${IMAGE_NAME}"
    }
}
def deploy() {
    echo "deploying stage"
    sh 'mvn deploy'
}
return this