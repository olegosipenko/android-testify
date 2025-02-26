package dev.testify.samples.flix.ui.common.composables

import dev.testify.ComposableScreenshotRule
import dev.testify.annotation.ScreenshotInstrumentation
import dev.testify.samples.flix.presentation.moviedetails.model.CreditPresentationModel
import dev.testify.samples.flix.ui.common.composeables.CastMember
import dev.testify.samples.flix.ui.common.util.imagePromise
import org.junit.Rule
import org.junit.Test

class CastMemberScreenshotTest {

    @get:Rule
    val rule = ComposableScreenshotRule()

    @ScreenshotInstrumentation
    @Test
    fun default() {
        rule
            .setCompose {
                CastMember(
                    model = CreditPresentationModel(
                        name = "Benjamin Franklin",
                        characterName = "Himself",
                        image = imagePromise("file:///android_asset/images/headshots/BenjaminFranklin.jpg")
                    )
                )
            }
            .assertSame()
    }
}
