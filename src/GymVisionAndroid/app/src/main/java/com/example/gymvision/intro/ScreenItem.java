package com.example.gymvision.intro;

public class ScreenItem {

    private String screentitle, desc;
    private int image;

    public ScreenItem(String screentitle, String desc, int image) {
        this.screentitle = screentitle;
        this.desc = desc;
        this.image = image;
    }

    //getters
    public String getScreentitle() {
        return screentitle;
    }

    public String getDesc() { return desc; }

    public int getImage() {
        return image;
    }
}
