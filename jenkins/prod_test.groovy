node {
    stage('Cool stuff') {
        onIBMi('PUB400') {
            //Create a library
            def library = 'COOLSTUFF'

            ibmiCommand "CRTLIB LIB($library)"

            //Create a library and carry on only if it exists
            def result = ibmiCommand(command: "CRTLIB LIB($library)", failOnError: false)
            if (!result.successful) {
                if (result.getMessage('CPF2111') != null) {
                    echo "Library $library already exists"
    } else {
                    //Any other error is reported and stops the pipeline
                    error result.getPrettyMessages()
                }
            }

            //Delete a library; carry on whatever happens
            ibmiCommand(command: "DLTLIB LIB($library)", failOnError: false)

            def savfContent = ibmiGetSAVF(libray: 'QTEMP', name: 'BACKUP', toFile: 'backup.savf')
            //Check if the SAVF file exists
            if (savfContent == null) {
                error "SAVF file not found"
            }
            //Check if the SAVF file is empty
            if (savfContent.entries.size() == 0) {
                error "SAVF file is empty"
            }
            //Print the number of objects in the SAVF file
            print "SAVF file contains ${savfContent.entries.size()} object(s)"
            print "${savfContent.entries.size} object(s) saved"
            //Print each saved object
            savfContent.entries.each { entry -> print "  - ${entry.name} (${entry.type})" }
        }
    }
}
