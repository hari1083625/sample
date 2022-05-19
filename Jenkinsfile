pipeline {

  options {

    office365ConnectorWebhooks([

      [name: "Office 365",

      url: "${https://mindtreeonline.webhook.office.com/webhookb2/329f59da-c0a6-4edb-b0a1-cbd712509488@85c997b9-f494-46b3-a11d-772983cf6f11/IncomingWebhook/716048a3dbcb4ebebc91cdbbf1c536a1/961ab056-0929-4c45-9d67-de9017c84fb0}",

      notifyBackToNormal: true,

      notifyFailure: true,

      notifyRepeatedFailure: true,

      notifySuccess: true,

      notifyAborted: true]

        ])

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
                             --destination=madhubala1997/rsvp:$GIT_COMMIT
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
