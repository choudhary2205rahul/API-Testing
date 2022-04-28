pipeline {

    agent any

    tools {
        jdk 'openjdk'
        maven 'maven'
    }

    stages {

        stage('cleanup old artifacts') {
            steps {
                dir('target') {
                    deleteDir()
                }
            }
        }

        stage('Test') {
            steps {
                withMaven(maven: 'maven') {
                    sh 'mvn clean test'
                }

            }
        }

        stage('Build') {
            steps {
                withMaven(maven: 'maven') {
                    sh 'mvn clean install'
                }
            }
        }

        stage('HTML Publish') {
            steps {
                publishHTML(target: [allowMissing         : false,
                                     alwaysLinkToLastBuild: true,
                                     keepAll              : true,
                                     reportDir            : 'target/site/jacoco',
                                     reportFiles          : 'index.html',
                                     reportName           : 'Test Report',
                                     reportTitles         : 'Testing Report'])

            }
        }

        stage('Sonar Scan') {
            steps {
                withSonarQubeEnv('sonarqube_local') {
                    withMaven(maven: 'maven') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=com.api:junit -Dsonar.host.url=http://localhost:9000 -Dsonar.login=abe2ad51ccf354b1c96e453d6e3266df1b620d42'
                    }
                }
            }
        }

        stage("Quality Gate Status") {
            steps {
                timeout(time: 600, unit: 'SECONDS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Snyk Scan') {
            steps {
                withMaven(maven: 'maven') {
                    snykSecurity(
                            snykInstallation: 'snyk',
                            snykTokenId: 'snyk_token_local',
                            severity: 'critical',
                            failOnIssues: false,
                            failOnError: true,
                            additionalArguments: '--debug --all-projects --target-reference=' + 'dev'
                    )
                }
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk              : '',
                            properties       : [],
                            reportBuildPolicy: 'ALWAYS',
                            results          : [[path: 'target/allure-results']]
                    ])

                }
            }
        }

        stage('InfluxDB Report') {
            steps {
                script {
                    if (currentBuild.currentResult == 'UNSTABLE') {
                        currentBuild.result = "UNSTABLE"
                    } else {
                        currentBuild.result = "SUCCESS"
                    }
                    step([$class: 'InfluxDbPublisher', customData: null, customDataMap: null, customPrefix: null, target: 'sonarqube_metrics'])
                }
            }
        }

    }

}
