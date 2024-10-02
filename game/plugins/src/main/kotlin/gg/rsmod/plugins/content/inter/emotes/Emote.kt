package gg.rsmod.plugins.content.inter.emotes

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class Emote(
    val component: Int,
    val anim: Int,
    val gfx: Int = -1,
    val varbit: Int = -1,
    val requiredVarbitValue: Int = 1,
    val unlockDescription: String? = null,
) {
    YES(component = 2, anim = 855),
    NO(component = 3, anim = 856),
    BOW(component = 4, anim = 858),
    ANGRY(component = 5, anim = 859),
    THINK(component = 6, anim = 857),
    WAVE(component = 7, anim = 863),
    SHRUG(component = 8, anim = 2113),
    CHEER(component = 9, anim = 862),
    BECKON(component = 10, anim = 864),
    LAUGH(component = 12, anim = 861),
    JUMP_FOR_JOY(component = 11, anim = 2109),
    YAWN(component = 13, anim = 2111),
    DANCE(component = 14, anim = 866),
    JIG(component = 15, anim = 2106),
    SPIN(component = 16, anim = 2107),
    HEADBANG(component = 17, anim = 2108),
    CRY(component = 18, anim = 860),
    BLOW_KISS(component = 19, anim = 1374, gfx = 1702),
    PANIC(component = 20, anim = 2105),
    RASPBERRY(component = 21, anim = 2110),
    CLAP(component = 22, anim = 865),
    SALUTE(component = 23, anim = 2112),
    GOBLIN_BOW(component = 24, anim = 2127, varbit = EmotesTab.GOBLIN_EMOTES_VARBIT, requiredVarbitValue = 7),
    GOBLIN_SALUTE(component = 25, anim = 2128, varbit = EmotesTab.GOBLIN_EMOTES_VARBIT, requiredVarbitValue = 7),
    GLASS_BOX(component = 26, anim = 1131, varbit = EmotesTab.GLASS_BOX_EMOTE_VARBIT),
    CLIMB_ROPE(component = 27, anim = 1130, varbit = EmotesTab.CLIMB_ROPE_EMOTE_VARBIT),
    LEAN(component = 28, anim = 1129, varbit = EmotesTab.LEAN_EMOTE_VARBIT),
    GLASS_WALL(component = 29, anim = 1128, varbit = EmotesTab.GLASS_WALL_EMOTE_VARBIT),
    IDEA(component = 33, anim = 4276, gfx = 712, varbit = EmotesTab.IDEA_EMOTE_VARBIT),
    STAMP(component = 31, anim = 1745, varbit = EmotesTab.STAMP_EMOTE_VARBIT),
    FLAP(component = 32, anim = 4280, varbit = EmotesTab.FLAP_EMOTE_VARBIT),
    SLAP_HEAD(component = 30, anim = 4275, varbit = EmotesTab.SLAP_HEAD_EMOTE_VARBIT),
    ZOMBIE_WALK(component = 34, anim = 3544, varbit = EmotesTab.ZOMBIE_WALK_EMOTE_VARBIT),
    ZOMBIE_DANCE(component = 35, anim = 3543, varbit = EmotesTab.ZOMBIE_DANCE_EMOTE_VARBIT),
    SCARED(component = 37, anim = 2836, varbit = EmotesTab.SCARED_EMOTE_VARBIT),
    RABBIT_HOP(component = 38, anim = 6111, varbit = EmotesTab.RABBIT_HOP_EMOTE_VARBIT),
    ZOMBIE_HAND(component = 36, anim = 7272, gfx = 1244, varbit = EmotesTab.ZOMBIE_HAND_EMOTE_VARBIT),
    SKILLCAPE(component = 39, anim = -1, varbit = EmotesTab.SKILLCAPE_EMOTE_VARBIT),
    SNOWMAN_DANCE(component = 40, anim = 7531, varbit = EmotesTab.SNOWMAN_EMOTE_VARBIT),
    AIR_GUITAR(component = 41, anim = 2414, gfx = 1537, varbit = EmotesTab.AIR_GUITAR_EMOTE_VARBIT),
    SAFETY_FIRST(component = 42, anim = 8770, gfx = 1553, varbit = EmotesTab.SAFETY_FIRST_EMOTE_VARBIT),
    EXPLORE(component = 43, anim = 9990, gfx = 1734, varbit = EmotesTab.EXPLORE_EMOTE_VARBIT),
    TRICK(component = 44, anim = 10530, gfx = 1864, varbit = EmotesTab.TRICK_EMOTE_VARBIT),
    FREEZE(component = 45, anim = 11044, gfx = 1973, varbit = EmotesTab.FREEZE_EMOTE_VARBIT),
    GIVE_THANKS(component = 46, anim = -1, varbit = EmotesTab.GIVE_THANKS_EMOTE_VARBIT),
    AROUND_THE_WORLD(
        component = 47,
        anim = 11542,
        gfx = 2037,
        varbit = EmotesTab.AROUND_THE_WORLD_EMOTE_VARBIT,
        requiredVarbitValue = 85,
    ),
    DRAMATIC_POINT(component = 48, anim = 12658, gfx = 780, varbit = EmotesTab.DRAMATIC_POINT_EMOTE_VARBIT),
    FAINT(component = 49, anim = 14165, varbit = EmotesTab.FAINT_EMOTE_VARBIT),
    PUPPET_MASTER(
        component = 50,
        anim = 14869,
        gfx = 2837,
        varbit = EmotesTab.PUPPET_MASTER_EMOTE_VARBIT,
        requiredVarbitValue = 20,
    ),
    TASK_MASTER(
        component = 51,
        anim = 15034,
        gfx = 2930,
        varbit = EmotesTab.TASK_MASTER_EMOTE_VARBIT,
        requiredVarbitValue = 428,
    ),
    SEAL_OF_APPROVAL(component = 52, anim = -1, varbit = EmotesTab.SEAL_OF_APPROVAL_EMOTE_VARBIT),
    INVOKE_SPRING(
        component = 53,
        anim = 15357,
        gfx = 1391,
        varbit = EmotesTab.INVOKE_SPRING_EMOTE_VARBIT,
        requiredVarbitValue = 60,
    ),
    ;

    companion object {
        val values = enumValues<Emote>()
    }
}
