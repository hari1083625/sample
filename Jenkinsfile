pipeline {

  options {
    ansiColor('xterm')
  }

  agent {
    kubernetes {
      label 'jenkins-slave'
             defaultContainer 'jnlp'
      yamlFile 'builder.yaml'
    }
  }
  environment {
      IMAGE_REPO = "madhubala1997/rsvp"
  }
  stages {
     stage('Build & Push Image') {
      steps {
        container('kaniko') {
          script {
            sh "echo ${env.GIT_COMMIT}"
            sh '''
            /kaniko/executor --dockerfile `pwd`/Dockerfile \
                             --context `pwd` \
                             --destination=madhubala1997/rsvp:${env.GIT_COMMIT}
            '''
          }
        }
      }
    }
    stage('Deploy') {
      environment {
        GIT_CREDS = credentials('github-token')
        HELM_GIT_REPO_URL = "github.com/madhubala2022/rsvpapp.git"
        GIT_REPO_EMAIL = "madhubala.ravichandran@mindtree.com"
        GIT_REPO_BRANCH = "master"
          
       // Update above variables with your user details
      }
      steps {
        container('tools') {
            sh "git clone https://${env.HELM_GIT_REPO_URL}"
            sh "git config --global user.email ${env.GIT_REPO_EMAIL}"
             // install wq
            sh "wget https://github.com/mikefarah/yq/releases/download/v4.9.6/yq_linux_amd64.tar.gz"
            sh "tar xvf yq_linux_amd64.tar.gz"
            sh "mv yq_linux_amd64 /usr/bin/yq"
            sh "git checkout -b master"
          dir("rsvpapp") {
              sh "git checkout ${env.GIT_REPO_BRANCH}"
            //install done
            sh '''#!/bin/bash
              echo $GIT_REPO_EMAIL
              echo $GIT_COMMIT
              ls -lth
              yq eval '.image.repository = env(IMAGE_REPO)' -i values.yaml
              yq eval '.image.tag = env(GIT_COMMIT)' -i values.yaml
              cat values.yaml
              pwd
              git add values.yaml
              git commit -m 'Triggered Build'
              git push https://$GIT_CREDS_USR:$GIT_CREDS_PSW@github.com/$GIT_CREDS_USR/rsvpapp.git
            '''
          }
        }
      }
    }   
  }
}
