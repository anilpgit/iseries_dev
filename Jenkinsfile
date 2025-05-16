//Jenkinsfile

/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any
    stages {
        stage ("info") {
            //when {
		        //changeRequest()
                //branch pattern: "dev_\\d+", comparator: "REGEXP"
              //  anyOf { branch 'main'; branch 'dev_Project1' } 
			//}
			
				when { changeset "subdirectory/*"}
                        steps {
                                script {
                                echo 'Running on IBMi'
                                echo "Running on ${env.JOB_NAME} in ${env.BUILD_URL}"
                                echo "Running on ${env.WORKSPACE}"
                                echo "Running on ${env.BUILD_ID}"
                                echo "Running on ${env.BUILD_NUMBER}"
                                echo "Running on ${env.BUILD_URL}"
                            }
                }
        
        }
        stage('CRTSAVF') {
            steps {
                script {
                    echo 'Creating SAVF'
                    onIBMi('PUB400') {
                        def library = 'APINTO12'
                        def savFile = 'RELEASE1'

                        // Create a SAVF in APINTO12
                        ibmiCommand "DLTF FILE($library/$savFile)"

                        // Create a library and carry on only if it exists
                        def result = ibmiCommand(
                            command: "CRTSAVF FILE($library/$savFile) TEXT('Backup before build ')",
                            failOnError: false
                        )

                        // Flattened logic to reduce nesting
                        if (result.successful) {
                            echo " $library created"
                            return
                        }
                        if (result.getMessage('CPF2111') != null) {
                            echo " $library already exists"
                            echo " $savFile already exists"
                            return
                        }
                        // Any other error is reported and stops the pipeline
                        error result.getPrettyMessages
                    }
                }
            
        }
        stage('SAVEF') {
            steps {
                script {
                    echo 'Saving objects to SAVF'

                    onIBMi('PUB400') {
                        //Create a SAVF in APINTO12
                        def result2 = ibmiCommand(
                            command: 'SAVLIB LIB(APINTO11) DEV(*SAVF) ' +
                                     'SAVF(APINTO12/RELEASE1) ' +
                                     'OMITOBJ(APINTO11/Q*)',
                            failOnError: false)
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

                        //Put savf to IFS /home/APinto1/release1.savf
                        ibmiPutSAVF(
                            library: 'APINTO12',
                            name: 'RELEASE1',
                            fromFile: 'release1.savf',
                            toPath: '/home/APinto1/release1.savf',
                            failOnError: false
                        )
                }
                //RSTLIB command not allowed on PUB400
                }
            }
        }
    
            stage('Build') {
                steps {
                    script {
                    /* groovylint-disable-next-line NestedBlockDepth */
                        onIBMi('PUB400') {
                        //Run the build command
                        // ibmiCommand 'CALL PGM(APINTO11/BUILD)'
                        echo 'Calling IBM Build Command'
                        }
                    }
                /* groovylint-disable-next-line TrailingWhitespace */
                }
            }
            stage('TEST') {
                steps {
                    script {
                        echo 'Running tests'
                    }
                }
            }
            stage('Deliver') {
                steps {
                    script {
                    echo 'Delivering'
                    }
                }
            }
            stage('Publish') {
                steps {
                    script {
                    echo 'Publishing'
                    }
                }
            }
            stage('Deploy') {
                steps {
                    script {
                    echo 'Deploying'
                    }
                }
            }
}
}
