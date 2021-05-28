package org.rmj.guanzongroup.g3msg_notifylib.Model;


import java.io.Serializable;

public class NotificationMessageModel implements Serializable {
    private String MessageID = "";
    private String MessageTitle = "";
    private String MessageSender = "";
    private String MessageBody = "";
    private String MessageDate = "";
    private String MessageStat = "";

    public NotificationMessageModel(String MessageID,
                                    String MessageTitle,
                                    String MessageSender,
                                    String MessageBody,
                                    String MessageDate,
                                    String MessageStat){
        this.MessageID = MessageID;
        this.MessageTitle = MessageTitle;
        this.MessageSender = MessageSender;
        this.MessageBody = MessageBody;
        this.MessageDate = MessageDate;
        this.MessageStat = MessageStat;
    }

    public String getMessageID() {
        return MessageID;
    }

    public String getMessageTitle() {
        return MessageTitle;
    }

    public String getMessageSender(){
        return MessageSender;
    }

    public String getMessageBody(){
        return MessageBody;
    }

    public String getMessageDate() {
        return MessageDate;
    }

    public boolean isRead(){
        if(MessageStat.equalsIgnoreCase("3")){
            return true;
        }
        return false;
    }
}
