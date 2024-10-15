package com.example.server.api

import org.youtopia.data.CityUpgradeReward
import org.youtopia.data.CityUpgradeRewardRequest
import org.youtopia.data.Game
import org.youtopia.data.GameEffect
import org.youtopia.data.GeneralAction
import org.youtopia.data.Position
import org.youtopia.data.RoadNetwork
import org.youtopia.data.Terrain
import org.youtopia.data.TileAction
import org.youtopia.data.TileActionAvailability
import org.youtopia.data.TroopAction
import org.youtopia.data.diff.ActionResult
import org.youtopia.data.diff.GameDiff

object Server : Commands {

    /**
     * DO NOT MODIFY!
     */
    private var game: Game = Game(tiles = emptyList(), size = 0 to 0, players = emptyList(), turn = 0, currentPlayerIndex = 0)

    /**
     * DO NOT MODIFY!
     */
    private val roadNetworks: MutableSet<RoadNetwork> = mutableSetOf()

    /**
     * DO NOT MODIFY!
     */
    private val cityUpgradeRequestQueue: ArrayDeque<CityUpgradeRewardRequest> = ArrayDeque()

    override fun requestAvailableDestinations(
        tilePosition: Int,
    ): Set<Int> {
        throw NotImplementedError()
    }

    override fun requestAvailableTargets(
        tilePosition: Int,
    ): Set<Int> {
        throw NotImplementedError()
    }

    override fun requestAvailableTroopActions(
        tilePosition: Int,
    ): List<TroopAction> {
        throw NotImplementedError()
    }

    override fun requestAvailableTileActions(
        tilePosition: Int,
        tileActions: List<TileAction>,
    ): List<TileAction> {
        val terrain = game.tiles[tilePosition].terrain
        return if (terrain == Terrain.FIELD || terrain == Terrain.MOUNTAIN) {
            tileActions.filterNot { it is TileAction.GrowForest } + TileAction.GrowForest(TileActionAvailability.AVAILABLE)
        } else tileActions
    }

    override fun performTroopAction(
        action: TroopAction,
    ): ActionResult {
        throw NotImplementedError()
    }

    override fun performTileAction(
        pos: Position,
        action: TileAction,
    ): ActionResult {
        if (action !is TileAction.GrowForest) throw NotImplementedError()

        val tile = game.tiles[pos]

        return ActionResult(buildList {
            if (tile.resource != null) {
                add(GameEffect.RemoveResource(pos))
            }
            add(GameEffect.SwapTerrain(pos, Terrain.FOREST, game.tiles[pos].tribe))
        })
    }

    override fun moveTo(
        startPosition: Int,
        finishPosition: Int,
    ): ActionResult {
        throw NotImplementedError()
    }

    override fun attack(
        attackerPosition: Int,
        targetPosition: Int,
    ): ActionResult {
        throw NotImplementedError()
    }

    override fun performGeneralAction(action: GeneralAction): ActionResult {
        throw NotImplementedError()
    }

    override fun chooseCityUpgradeReward(
        pos: Position,
        reward: CityUpgradeReward,
    ): ActionResult {
        throw NotImplementedError()
    }

    override fun generateGame(size: Pair<Int, Int>): Pair<Game, Set<RoadNetwork>> {
        throw NotImplementedError()
    }

    /**
     * DO NOT MODIFY!
     */
    override fun syncGame(game: Game) {
        Server.game = game
    }

    /**
     * DO NOT MODIFY!
     */
    override fun syncGameDiff(gameDiff: GameDiff) {
        game = game.update(gameDiff)
    }

    override fun calculateGameDiff(gameEffect: GameEffect): GameDiff {
        throw NotImplementedError()
    }

    /**
     * DO NOT MODIFY!
     */
    override fun syncRoadNetworks(roadNetworks: Set<RoadNetwork>) {
        Server.roadNetworks.clear()
        Server.roadNetworks.addAll(roadNetworks)
    }

    /**
     * DO NOT MODIFY!
     */
    override fun removeFirstCityUpgradeRequest() {
        cityUpgradeRequestQueue.removeFirst()
    }

    /**
     * DO NOT MODIFY!
     */
    override fun addCityUpgradeRequest(request: CityUpgradeRewardRequest) {
        cityUpgradeRequestQueue.add(request)
    }
}
