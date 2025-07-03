pipeline {

    agent any

    // agent {
    //    label "agent-tools-jdk"
    //}

    stages {

        stage("Init") {

            steps {

                echo "First step in the pipeline"

            }
        }
    }
}

// ENV VARS

pipeline {
    agent { label 'jenkins-agent-java' }
    environment {
        MY_VAR = 'production'
    }
    parameters {
        string(name: 'ENV', defaultValue: 'dev', description: 'Target Environment')
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/user/repo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Show Env') {
            steps {
                sh 'echo $MY_VAR'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}

// CREDENTIALS

pipeline {
    agent any
    stages {
        stage('Use SSH Key') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'my-ssh-key', keyFileVariable: 'KEYFILE')]) {
                    sh 'ssh -i $KEYFILE user@host'
                }
            }
        }
    }
}

// POST BUILD ACTIONS

pipeline {
    agent any
    stages {
        stage('Do Something') {
            steps {
                echo 'Running job...'
            }
        }
    }
    post {
        always {
            echo 'Always runs'
        }
        success {
            echo 'Job succeeded'
        }
        failure {
            echo 'Job failed'
        }
    }
}

// PARALLEL

pipeline {
    agent any

    stages {
        stage('Parallel Execution') {
            parallel {
                stage('Job 1') {
                    steps { echo 'Job 1' }
                }
                stage('Job 2') {
                    steps { echo 'Job 2' }
                }
            }
        }
    }
}

// SCRIPTED BLOCK

pipeline {
    agent any

    stages {
        stage('Mixing') {
            steps {
                script {
                    for (int i = 0; i < 3; i++) {
                        echo "Loop ${i}"
                    }
                }
            }
        }
    }
}
