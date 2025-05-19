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
            println('Changed file list: ' + changedFiles)
            /* groovylint-disable-next-line ComparisonOfTwoConstants */
            for(item in changedFiles) {
                println('Changed file: ' + item.lastIndexOf('.'))
                println('Changed file: ' + item.substring(item.lastIndexOf('.')))
                println('Changed file: ' + item.substring(item.lastIndexOf('.') + 1))
                if(item.substring(item.lastIndexOf('.') + 1) == 'BND') {
                    println('Do BND Stuff  ' + item)
                if(item.substring(item.lastIndexOf('.') + 1) == 'RPGLE') {
                    println('Do RPGLE Stuff  ' + item)
                if(item.substring(item.lastIndexOf('.') + 1) == 'SQLRPGLE') {
                    println('Do SQLRPGLE Stuff  ' + item)
                if(item.substring(item.lastIndexOf('.') + 1) == 'DSPF') {
                    println('Do DSPF Stuff  ' + item)

        }
    }
    println('Changed before sort file list: ' + changedFiles)
    changedFiles.sort()
    println('Changed after sort file list: ' + changedFiles)
    changedFiles.sort { s1, s2 -> s1.substring(s1.lastIndexOf('.') + 1) <=> s2.substring(s2.lastIndexOf('.') + 1) }
    println('Changed after sort file extension: ' + changedFiles)

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
