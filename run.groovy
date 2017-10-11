#!groovy

node('amd64') {
    stage('Build') {
        checkout scm
        sh 'ls -l'
        message = sh returnStdout: true, script: '''awk 'BEGIN{line=""} /--- FAIL: (.*) \\([0-9\\.]+s\\)/{if(line!=""){print line};flag=1;print $0;next}/([[:space:]]*[-=]{3})|((PASS)|(FAIL), output in)/{flag=0}flag {line=$0} END{print line}' < test'''
    }
}

slackSend color: 'bad',
          channel: '@slowrie',
          message: "```AWS Kola Failure:\n$message```"
