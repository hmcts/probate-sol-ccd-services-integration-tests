#!groovy

node {
    try {
        def version
        stage('Checkout') {
            deleteDir()
            checkout scm
        }

        stage('Test') {
            sh "./gradlew clean build"
        }

        stage('Package (Docker)') {
            if ("master" == "${env.BRANCH_NAME}") {
                dockerImage imageName: 'probate/sol-ccd-services-integration-tests', tags: ['master']
            } else if ("master" == "${env.BRANCH_NAME}") {
                dockerImage imageName: 'probate/sol-ccd-services-integration-tests'
            }
        }

    } catch (err) {
        currentBuild.result = 'UNSTABLE'
        echo "RESULT: ${currentBuild.result}"

        slackSend(
                channel: '#probate-jenkins',
                color: 'danger',
                message: "${env.JOB_NAME}:  <${env.BUILD_URL}console|Build ${env.BUILD_DISPLAY_NAME}> has FAILED probate Sol CCD service")

        throw err
    } finally {
        publishHTML target: [
                reportDir            : "${env.WORKSPACE}/target/site/serenity/",
                reportFiles          : "index.html",
                reportName           : "Sol CCD Integration Tests Report For Dev",
                alwaysLinkToLastBuild: true
        ]
    }
}
