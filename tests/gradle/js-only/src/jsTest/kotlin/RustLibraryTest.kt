/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package gobley.uniffi.tests.gradle.jsonly

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class RustLibraryTest {
    @Test
    fun addTest() {
        RustLibrary.add(3, 5) shouldBe 8
        RustLibrary.add(2, 7) shouldBe 9
    }

    @Test
    fun stackManipulationTest() {
        RustLibrary.sendStructToRust((1..20).toList().toTypedArray()) shouldBe 210
    }

    @Test
    fun functionImportsTest() {
        var value = 5
        RustLibrary.registerExternalFunction {
            value += 10
        }
        RustLibrary.invokeAllRegisteredExternalFunctionsTwiceViaRust()
        value shouldBe 25
    }

    @Test
    fun functionPointersTest() {
        RustLibrary.callKotlinFunctionAsFunctionPointer1() shouldBe
                (RustLibrary.theKotlinFunctionCalledAsFunctionPointer1(5, 6.0f)
                        + RustLibrary.theKotlinFunctionCalledAsFunctionPointer1(10, 11.0f))

        RustLibrary.callKotlinFunctionAsFunctionPointer2() shouldBe
                (RustLibrary.theKotlinFunctionCalledAsFunctionPointer1(15, 23.0f)
                        * RustLibrary.theKotlinFunctionCalledAsFunctionPointer2(9))
    }
}