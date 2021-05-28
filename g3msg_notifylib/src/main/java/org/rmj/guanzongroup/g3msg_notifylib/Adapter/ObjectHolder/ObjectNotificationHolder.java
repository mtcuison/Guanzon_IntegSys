package org.rmj.guanzongroup.g3msg_notifylib.Adapter.ObjectHolder;

public class ObjectNotificationHolder {
    private String poSenderx;
    private String poTitlexx;
    private String poMessage;
    private String poDateRcv;
    private String poStatusx;
    private String poMsgType;

    public ObjectNotificationHolder(String Sender,
                                    String Title,
                                    String Message,
                                    String DateReceive,
                                    String Status,
                                    String MessageType){
        this.poSenderx = Sender;
        this.poTitlexx = Title;
        this.poMessage = Message;
        this.poDateRcv = DateReceive;
        this.poStatusx = Status;
        this.poMsgType = MessageType;
    }

    public String getSender(){
        return poSenderx;
    }

    public String getTitle(){
        return poTitlexx;
    }

    public String getMessage(){
        return poMessage;
    }

    public String getDateReceive(){
        return poDateRcv;
    }

    public String getStatus(){
        return poStatusx;
    }

    public boolean isRead(){
        if(poStatusx.equalsIgnoreCase("3")){
            return true;
        }
        return false;
    }

    public String getPoMsgType() {
        return poMsgType;
    }
}
