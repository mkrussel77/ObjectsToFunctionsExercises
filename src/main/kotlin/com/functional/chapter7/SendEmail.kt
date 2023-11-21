package com.functional.chapter7

data class FileError(override val msg: String) : OutcomeError
data class EmailError(override val msg: String) : OutcomeError

fun sendEmail(fileName: String): Outcome<EmailError, Unit> =
    readFile(fileName)
        .transformFailure { EmailError("error reading file ${it.msg}") }
        .onFailure { return@sendEmail it.asFailure() }
        .let(::sendTextByEmail)


fun readFile(fileName: String): Outcome<FileError, String> =
    if (fileName.startsWith("err")) {
        FileError("$fileName missing!").asFailure()
    } else {
        "something from $fileName".asSuccess()
    }

fun sendTextByEmail(text: String): Outcome<EmailError, Unit> = Unit.asSuccess()
