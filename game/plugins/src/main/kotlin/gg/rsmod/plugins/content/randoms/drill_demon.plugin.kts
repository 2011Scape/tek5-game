package gg.rsmod.plugins.content.randoms

import gg.rsmod.game.model.attr.*
import kotlin.random.Random

/**
 * @author Harley <https://github.com/HarleyGilpin>
 *
 *This plugin is for the Drill Demon random event, in which players are tested on their agility.
 *
 */

val sergeantDamien = Npcs.SERGEANT_DAMIEN

// Handle the 'talk-to' option for Sergeant Damien
on_npc_option(sergeantDamien, option = "talk-to") {
    player.queue {
        // If the player is in the training area, start the Drill Demon event
        if (player.tile.regionId == 12619) {
            atTheDrillDemonTrainingArea(this)
        } else {
            // If the player is not in the training area, check if they have already started the event
            if (player.attr[DRILL_DEMON_ACTIVE] == false || !player.attr.has(DRILL_DEMON_ACTIVE)) {
                NotAllowedZone(this)
            } else {
                // If the player has started the event, continue with the dialogue
                sergeantDamienDialogue(this)
            }
        }
    }
}

// Handle the Drill Demon dialogue with Sergeant Damien
suspend fun sergeantDamienDialogue(it: QueueTask) {
    var lastKnownPosition: Tile = it.player.tile
    var teleportToDrillDemon = Tile(3163, 4821)
    it.chatNpc("Private ${it.player.username}, atten-SHUN!",
                        "You've been recommended for my corps.",
                        "Do you think you can be the best?")
    it.chatPlayer("Sir, yes sir!")
    it.player.attr[LAST_KNOWN_POSITION] = lastKnownPosition
    it.wait(1)
    it.player.moveTo(teleportToDrillDemon)
    it.wait(1)
    it.player.graphic(86)
    val npc = it.player.getInteractingNpc()
    npc.queue {
        world.remove(npc)
        npc.graphic(86)
    }
}

suspend fun atTheDrillDemonTrainingArea(it: QueueTask) {
    it.chatNpc("Move yourself, Private! Follow my orders and you",
                        "may, just may, leave here in a fit state for my corps!")
    it.player.attr[CORRECT_EXERCISE] = Random.nextInt(1, 5)
    val exercise: Int? = it.player.attr[CORRECT_EXERCISE]
    if (exercise != null) {
        afterPerformingCorrectExerciseOrStartingEvent(it, exercise)
    }
}

suspend fun useExerciseMatBeforeInstruction(it: QueueTask) {
    it.chatNpc("I haven't given you the order yet, worm!", npc = sergeantDamien)
    // Continue with the dialogue below.
}

suspend fun afterPerformingCorrectExerciseOrStartingEvent(it: QueueTask, exerciseType: Int) {
    val dialogue = when (exerciseType) {
        1 -> ("I want to see you on that mat doing star jumps, private!")
        2 -> ("Drop and give me push ups on that mat, private!")
        3 -> ("Get yourself over there and jog on that mat, private!")
        4 -> ("Get on that mat and give me sit ups, private!")
        else -> throw IllegalArgumentException("Invalid exercise: $exerciseType")
    }
    it.chatNpc(dialogue, npc = sergeantDamien)
    val exerciseScore = it.player.attr[EXERCISE_SCORE] ?: 0
    if (exerciseScore > 4) {
        it.chatNpc(
            "Well I'll be, you actually did it, Private.",
            "Now take this and get out of my sight.", npc = sergeantDamien
        )
        it.player.inventory.add(Item(Items.RANDOM_EVENT_GIFT))
        it.player.addLoyalty(world.random(1..30))
        it.player.attr[DRILL_DEMON_ACTIVE] = false
        it.player.attr[EXERCISE_SCORE] = 0
        it.player.attr[BOTTING_SCORE] = (it.player.attr[BOTTING_SCORE] ?: 0) - 1
        val lastKnownPosition: Tile? = it.player.attr[LAST_KNOWN_POSITION]
        val backupPosition = Tile(x = 3222, z = 3219, 0)
        if (lastKnownPosition != null) {
                it.player.moveTo(lastKnownPosition)
            } else {
                // Handle the case where the saved position is null, e.g., notify the player.
                it.player.message("We couldn't locate your last known position. We'll teleport you to Lumbridge.")
                it.player.moveTo(backupPosition)
            }
    }
}

suspend fun afterPerformingIncorrectExercise(it: QueueTask) {
    val dialogue = when (val correctExerciseType: Int? = it.player.attr[CORRECT_EXERCISE] as? Int) {
        1 -> Pair("Wrong exercise, worm!", "I want to see you on that mat doing star jumps, private!")
        2 -> Pair("Wrong exercise, worm!", "Drop and give me push ups on that mat, private!")
        3 -> Pair("Wrong exercise, worm!", "Get yourself over there and jog on that mat, private!")
        4 -> Pair("Wrong exercise, worm!", "Get on that mat and give me sit ups, private!")
        else -> throw IllegalArgumentException("Invalid exercise: $correctExerciseType")
    }
    it.chatNpc(dialogue.first, dialogue.second, npc = sergeantDamien)
}

//Handles the wrong player talking to the NPC.
suspend fun NotAllowedZone(it: QueueTask) {
    it.chatNpc("As you were, soldier.", npc = sergeantDamien)
}

fun getCorrectMatId(exerciseType: Int): Int {
    return when (exerciseType) {
        1 -> 10078 //Star jumps
        2 -> 10077 //Push ups
        3 -> 10079 //Running Man
        4 -> 10076 //Situps
        else -> throw IllegalArgumentException("Invalid exercise: $exerciseType")
    }
}

/**
Handles the interaction between a player and an exercise mat object, executing the specified exercise type.
Evaluates the player's performance and updates the exercise score accordingly.
@param p The player interacting with the exercise mat.
@param obj The exercise mat game object.
@param correctMatId The ID of the correct exercise mat the player should be interacting with.
@param exerciseType The type of exercise to be performed (1: Star jumps, 2: Push ups, 3: Sit ups, 4: Running man).
 */
fun interactWithMat(p: Player, obj: GameObject, correctMatId: Int, exerciseType: Int) {
    val faceSouth = Tile(x = obj.tile.x, z = obj.tile.z - 1)
// Lock the player's actions during this interaction sequence
    p.lockingQueue {
        var ticks = 0
        var isCorrectExercise = false

        // Loop through actions to be executed in sequence
        while (true) {
            when (ticks) {
                // Move player to the exercise mat's position
                1 -> p.moveTo(obj.tile.x, 4820)

                // Face the player to the south
                2 -> p.faceTile(faceSouth)

                // Execute the animation corresponding to the exercise type
                3 -> {
                    when (exerciseType) {
                        1 -> p.animate(2761) // Star jumps
                        2 -> p.animate(2762) // Push ups
                        3 -> p.animate(2763) // Sit ups
                        4 -> p.animate(2764) // Running man
                    }
                }

                // Check if the player is performing the correct exercise
                4 -> {
                    isCorrectExercise = p.getInteractingGameObj()?.id == correctMatId
                    if (isCorrectExercise) {
                        chatNpc("You perform the exercise correctly.", npc = sergeantDamien)
                    } else {
                        chatNpc("You perform the wrong exercise.", npc = sergeantDamien)
                    }
                }

                // Update player's score based on whether the exercise was performed correctly
                5 -> {
                    if (isCorrectExercise) {
                        player.queue {
                            player.attr[EXERCISE_SCORE] = (player.attr[EXERCISE_SCORE] ?: 0) + 1
                            player.attr[CORRECT_EXERCISE] = Random.nextInt(1, 5)
                            val exercise: Int? = player.attr[CORRECT_EXERCISE]
                            if (exercise != null) {
                                afterPerformingCorrectExerciseOrStartingEvent(this, exercise)
                            }
                        }
                    } else {
                        player.queue {
                            player.attr[EXERCISE_SCORE] = 0
                            val exercise: Int? = player.attr[CORRECT_EXERCISE]
                            if (exercise != null) {
                                afterPerformingIncorrectExercise(this)
                            }
                        }
                    }
                    // Unlock player's actions and exit the loop
                    p.unlock()
                    break
                }
            }
            // Increment the tick counter
            ticks++
            // Wait for 1 tick before proceeding to the next action
            wait(1)
        }
    }
}

// Set up an event handler for the "use" option on the exercise mat object
on_obj_option(obj = Objs.EXERCISE_MAT_10079, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    // Check if the exercise mat object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            // Get the correct exercise the player should perform
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]

            // If there is a correct exercise, initiate the interaction with the exercise mat
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 4) // Running man
            }
        }
    }
}

// Set up an event handler for the "use" option on the exercise mat object
on_obj_option(obj = Objs.EXERCISE_MAT_10078, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    // Check if the exercise mat object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            // Get the correct exercise the player should perform
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            // If there is a correct exercise, initiate the interaction with the exercise mat
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 1) // Star Jumps
            }
        }
    }
}

// Set up an event handler for the "use" option on the exercise mat object
on_obj_option(obj = Objs.EXERCISE_MAT_10077, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    // Check if the exercise mat object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            // Get the correct exercise the player should perform
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 2) // Push ups
            }
        }
    }
}

// Set up an event handler for the "use" option on the exercise mat object
on_obj_option(obj = Objs.EXERCISE_MAT, option = "use", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    // Check if the exercise mat object is spawned in the game world
    if (obj.isSpawned(world)) {
        // Queue the player's actions for the interaction
        player.queue {
            // Get the correct exercise the player should perform
            val correctExercise: Int? = player.attr[CORRECT_EXERCISE]
            if (correctExercise != null) {
                interactWithMat(player, obj, correctMatId = getCorrectMatId(correctExercise), exerciseType = 3) // Sit ups
            }
        }
    }
}