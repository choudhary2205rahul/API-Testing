pipeline {

    agent { label 'any' }

    tools {
        jdk 'openjdk-11'
        maven 'maven-3.8.5'
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
                    sh 'mvn clean jacoco:prepare-agent install'
                }
            }
        }

        stage('HTML Publish') {
            steps {
                publishHTML (target : [allowMissing: false,
                                       alwaysLinkToLastBuild: true,
                                       keepAll: true,
                                       reportDir: 'target/site/jacoco',
                                       reportFiles: 'index.html',
                                       reportName: 'Test Report',
                                       reportTitles: 'Testing Report'])

            }
        }

        stage('Sonar Scan') {
            steps {
                withSonarQubeEnv('Atlas_Testing_Sonarqube') {
                    withMaven(maven: 'maven') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=com.swisscom:atlas-testing-backend -Dsonar.host.url=https://eye3-sonarqube.dos.corproot.net -Dsonar.login=49289e000bb50bac108cacc9cae4be0e3ea50f38'
                    }
                }
            }
        }

//        stage("Quality Gate Status") {
//            steps {
//                timeout(time: 600, unit: 'SECONDS') {
//                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
//                    // true = set pipeline to UNSTABLE, false = don't
//                    waitForQualityGate abortPipeline: true
//                }
//            }
//        }

//        stage('Snyk Scan') {
//            steps {
//                withMaven(maven: 'maven') {
//
////                    failOnIssues (optional, default: true)
////                    Whether the step should fail if issues and vulnerabilities are found.
//
////                    failOnError (optional, default: true)
////                    Whether the step should fail if Snyk fails to scan the project due to an error.
////                    Errors include scenarios like: failing to download Snyk's binaries, improper Jenkins setup, bad configuration and server errors.
//
////                    severity (optional, default: automatic)
////                    The minimum severity to detect. Can be one of the following: low, medium, high , critical.
////                    See --severity-threshold under Snyk CLI docs for default behaviour.
//
//                    snykSecurity(
//                            snykInstallation: 'snyk',
//                            snykTokenId: 'atlas_testing_snyk',
//                            severity: 'critical',
//                            failOnIssues: false,
//                            failOnError: true,
//                            additionalArguments: '--debug --all-projects --target-reference=' + 'dev'
//                    )
//                }
//            }
//        }

        stage('Allure Report') {
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                    ])

                    if (currentBuild.currentResult == 'UNSTABLE') {
                        currentBuild.result = "UNSTABLE"
                    } else {
                        currentBuild.result = "SUCCESS"
                    }
                    step([$class: 'InfluxDbPublisher', customData: null, customDataMap: null, customPrefix: null, target: 'grafana'])

                }
            }
        }

    }

}
