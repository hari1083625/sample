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
  container('tools') {
    sh "git clone https://${env.HELM_GIT_REPO_URL}"
    sh "git config --global user.email ${env.GIT_REPO_EMAIL}"
    // install wq
    sh "wget https://github.com/mikefarah/yq/releases/download/v4.9.6/yq_linux_amd64.tar.gz"
    sh "tar xvf yq_linux_amd64.tar.gz"
    sh "mv yq_linux_amd64 /usr/bin/yq"
    sh "git checkout -b ${env.GIT_REPO_BRANCH}"
    dir("rsvpapp-helm-cicd") {
    sh "git checkout ${env.GIT_REPO_BRANCH}"
    //install done
    sh '''#!/bin/bash
    echo ${env.GIT_REPO_EMAIL}
    echo ${env.GIT_COMMIT}
    ls -lth
    yq eval '.image.repository = ${env.IMAGE_REPO}' -i values.yaml
    yq eval '.image.tag = ${env.GIT_COMMIT} -i values.yaml
    cat values.yaml
    pwd
    git add values.yaml
    git commit -m 'Triggered Build'
    git push https://$GIT_CREDS_USR:$GIT_CREDS_PSW@github.com/$GIT_CREDS_USR/rsvpapp-helm-cicd.git
    '''
    }
  }
}

return this
