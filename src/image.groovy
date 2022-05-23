print("Loaded class Image.groovy")

env.IMAGE_REPO = "madhubala1997/rsvp"
env.REPO = "rsvp"
env.HELM_REPO = "rsvpapp-helm-cicd"
env.GIT_CREDS = credentials('github-token')
env.HELM_GIT_REPO_URL = "github.com/madhubala2022/rsvpapp-helm-cicd.git"
env.GIT_REPO_EMAIL = "madhubala.ravichandran@mindtree.com"
env.GIT_REPO_BRANCH = "master"
env.WEBHOOK = "https://mindtreeonline.webhook.office.com/webhookb2/329f59da-c0a6-4edb-b0a1-cbd712509488@85c997b9-f494-46b3-a11d-772983cf6f11/JenkinsCI/22f5c0f87ca8436a82684dc70b5d509b/961ab056-0929-4c45-9d67-de9017c84fb0"


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

String chartUpdation() {
  print("updation working")
  echo $env.GIT_REPO_EMAIL
  echo $env.GIT_COMMIT
}

return this
