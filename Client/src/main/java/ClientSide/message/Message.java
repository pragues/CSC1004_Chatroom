package ClientSide.message;

import ClientSide.chatwindow.Emoji;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private String name;
    private MessageType type;
    private String msg;
    private Emoji emoji;
    private String pictureInURL;
    private Date sendTime;


    //Pic:
    public Message() {}
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
    public void setEmoji(Emoji emoji1){ emoji= emoji1;}
    public Emoji getEmoji(){return emoji;}


}
