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
           environment{
              GIT_CREDS = credentials('github-token')
            }
            steps {
               script{
                    def imageTag = load 'src/image.groovy'
                    imageTag.chartUpdation()
                }
                sh "git push https://$GIT_CREDS_USR:$GIT_CREDS_PSW@github.com/$GIT_CREDS_USR/rsvpapp-helm-cicd.git"
        }
            
    } 
}
}
