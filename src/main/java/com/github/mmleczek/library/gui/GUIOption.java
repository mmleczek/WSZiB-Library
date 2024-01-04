package com.github.mmleczek.library.gui;

public class GUIOption {
    private int accessLevel;
    private String label;
    private String action;
    private int optionNum = -1;

    public GUIOption(int accessLevel, String label, String action) {
        this.accessLevel = accessLevel;
        this.label = label;
        this.action = action;
    }

    public int getAccessLevel() { return accessLevel; }
    public String getLabel() { return label; }
    public String getAction() { return action; }
    public void setOptionNum(int optionNum) { this.optionNum = optionNum; }
    public int getOptionNum() { return this.optionNum; }
}
