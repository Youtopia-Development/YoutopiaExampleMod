package com.example.server.zipline

import app.cash.zipline.Zipline
import com.example.server.api.Commands
import com.example.server.api.Server

/**
 * DO NOT MODIFY!
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
fun launchCommandsService() {
    val zipline = Zipline.get()
    zipline.bind<Commands>("commands", Server)
}
