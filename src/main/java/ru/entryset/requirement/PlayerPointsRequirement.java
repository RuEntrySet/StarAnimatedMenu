package ru.entryset.requirement;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPointsRequirement {

    public static boolean check(Player p, Integer amont) {
        if(Bukkit.getPluginManager().getPlugin("PlayerPoints") == null) {
            return true;
        }
        PlayerPointsAPI ppAPI = PlayerPoints.getInstance().getAPI();
        if(ppAPI.look(p.getUniqueId()) >= amont) {
            return true;
        }
        return false;
    }

}
