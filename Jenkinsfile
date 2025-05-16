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
			steps {
				powershell 'gci env:\\ | ft name,value -autosize'
				
                // add a ref to git config to make it aware of master
                powershell '& git config --add remote.origin.fetch +refs/heads/main:refs/remotes/origin/main'
				
                powershell  '& git config --list'
                // now fetch master so you can do a diff against it 
                //powershell '& git fetch --no-tags'
                powershell '& git add -A'
                powershell '& git fetch origin main:refs/remotes/origin/main'
                //powershell '& git fetch origin dev_Project1:refs/remotes/origin/dev_Project1'

				echo    'Fetching main'
                // get the branch name from the environment variable
                //def branchName = env.BRANCH_NAME
                //echo "Branch name: ${branchName}"
                
                powershell '& git status'
                //powershell '& git diff --name-only '
                // do the diff and set some variable based on the result
                powershell '''  
					$DiffToMaster = & git diff --name-only origin/dev_Project1..origin/main
					Switch ($DiffToMaster) {
						'server-1607/base.json' {$env:PACK_BASE = $true}
						'server-1607/basic.json' {$env:PACK_BASIC = $true}
						'server-1607/algo.json' {$env:PACK_ALGO = $true}
						'server-1607/build.json' {$env:PACK_BUILD = $true}
						'server-1607/calc.json' {$env:PACK_CALC = $true}
					}
					gci env:/PACK_*
				'''
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
