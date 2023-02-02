package com.example.hearthstone_app;

public class SearchModel {
    private String name;
    private String cardSet;
    private String type;
    private String faction;
    private String playerClass;

    public SearchModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }
}
