package com.raikerdev.petproject.movies.data.server

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule: TestWatcher() {

    lateinit var server: MockWebServer

    override fun starting(description: Description) {
        super.starting(description)
        server = MockWebServer()
        server.start(8080)
    }

    override fun finished(description: Description) {
        super.finished(description)
        server.shutdown()
    }

}
