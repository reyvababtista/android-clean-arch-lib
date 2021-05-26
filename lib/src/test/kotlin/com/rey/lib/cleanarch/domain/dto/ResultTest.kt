package com.rey.lib.cleanarch.domain.dto

import com.google.common.truth.Truth.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ResultTest : Spek({
    Feature("result wrapper") {
        val successData = "foo"
        val exception = Exception("bar")

        Scenario("result success should contains data") {
            lateinit var result: Result<String>

            Given("success result") {
                result = Result.Success(successData)
            }
            Then("result should contain data") {
                assertThat(Result::class.java.isInstance(result)).isTrue()
                assertThat(result is Result.Success).isTrue()
                assertThat(result.data).isEqualTo(successData)
            }
        }

        Scenario("result error should contains error") {
            lateinit var result: Result<Unit>

            Given("error result") {
                result = Result.Error(exception)
            }
            Then("result should contain error") {
                assertThat(Result::class.java.isInstance(result)).isTrue()
                assertThat(result is Result.Error).isTrue()
                assertThat(result.error).isEqualTo(exception)
            }
        }
    }
})