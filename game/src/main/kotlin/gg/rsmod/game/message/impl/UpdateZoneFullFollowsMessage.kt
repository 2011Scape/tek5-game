package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class UpdateZoneFullFollowsMessage(
    val x: Int,
    val z: Int,
    val height: Int,
) : Message
