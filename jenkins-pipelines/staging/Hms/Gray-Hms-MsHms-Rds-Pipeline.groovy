def COMPILE_JOB_NAME = 'Gray-Hms-MSHMS-RDS-Compile'
def DEPLOY_JOB_NAME = 'Gray-Hms-MSHMS-RDS-Deploy'

def TAG = ' '
def OLD_VERSION = ' '
def BRANCH = '*/test'
def WAR_NAME = 'hms-rds-1.0.0.jar'

properties ([
        parameters ([
                string (
                        defaultValue: '',
                        description: '',
                        name: 'TAG'),
                string (
                        defaultValue: 'v1.0.0',
                        description: 'v1.0.0',
                        name: 'OLD_VERSION'),
                string (
                        defaultValue: '',
                        description: '',
                        name: 'BRANCH'),
                password  (
                        defaultValue: '',
                        description: 'JPS_PWD',
                        name: 'JPS_PWD')
        ])
])

node {
    stage('Compile') {
        build job: "$COMPILE_JOB_NAME", parameters: [string(name: 'TAG', value:env.TAG), string(name: 'BRANCH', value: BRANCH), string(name: 'WAR_NAME', value: WAR_NAME)]
    }

    /*stage ('input'){
        def userInput = input(
                id: 'userInput', message: 'go on?', parameters: [
                [$class: 'StringParameterDefinition', defaultValue: '',description: 'war_name', name: 'war_name'],
        ])
        WAR_NAME = userInput
    }*/
    stage('Deploy') {
        build job: "$DEPLOY_JOB_NAME",parameters :[string(name: 'WAR_NAME',value: WAR_NAME),string(name: 'TAG', value:env.TAG), string(name: 'OLD_VERSION', value:env.OLD_VERSION), password(name: 'JPS_PWD', value:env.JPS_PWD)]
    }
}
