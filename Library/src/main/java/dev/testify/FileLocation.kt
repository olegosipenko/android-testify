package dev.testify

enum class FileLocation {
    /**
     * When enabled Testify will write baseline images
     * to the data/data/com.application.package/app_images/ directory
     * of your device or emulator.
     * Default option.
     */
    DATA,

    /**
     * Allows Testify to write screenshots to the SDCARD directory.
     *
     * @see [https://ndtp.github.io/android-testify/docs/recipes/sdcard#configuring-the-gradle-plugin-to-write-to-the-sdcard]
     */
    SD_CARD,

    /**
     * Instructs Testify to save screenshots to the Test Storage.
     *
     * @see [https://developer.android.com/reference/androidx/test/services/storage/TestStorage]
     */
    TEST_STORAGE
}
