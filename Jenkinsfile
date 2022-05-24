pipeline {
    agent any
    stages {
        stage('Step1') {
        if (env.BRANCH_NAME == 'master') {
            echo 'Hello from main branch'
        } else {
            sh "echo 'Hello from ${env.BRANCH_NAME} branch!'"
        }
    }
    }
}
