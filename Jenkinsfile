pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
                if("$env.BRANCH_NAME" == 'master')
{
  echo "This is the master branch"
}
            }
        }
    }
}
