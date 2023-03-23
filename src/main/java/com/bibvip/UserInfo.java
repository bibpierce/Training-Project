package com.bibvip;

public class UserInfo {

    private final int id;
    private final String name;
    private final String pictureFileName;
    private final String vacFileName;
    private final String primaryFileName;
    private final String secondaryFileName;



    public UserInfo(int id, String name, String pictureFileName,
                    String vacFileName, String primaryFileName, String secondaryFileName) {

        this.id = id;
        this.name = name;
        this.pictureFileName = pictureFileName;
        this.vacFileName = vacFileName;
        this.primaryFileName = primaryFileName;
        this.secondaryFileName = secondaryFileName;
    }


    @Override
    public String toString() {
        return
                "=============================================================" +
                        "\n| UserInfo ID: " + id +
                "\n| Name: " + name +
                "\n| Picture File Name: " + pictureFileName +
                "\n| Vaccine Card FileName: " + vacFileName +
                "\n| Primary ID FileName: " + primaryFileName +
                "\n| Secondary ID FileName: " + secondaryFileName ;
    }
}
