print("Loaded class Image.groovy")

String buildImage() {
  container('kaniko') {
          script {
            sh "echo ${env.GIT_COMMIT}"
            sh '''
            /kaniko/executor --dockerfile `pwd`/Dockerfile \
                             --context `pwd` \
                             --destination=madhubala1997/$REPO:$GIT_COMMIT
            '''
          }
        }
}

return this
