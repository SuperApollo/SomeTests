package com.example.administrator.testverticalviewpager;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BroadcustData {

    /**
     * avatar : String
     * broadCastContent : String
     * dollPic : String
     * roomId : Long
     * parentRoomId : Long
     */

    private String avatar;
    private String broadCastContent;
    private String dollPic;
    private String roomId;
    private String parentRoomId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBroadCastContent() {
        return broadCastContent;
    }

    public void setBroadCastContent(String broadCastContent) {
        this.broadCastContent = broadCastContent;
    }

    public String getDollPic() {
        return dollPic;
    }

    public void setDollPic(String dollPic) {
        this.dollPic = dollPic;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getParentRoomId() {
        return parentRoomId;
    }

    public void setParentRoomId(String parentRoomId) {
        this.parentRoomId = parentRoomId;
    }
}
