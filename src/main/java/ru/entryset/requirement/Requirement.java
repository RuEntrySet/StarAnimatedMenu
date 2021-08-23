package ru.entryset.requirement;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.entryset.tools.Utils;

public class Requirement {

    private Player player;

    private YamlConfiguration yml;

    private RequirementType type;

    private Integer sistem;

    private String value;

    //for open menu
    public Requirement(Player player, YamlConfiguration y, RequirementType type, String value) {
        this.player = player;
        this.yml = y;
        this.sistem = 0;
        this.type = type;
        this.value = value + ".";
        CheckRequirements();
    }

    private Player getPlayer() {
        return this.player;
    }

    private YamlConfiguration getMenu() {
        return this.yml;
    }

    private RequirementType getType() {
        return this.type;
    }

    private String getTypeByName() {
        return getType().name();
    }

    private String getValue() {
        return this.value;
    }

    private Integer getSistem() {
        return this.sistem;
    }

    private void addSistem() {
        this.sistem++;
    }

    private void setSistem(Integer size) {
        this.sistem = size;
    }

    private void CheckRequirements(){
        if(!getMenu().contains(getValue() + getTypeByName() + "_requirements")) {
            return;
        }
        for(String section : getMenu().getConfigurationSection
                (getValue() + getTypeByName() + "_requirements").getKeys(false)) {

            String comparison = getMenu().getString(getValue() + getTypeByName() + "_requirements."
                    + section + ".type");

            Comparison(comparison, section);

        }
    }

    private void Comparison(String comparison, String section){
        switch (comparison){
            case ("has permission"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".permission")) {
                    if(!PermissionRequirement.check(getPlayer()
                            , getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".permission"))) {
                        deny(section);
                    }
                }
                break;
            case ("has item"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".item")) {
                    int amont = 1;
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".amont")) {
                        amont = getMenu().getInt(getValue() + getTypeByName()
                                + "_requirements." + section + ".amont");
                    }
                    if(!ItemRequirement.check(getMenu()
                                    .getString(getValue() + getTypeByName() + "_requirements." + section + ".item")
                            , getPlayer(), amont)) {
                        deny(section);
                    }
                }
                break;
            case ("has money"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".amont")) {
                    if(!MoneyRequirement.check(getPlayer()
                            , getMenu()
                                    .getInt(getValue() + getTypeByName() + "_requirements." + section + ".amont"))) {
                        deny(section);
                    }
                }
                break;
            case ("has dkcoins"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".amont")) {
                    if(!DKCoinsRequirement.check(getPlayer()
                            , getMenu()
                                    .getInt(getValue() + getTypeByName() + "_requirements." + section + ".amont"))) {
                        deny(section);
                    }
                }
                break;
            case ("has playerpoints"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".amont")) {
                    if(!PlayerPointsRequirement.check(getPlayer()
                            , getMenu()
                                    .getInt(getValue() + getTypeByName() + "_requirements." + section + ".amont"))) {
                        deny(section);
                    }
                }
                break;
            case ("script"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".expression")) {
                    if(!JavaScriptRequirement.check(getPlayer()
                            , getMenu()
                                    .getString(getValue() + getTypeByName() + "_requirements." + section + ".expression"))) {
                        deny(section);
                    }
                }
                break;
            case ("string equals ignorecase"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".input")) {
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".output")) {
                        if(!CalculationsRequirement.string(getPlayer(), getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".input")
                                , getMenu()
                                        .getString(getValue() + getTypeByName() + "_requirements." + section + ".output"))) {
                            deny(section);
                        }
                    }
                }
                break;
            case ("=="):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".input")) {
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".output")) {
                        if(!CalculationsRequirement.eqlse(getPlayer(), getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".input")
                                , getMenu()
                                        .getInt(getValue() + getTypeByName() + "_requirements." + section + ".output"))) {
                            deny(section);
                        }
                    }
                }
                break;
            case (">="):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".input")) {
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".output")) {
                        if(!CalculationsRequirement.moreeqlse(getPlayer(), getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".input")
                                , getMenu()
                                        .getInt(getValue() + getTypeByName() + "_requirements." + section + ".output"))) {
                            deny(section);
                        }
                    }
                }
                break;
            case ("<="):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".input")) {
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".output")) {
                        if(!CalculationsRequirement.unmoreeqlse(getPlayer(), getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".input")
                                , getMenu()
                                        .getInt(getValue() + getTypeByName() + "_requirements." + section + ".output"))) {
                            deny(section);
                        }
                    }
                }
                break;
            case (">"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".input")) {
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".output")) {
                        if(!CalculationsRequirement.more(getPlayer(), getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".input")
                                , getMenu()
                                        .getInt(getValue() + getTypeByName() + "_requirements." + section + ".output"))) {
                            deny(section);
                        }
                    }
                }
                break;
            case ("<"):
                if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".input")) {
                    if(getMenu().contains(getValue() + getTypeByName() + "_requirements." + section + ".output")) {
                        if(!CalculationsRequirement.unmore(getPlayer(), getMenu().getString(getValue() + getTypeByName() + "_requirements." + section + ".input")
                                , getMenu()
                                        .getInt(getValue() + getTypeByName() + "_requirements." + section + ".output"))) {
                            deny(section);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void deny(String section){
        addSistem();
        if(getMenu().contains(getValue() + getTypeByName()
                + "_requirements." + section + ".deny_commands")) {
            Utils.denyCommands(getPlayer(), getMenu(), getValue() + getTypeByName()
                    + "_requirements." + section + ".deny_commands");
        }
    }

    public boolean Check() {
        if(getSistem() > 0) {
            return false;
        }
        return true;
    }

}
