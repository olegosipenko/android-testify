/*
 * The MIT License (MIT)
 *
 * Modified work copyright (c) 2022 ndtp
 * Original work copyright (c) 2021 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dev.testify

import android.content.Context
import dev.testify.internal.helpers.getMetaDataBundle

enum class TestifyFeatures(internal val tags: List<String>, private val defaultValue: Boolean = false) {

    ExampleFeature(listOf("testify-example", "testify-alias"), defaultValue = true),
    ExampleDisabledFeature(listOf("testify-disabled")),

    @Deprecated("Please use setCaptureMethod()", ReplaceWith("ScreenshotRule.setCaptureMethod(::canvasCapture)"))
    CanvasCapture(listOf("testify-canvas-capture")),

    Reporter(listOf("testify-reporter")),

    /**
     * When enabled, GenerateDiffs will write a companion image for your screenshot test which can help you more easily
     * identify which areas of your test have triggered the screenshot failure.
     * Diff files are only generated for failing tests.
     * The generated file will be created in the same directory as your baseline images. Diff files can be pulled from
     * the device using `:screenshotPull`.
     *
     * - Black pixels are identical between the baseline and test image
     * - Grey pixels have been excluded from the comparison
     * - Yellow pixels are different, but within the Exactness threshold
     * - Red pixels are different
     */
    GenerateDiffs(listOf("testify-generate-diffs"), defaultValue = false),

    @Deprecated("Please use setCaptureMethod()", ReplaceWith("ScreenshotRule.setCaptureMethod(::pixelCopyCapture)"))
    PixelCopyCapture(listOf("testify-experimental-capture", "testify-pixelcopy-capture"));

    private var override: Boolean? = null

    internal fun reset() {
        override = null
    }

    fun setEnabled(enabled: Boolean = true) {
        this.override = enabled
    }

    fun isEnabled(context: Context? = null): Boolean {
        return override ?: context?.isEnabledInManifest ?: defaultValue
    }

    private val Context?.isEnabledInManifest: Boolean?
        get() {
            return this?.let {
                val metaData = getMetaDataBundle(this)
                val tag = tags.find { tag ->
                    metaData?.containsKey(tag) == true
                }
                if (tag != null) metaData?.getBoolean(tag) else null
            }
        }

    companion object {
        internal fun reset() {
            enumValues<TestifyFeatures>().forEach {
                it.reset()
            }
        }
    }
}
