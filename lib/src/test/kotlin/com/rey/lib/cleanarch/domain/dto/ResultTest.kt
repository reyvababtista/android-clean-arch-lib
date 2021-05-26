/**
 * Copyright (C) 2021 Reyva Babtista
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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