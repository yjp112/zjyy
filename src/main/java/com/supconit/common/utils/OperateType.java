package com.supconit.common.utils;

public enum OperateType {

    LEFT("left"),RIGHT("right"),
    UP("up"),DOWN("down"),LEFTUP("leftup"),LEFTDOWN("leftdown"),RIGHTUP("rightup"),RIGHTDOWN("rightdown"),
    SCAN("scan"),
    ZOOMIN("zoomin"),ZOOMOUT("zoomout"),
    FOCUSIN("focusnear"),FOCUSOUT("focusfar"),
    IRISOPEN("irisopen"),IRISCLOSE("irisclose"),
    SCREEN("Ma"),CAMERA("#a");

    private final String type;

    private OperateType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public static String getType(int i){
        String command = "";
        switch (i){
            case 1:
                command = LEFT.getType();
                break;
            case 2:
                command = LEFTUP.getType();
                break;
            case 3:
                command = UP.getType();
                break;
            case 4:
                command = RIGHTUP.getType();
                break;
            case 5:
                command = RIGHT.getType();
                break;
            case 6:
                command = RIGHTDOWN.getType();
                break;
            case 7:
                command = DOWN.getType();
                break;
            case 8:
                command = LEFTDOWN.getType();
                break;
            case 9:
                command = SCAN.getType();
                break;
            case 10:
                command = ZOOMIN.getType();
                break;
            case 11:
                command = ZOOMOUT.getType();
                break;
            case 12:
                command = FOCUSIN.getType();
                break;
            case 13:
                command = FOCUSOUT.getType();
                break;
            case 14:
                command = IRISOPEN.getType();
                break;
            case 15:
                command = IRISCLOSE.getType();
                break;
            default:
                break;
        }

        return command;
    }
}
