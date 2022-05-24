pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
                echo $env.BRANCH_NAME
            }
        }
    }
}
