package org.youtopia.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface Building {

    val name: String

    sealed interface UpgradeableBuilding<T : UpgradeableBuilding<T>> : Building {
        val level: Int
        fun buildingCopy(newLevel: Int): T
        fun upgrade() = buildingCopy(level + 1)
        fun downgrade() = buildingCopy(level - 1)
    }

    @Serializable
    data object Village : Building {
        override val name = "Village"
    }

    @Serializable
    data object Ruin : Building {
        override val name = "Ruin"
    }

    @Serializable
    data class City(
        override val name: String,
        val tribe: org.youtopia.data.Tribe,
        val level: Int = 1,
        val isCapital: Boolean = false,
        val hasWorkshop: Boolean = false,
        val hasWalls: Boolean = false,
        val parksCount: Int = 0,
        val embassies: List<Embassy> = emptyList(),
        // TODO: add a field to store the exact district configuration
    ) : Building

    @Serializable
    data object LumberHut : Building {
        override val name = "Lumber Hut"
    }

    @Serializable
    data class Sawmill(
        override val level: Int,
    ) : Building.UpgradeableBuilding<Building.Sawmill> {
        override val name = "Sawmill"
        override fun buildingCopy(newLevel: Int) = copy(level = newLevel)
    }

    @Serializable
    data object Farm : Building {
        override val name = "Farm"
    }

    @Serializable
    data class Windmill(
        override val level: Int,
    ) : Building.UpgradeableBuilding<Building.Windmill> {
        override val name = "Windmill"
        override fun buildingCopy(newLevel: Int) = copy(level = newLevel)
    }

    @Serializable
    data object Mine : Building {
        override val name = "Mine"
    }

    @Serializable
    data class Forge(
        override val level: Int,
    ) : Building.UpgradeableBuilding<Building.Forge> {
        override val name = "Forge"
        override fun buildingCopy(newLevel: Int) = copy(level = newLevel)
    }

    @Serializable
    data class Market(
        override val level: Int,
        val nearSawmill: Boolean,
        val nearWindmill: Boolean,
        val nearForge: Boolean,
    ) : Building.UpgradeableBuilding<Building.Market> {
        override val name = "Market"
        override fun buildingCopy(newLevel: Int) = copy(level = newLevel)

        fun setStands(nearSawmill: Boolean? = null, nearWindmill: Boolean? = null, nearForge: Boolean? = null) =
            Building.Market(
                level,
                nearSawmill ?: this.nearSawmill,
                nearWindmill ?: this.nearWindmill,
                nearForge ?: this.nearForge,
            )
    }

    @Serializable
    data class Port(
        val connectedPorts: Set<Position>,
        val waterRoutes: Set<List<Position>>,
    ) : Building {
        override val name = "Port"
    }

    sealed interface Temple<T : Building.Temple<T>> :
        Building.UpgradeableBuilding<T> {
        val turnsToLevelUp: Int
        fun buildingCopy(newLevel: Int, newTurnsToLevelUp: Int): T
        fun decrementCounter(): T = buildingCopy(level, turnsToLevelUp - 1)
        override fun buildingCopy(newLevel: Int): T = buildingCopy(newLevel, turnsToLevelUp)
        override fun upgrade() = buildingCopy(level + 1, 2)
        override fun downgrade() = buildingCopy(level - 1)
    }

    @Serializable
    data class FieldTemple(
        override val level: Int,
        override val turnsToLevelUp: Int,
    ) : Building.Temple<Building.FieldTemple> {
        override val name = "Temple"
        override fun buildingCopy(newLevel: Int, newTurnsToLevelUp: Int) = copy(level = newLevel, turnsToLevelUp = newTurnsToLevelUp)
    }

    @Serializable
    data class ForestTemple(
        override val level: Int,
        override val turnsToLevelUp: Int,
    ) : Building.Temple<Building.ForestTemple> {
        override val name = "Forest Temple"
        override fun buildingCopy(newLevel: Int, newTurnsToLevelUp: Int) = copy(level = newLevel, turnsToLevelUp = newTurnsToLevelUp)
    }

    @Serializable
    data class MountainTemple(
        override val level: Int,
        override val turnsToLevelUp: Int,
    ) : Building.Temple<Building.MountainTemple> {
        override val name = "Mountain Temple"
        override fun buildingCopy(newLevel: Int, newTurnsToLevelUp: Int) = copy(level = newLevel, turnsToLevelUp = newTurnsToLevelUp)
    }

    @Serializable
    data class WaterTemple(
        override val level: Int,
        override val turnsToLevelUp: Int,
    ) : Building.Temple<Building.WaterTemple> {
        override val name = "Water Temple"
        override fun buildingCopy(newLevel: Int, newTurnsToLevelUp: Int) = copy(level = newLevel, turnsToLevelUp = newTurnsToLevelUp)
    }

    @Serializable
    data class Monument(
        val type: MonumentType,
        val tribe: Tribe,
    ) : Building {
        override val name = "Monument"
    }

    @Serializable
    data class Lighthouse(
        val direction: Direction,
        val seenBy: List<PlayerId>,
    ) : Building {
        override val name = "Lighthouse"
    }

    @Serializable
    data class Bridge(
        val rotated: Boolean,
    ) : Building {
        override val name = "Bridge"
    }
}
