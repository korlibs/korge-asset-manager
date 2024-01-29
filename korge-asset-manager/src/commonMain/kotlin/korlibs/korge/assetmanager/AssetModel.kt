package korlibs.korge.assetmanager

import korlibs.korge.parallax.ParallaxConfig


/**
 * Asset model contains run time configuration for loading assets for the game.
 * This config could be also loaded later from YAML files.
 *
 * Hint: Make sure to use only basic types (Integer, String, Boolean).
 */
data class AssetModel(
    val type: AssetType = AssetType.None,
    val folderName: String = "none",
    val sounds: Map<String, String> = mapOf(),
    val backgrounds: Map<String, ParallaxConfig> = mapOf(),
    val images: Map<String, ImageDataConfig> = mapOf(),
    val fonts: Map<String, String> = mapOf(),
    val tiledMaps: Map<String, String> = mapOf(),
    val entityConfigs: Map<String, ConfigBase> = mapOf()
) {
    data class ImageDataConfig(
        val fileName: String = "",
        val layers: String? = null
    )
}

enum class AssetType{ None, Common, World, Level, Special }

interface ConfigBase
