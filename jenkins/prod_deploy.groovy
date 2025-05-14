pipeline {
    agent any
    stages {
        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'master',
                        credentialsId: 'iseries-anilpgit-dev-credential',
                        url: 'https://github.com/anilpgit/iseries_dev.git/'
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

