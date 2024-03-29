package korlibs.korge.assetmanager

import korlibs.korge.parallax.ParallaxConfig


/**
 * Asset model contains run time configuration for loading assets for the game.
 * This config could be also loaded later from YAML files.
 *
 * Hint: Make sure to use only basic types (Integer, String, Boolean).
 */
data class AssetModel(
    val type: AssetType,
    val folderName: String = "",  // default is empty string
    val sounds: MutableMap<String, String> = mutableMapOf(),
    val backgrounds: MutableMap<String, ParallaxConfig> = mutableMapOf(),
    val images: MutableMap<String, ImageDataConfig> = mutableMapOf(),
    val fonts: MutableMap<String, String> = mutableMapOf(),
    val tileMaps: MutableMap<String, TileMapConfig> = mutableMapOf(),
    val entityConfigs: MutableMap<String, ConfigBase> = mutableMapOf()
) {
    data class ImageDataConfig(
        val fileName: String = "",
        val layers: String? = null
    )

    data class TileMapConfig(
        val fileName: String,
        val type: TileMapType
    )

    fun addSound(id: String, fileName: String) {
        sounds[id] = fileName
    }

    fun addBackground(id: String, config: ParallaxConfig) {
        backgrounds[id] = config
    }

    fun addImage(id: String, fileName: String, layers: String? = null) {
        images[id] = ImageDataConfig(fileName, layers)
    }

    fun addFont(id: String, fileName: String) {
        fonts[id] = fileName
    }

    fun addTileMap(id: String, fileName: String, type: TileMapType) {
        tileMaps[id] = TileMapConfig(fileName, type)
    }

    fun addEntityConfig(id: String, config: ConfigBase) {
        entityConfigs[id] = config
    }
}


suspend fun loadAssets(type: AssetType, folderName: String, hotReloading: Boolean = false, cfg: AssetModel.() -> Unit) {
    val assetModel = AssetModel(type, folderName).apply(cfg)
    AssetStore.loadAssets(assetModel)
    if (hotReloading) {
        configureResourceDirWatcher {
            addAssetWatcher(type) {}
        }
    }
}

enum class AssetType { Common, World, Level, Special }
enum class TileMapType { LDtk, Tiled }

interface ConfigBase
