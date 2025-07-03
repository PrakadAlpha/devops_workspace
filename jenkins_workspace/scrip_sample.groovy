properties([
    parameters([
        string(name: 'ENV', defaultValue: 'dev', description: 'Target environment')
    ])
])
node("jenkins-agent"){

    stage("Start"){
        echo "this is without stages"
    }
    stage('Build') {
        sh 'mvn clean package'
    }
    stage('Test') {
        sh 'mvn test'
    }
    stage('Env Example') {
        env.MY_VAR = 'production'
        sh 'echo $MY_VAR'
    }
}

//ENV VARS

node {
    withEnv(["MY_VAR=production"]) {
        sh 'echo $MY_VAR'
    }
}

// CREDENTIALS

node {
    withCredentials([usernamePassword(credentialsId: 'my-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh 'echo $USER'
    }
}

// PARALLEL

node {
    stage('Parallel Work') {
        parallel(
            job1: {
                echo 'Running Job 1'
            },
            job2: {
                echo 'Running Job 2'
            }
        )
    }
}

// RETRY

node {
    retry(3) {
        sh 'unstable-command'
    }
}

// TIMEOUT

node {
    timeout(time: 5, unit: 'MINUTES') {
        sh 'long-task'
    }
}

// POST BUILD

node {
    try {
        stage('Main Job') {
            sh 'run-something'
        }
    } catch (e) {
        echo 'Build failed: ' + e.toString()
        currentBuild.result = 'FAILURE'
    } finally {
        echo 'Always runs'
    }
}
