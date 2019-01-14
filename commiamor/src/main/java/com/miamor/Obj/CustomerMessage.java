package com.miamor.Obj;

import java.util.Date;

/**
 * Created by Marcot on 8/14/2015.
 */
public class CustomerMessage {

    private int Id ;

    private int VendorId ;

    private int MessageTypeId ;

    private String Title ;

    private String MsgTxt ;

    private boolean WasOpened ;

    private Date DateOpened ;

    private Date DateSend ;

    public CustomerMessage() {
    }

    public CustomerMessage(int id, int vendorId, int messageTypeId, String title, String msgTxt, boolean wasOpened, Date dateOpened, Date dateSend) {
        Id = id;
        VendorId = vendorId;
        MessageTypeId = messageTypeId;
        Title = title;
        MsgTxt = msgTxt;
        WasOpened = wasOpened;
        DateOpened = dateOpened;
        DateSend = dateSend;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getVendorId() {
        return VendorId;
    }

    public void setVendorId(int vendorId) {
        VendorId = vendorId;
    }

    public int getMessageTypeId() {
        return MessageTypeId;
    }

    public void setMessageTypeId(int messageTypeId) {
        MessageTypeId = messageTypeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMsgTxt() {
        return MsgTxt;
    }

    public void setMsgTxt(String msgTxt) {
        MsgTxt = msgTxt;
    }

    public boolean isWasOpened() {
        return WasOpened;
    }

    public void setWasOpened(boolean wasOpened) {
        WasOpened = wasOpened;
    }

    public Date getDateOpened() {
        return DateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        DateOpened = dateOpened;
    }

    public Date getDateSend() {
        return DateSend;
    }

    public void setDateSend(Date dateSend) {
        DateSend = dateSend;
    }
}
