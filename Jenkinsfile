pipeline {
   options {
        office365ConnectorWebhooks([[
                    startNotification: true,
                    notifySuccess: true,
                    notifyFailure: true,
                    notifyAborted: true,
                    notifyBackToNormal: true,
                    notifyNotBuilt: true,
                    notifyRepeatedFailure: true,
                        url: '''https://mindtreeonline.webhook.office.com/webhookb2/329f59da-c0a6-4edb-b0a1-cbd712509488@85c997b9-f494-46b3-a11d-772983cf6f11/IncomingWebhook/716048a3dbcb4ebebc91cdbbf1c536a1/961ab056-0929-4c45-9d67-de9017c84fb0'''
            ]]
        )
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
      REPO = "rsvp"
      HELM_REPO = "rsvpapp-helm-cicd"
      GIT_CREDS = credentials('github-token')
      HELM_GIT_REPO_URL = "github.com/madhubala2022/rsvpapp-helm-cicd.git"
      GIT_REPO_EMAIL = "madhubala.ravichandran@mindtree.com"
      GIT_REPO_BRANCH = "master"
      WEBHOOK = "https://mindtreeonline.webhook.office.com/webhookb2/329f59da-c0a6-4edb-b0a1-cbd712509488@85c997b9-f494-46b3-a11d-772983cf6f11/JenkinsCI/22f5c0f87ca8436a82684dc70b5d509b/961ab056-0929-4c45-9d67-de9017c84fb0"
  }
 
  stages {
    stage('Build & Push Image') {
      steps {
         script{
            def imageBuild = load 'src/image.groovy'
            imageBuild.buildImage()
         }
      }
    }
    stage('Deploy') {
      steps {
        script{
            def updation = load 'src/image.groovy'
            updation.chartUpdation()
         }
        }
      }
    } 
}
