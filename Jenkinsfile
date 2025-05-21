pipeline {
    agent any

    stages {
        stage('Get Last Commit Details') {
            steps {
                script {
                    List<String> changes = getChangedFilesList()
                    println('Changed file list: ' + changes)
                    List<String> prevchanges = getPreviousChangedFilesList()
                    println('Previous Changed file list: ' + prevchanges)
                    String gitCommitId = getGitcommitID()
                    println('GIT CommitID: ' + gitCommitID)

                    String gitCommitAuthorName = getAuthorName()
                    println('GIT CommitAuthorName: ' + gitCommitAuthorName)

                    String gitCommitMessage = getCommitMessage()
                    println('GIT CommitMessage: ' + gitCommitMessage)
                }
            }
        }
    }
}

@NonCPS
List<String> getChangedFilesList() {
    def changedFiles = []
    for ( changeLogSet in currentBuild.changeSets) {
        for (entry in changeLogSet.getItems()) {
            changedFiles.addAll(entry.affectedPaths)
            println('Changed before sort file extension: ' + changedFiles)
            changedFiles.sort { s1, s2 -> s1.substring(s1.lastIndexOf('.') + 1) <=> s2.substring(s2.lastIndexOf('.') + 1) }
            println('Changed after sort file extension: ' + changedFiles)
            /* groovylint-disable-next-line ComparisonOfTwoConstants */
            for (item in changedFiles) {
                if (item.substring(item.lastIndexOf('.') + 1) == 'BND') {
                    println('Do BND Stuff  ' + item)
                }
                if (item.substring(item.lastIndexOf('.') + 1) == 'RPGLE') {
                    println('Do RPGLE Stuff  ' + item)
                }
                if (item.substring(item.lastIndexOf('.') + 1) == 'SQLRPGLE') {
                    println('Do SQLRPGLE Stuff  ' + item)
                }
                if (item.substring(item.lastIndexOf('.') + 1) == 'DSPF') {
                    println('Do DSPF Stuff  ' + item)
                    onIBMi('PUB400') {
                        print "Command job is ${env.IBMI_COMMAND_JOB}"
                        print "Current CCSID is ${env.IBMI_PROFILE}"
                        //Example below where /QOpenSys/bin/script.sh is any PASE shell command or script.
                        //ibmCommand "QSH CMD('/QOpenSys/usr/bin/sh -c "/QOpenSys/bin/script.sh"')"
                        //ibmCommand "QSH CMD('export PATH=$PATH:/QOpenSys/usr/bin:/QOpenSys/pkgs/bin:')" 
                        ibmCommand "QSH CMD('/QOpenSys/pkgs/bin/makei c -f /home/Apinto1/builds/iseries_dev/QDDSSRC/ART200D.DSPF')" 
                        //Example of running a shell script
                        //Some pipeline steps running on PUB400
                        ibmiCommand "SNDMSG MSG('Hello from Jenkins') TOUSR(ESPENGLER)"
                    }
                }
                if (item.substring(item.lastIndexOf('.') + 1) == 'TABLE') {
                    println('Do TABLE Stuff  ' + item)
                }
            }
        }
    }

    return changedFiles
}
@NonCPS
List<String> getPreviousChangedFilesList() {
    def changedFiles = []
    for ( changeLogSet in currentBuild.previousBuild.changeSets) {
        for (entry in changeLogSet.getItems()) {
            changedFiles.addAll(entry.affectedPaths)
        }
    }
    return changedFiles
}
@NonCPS
String getGitcommitID() {
    gitCommitID = ' '
    for ( changeLogSet in currentBuild.changeSets) {
        for (entry in changeLogSet.getItems()) {
            gitCommitID = entry.commitId
        }
    }
    return gitCommitID
}

@NonCPS
String getAuthorName() {
    gitAuthorName = ' '
    for ( changeLogSet in currentBuild.changeSets) {
        for (entry in changeLogSet.getItems()) {
            gitAuthorName = entry.authorName
        }
    }
    return gitAuthorName
}

@NonCPS
String getCommitMessage() {
    commitMessage = ' '
    for ( changeLogSet in currentBuild.changeSets) {
        for (entry in changeLogSet.getItems()) {
            commitMessage = entry.msg
        }
    }
    return commitMessage
}
