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
             environment {
        GIT_CREDS = credentials('github-token')
        HELM_GIT_REPO_URL = "github.com/madhubala2022/rsvpapp-helm-cicd.git"
        GIT_REPO_EMAIL = 'madhubala.ravichandran@mindtree.com'
        GIT_REPO_BRANCH = "master"
          
       // Update above variables with your user details
      }
            steps {
               script{
                    def imageTag = load 'src/image.groovy'
                    if (env.BRANCH_NAME == 'dev') {
                    imageTag.chartUpdation()
                  }
                  else{
                        sh "echo 'Hello from ${env.BRANCH_NAME} branch! skipping deploy stage'"
                    }
                    
                }
        }
    } 
}
}



        
