pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'nn1ck1/spring-k8s-demo'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvnw.cmd clean install'
            }
        }

        stage('Build Docker Image') {
            when {
                branch 'master'
            }
            steps {
                bat 'docker build -t %DOCKER_IMAGE%:%BUILD_NUMBER% -t %DOCKER_IMAGE%:latest .'
            }
        }

        stage('Push Docker Image') {
            when {
                branch 'master'
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    powershell '$env:DOCKER_PASSWORD | docker login -u $env:DOCKER_USERNAME --password-stdin'
                    bat 'docker push %DOCKER_IMAGE%:%BUILD_NUMBER%'
                    bat 'docker push %DOCKER_IMAGE%:latest'
                }
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}