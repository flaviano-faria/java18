package com.java18.switchcase;

public class Service {

    public static void main(String[] args) {

        Notification whatsapp = new Whatsapp("1234", "nameTest", "19-9988776545");
        Notification sms = new SMS("11-99837464", "this is a sms message");

        switch (sms){
            case SMS smsNotification -> System.out.println("Notification from:"+smsNotification.number());
            default -> System.out.println("Notification isn't valid");
        }
    }
}
