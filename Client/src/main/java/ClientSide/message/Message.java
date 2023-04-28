package ClientSide.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {

    private String name;
    private MessageType type;
    private String msg;
    private String pictureInURL;
    private Date sendTime;
    private ArrayList<String> cHandlers;
    private byte[] voiceMessage;

    //Pic:
    public Message() {}
    public void setVoiceMessage(byte[] newVoiceMessage){voiceMessage=newVoiceMessage;}
    public byte[] getVoiceMessage() {return voiceMessage;}
    public void setOnlineUsers(ArrayList<String> onlineUser){cHandlers=onlineUser;}
    public ArrayList<String> getOnlineUsers(){return cHandlers;}
    public void setPicture(String picture) {
        pictureInURL= picture;
    }
    public String getPicture() {return pictureInURL;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //text message
    public String getMsg() {return msg;}
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setSendTime(Date date){sendTime=date;}
    public Date getSendTime(){return sendTime;}

}
