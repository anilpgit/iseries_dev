//Jenkinsfile

pipeline {
    agent any
    stages {
        stage('Setting the variables values') {
            steps {
                sh '''#!/bin/bash
                 echo "hello world"
         '''
                sh '''makei build'''
                script {
                    // Set the environment variables
                    env.ENVIRONMENT = 'DEV'
                    env.JOB_NAME = 'Jenkinsfile'
                    env.BUILD_NUMBER = '1'
                    env.BUILD_ID = '1'
                    env.WORKSPACE = '/home/jenkins/workspace'
                    env.JENKINS_HOME = '/var/lib/jenkins'
                    env.JENKINS_URL = 'http://localhost:8080'
                    env.JOB_URL = "${env.JENKINS_URL}/job/${env.JOB_NAME}/${env.BUILD_NUMBER}/"
                }
                
            }
        }
        stage('Save Restore') {
            steps {
                script {
                    onIBMi('PUB400') {
                        //Create a SAVF in APINTO12
                        def library = 'APINTO12'
                        def savFile = 'RELEASE1'
                        ibmiCommand "DLTF FILE($library/$savFile)"
            //ibmiCommand "CRTSAVF QTEMP/BACKUP"
            //ibmiCommand "SAVLIB LIB(ECTO1) DEV(*SAVF) SAVF(QTEMP/BACKUP)"
            //ibmiGetSAVF library: "QTEMP", name: "BACKUP", toFile: "ecto1.savf"

                        //Create a library and carry on only if it exists
                        def result = ibmiCommand(command: "CRTSAVF FILE($library/$savFile) TEXT('Backup before build ')", failOnError: false)
                        if (!result.successful) {
                            if (result.getMessage('CPF2111') != null) {
                                echo " $savFile already exists"
    } else {
                                //Any other error is reported and stops the pipeline
                                error result.getPrettyMessages()
                            }
                        }

                        def result2 = ibmiCommand(command: 'SAVLIB LIB(APINTO11) DEV(*SAVF) SAVF(APINTO12/RELEASE1) OMITOBJ(APINTO11/Q*)', failOnError:false)
                        def savfContent = ibmiGetSAVF(library: 'APINTO12', name: 'RELEASE1', toFile: 'release1.savf')
                        //Check if the SAVF file exists
                        if (savfContent == null) {
                            error 'SAVF file not found'
                        }
                        //Check if the SAVF file is empty
                        if (savfContent.entries.size() == 0) {
                            error 'SAVF file is empty'
                        }
                        //Print the number of objects in the SAVF file
                        print "SAVF file contains ${savfContent.entries.size()} object(s)"
                        print "${savfContent.entries.size} object(s) saved"
                        //Print each saved object
                        savfContent.entries.each { entry -> print "  - ${entry.name} (${entry.type})" }
                    }
                }
            }
        }
    }
}
