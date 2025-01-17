package com.minek.kotlin.everywhere

import com.minek.kotlin.everywhere.keduct.qunit.asyncTest
import com.minek.kotlin.everywhere.keduct.qunit.fixture
import com.minek.kotlin.everywhere.keuix.browser.Update
import com.minek.kotlin.everywhere.keuix.browser.html.Html
import com.minek.kotlin.everywhere.keuix.browser.runBeginnerProgram
import com.minek.kotlin.everywhere.keuix.browser.runProgram
import org.junit.Test
import org.w3c.dom.Element
import kotlin.test.assertEquals

private data class Model(val count: Int = 0)
private sealed class Msg

private val init = Model()

private val update: Update<Model, Msg> = { _, model ->
    model to null
}

private val view: (Model) -> Html<Msg> = { (count) ->
    Html.text("count = $count")
}

@JsModule("jquery")
external val q: dynamic

class TestProgram {
    @Test
    fun testProgram() {
        val fixture = q(fixture())
        val container = q("<div>").appendTo(fixture)[0] as Element

        asyncTest { resolve, _ ->
            runProgram(container, init, update, view) {
                assertEquals("count = 0", fixture.text())
                resolve(Unit)
            }
        }
    }

    @Test
    fun testBeginnerProgram() {
        val fixture = q(fixture())
        val container = q("<div>").appendTo(fixture)[0] as Element

        val update: (Msg, Model) -> Model = { _, model ->
            model
        }

        asyncTest { resolve, _ ->
            runBeginnerProgram(container, init, update, view) {
                assertEquals("count = 0", fixture.text())
                resolve(Unit)
            }
        }
    }

    @Test
    fun testBeginnerViewProgram() {
        val fixture = q(fixture())
        val container = q("<div>").appendTo(fixture)[0] as Element

        asyncTest { resolve, _ ->
            runBeginnerProgram(container, Html.text("beginnerViewProgram")) {
                assertEquals("beginnerViewProgram", fixture.text())
                resolve(Unit)
            }
        }
    }
}