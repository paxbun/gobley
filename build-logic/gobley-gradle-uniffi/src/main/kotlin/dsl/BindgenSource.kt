/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package gobley.gradle.uniffi.dsl

import gobley.gradle.BuildConfig
import gobley.gradle.InternalGobleyGradleApi
import java.io.Serializable

sealed class BindgenSource : Serializable {
    data class Registry @OptIn(InternalGobleyGradleApi::class) constructor(
        val packageName: String = BuildConfig.BINDGEN_CRATE,
        val version: String = BuildConfig.BINDGEN_VERSION,
        val registry: String? = null,
    ) : BindgenSource(), Serializable

    data class Path(val path: String) : BindgenSource(), Serializable

    data class Git(
        val repository: String,
        val commit: Commit? = null,
    ) : BindgenSource(), Serializable {
        sealed class Commit : Serializable {
            data class Branch(val branch: String) : Commit(), Serializable
            data class Tag(val tag: String) : Commit(), Serializable
            data class Revision(val revision: String) : Commit(), Serializable
        }
    }
}
