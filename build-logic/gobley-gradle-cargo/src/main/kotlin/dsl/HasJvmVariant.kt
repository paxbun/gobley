/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package gobley.gradle.cargo.dsl

import gobley.gradle.Variant
import org.gradle.api.provider.Property

interface HasJvmVariant {
    /**
     * The variant to use for JVM builds. Defaults to `Variant.Debug`. Setting this will override
     * the `jvmVariant` properties in outer blocks. For example, in the following DSL:
     * ```kotlin
     * cargo {
     *   jvmVariant = Variant.Debug
     *   builds.mingw {
     *     jvmVariant = Variant.Release
     *   }
     * }
     * ```
     * the resulting `.jar` will have a release `.dll` file and debug `.so` and `.dylib` files.
     */
    val jvmVariant: Property<Variant>

    /**
     * The variant to use for Kotlin Multiplatform JVM publishing. Defaults to `Variant.Release`.
     * Setting this will override the `jvmPublishingVariant` properties in outer blocks, in the
     * same manner as `jvmVariant`.
     */
    val jvmPublishingVariant: Property<Variant>
}
