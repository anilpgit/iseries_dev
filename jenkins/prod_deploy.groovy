node {
    stage('Save Restore') {
        onIBMi('PUB400') {
            //Create a SAVF in APINTO12
            def library = 'APINTO12'
            def savFile = 'RELEASE1'
            //ibmiCommand "CRTSAVF FILE($library/$savFile)"

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

            ibmiCommand(SAVLIB: APINTO11, SAVF: savFile, DATASET: 'RELEASE1', REPLACE: true)
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
