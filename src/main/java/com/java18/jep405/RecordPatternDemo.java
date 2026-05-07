package com.java18.jep405;

import com.java18.switchcase.Notification;
import com.java18.switchcase.SMS;
import com.java18.switchcase.Whatsapp;

/**
 * Demonstrates <strong>JEP 405 - Record Patterns</strong> (second preview in JDK 18; pattern matching
 * for {@code instanceof} and {@code switch} with record decomposition is finalized in JDK 21).
 * <p>
 * This class shows:
 * <ul>
 *   <li>record patterns in {@code instanceof};</li>
 *   <li>top-level record patterns in {@code switch};</li>
 *   <li>nested record patterns (a record whose component is itself matched as a record).</li>
 * </ul>
 */
public final class RecordPatternDemo {

    private RecordPatternDemo() {
    }

    /**
     * Wraps a {@link Notification} with a priority so nested record patterns are meaningful.
     */
    private record Delivery(Notification notification, int priority) {
    }

    public static void main(String[] args) {
        System.out.println("JEP 405 - Record patterns");
        System.out.println();

        Notification sms = new SMS("11-99837464", "meeting at 3pm");
        Notification chat = new Whatsapp("1234", "Ada", "19-9988776545");

        System.out.println("--- instanceof with record decomposition ---");
        describeWithInstanceof(sms);
        describeWithInstanceof(chat);
        System.out.println();

        System.out.println("--- switch with record patterns ---");
        System.out.println(describeNotification(sms));
        System.out.println(describeNotification(chat));
        System.out.println();

        System.out.println("--- nested record patterns (Delivery(Notification, int)) ---");
        System.out.println(describeDelivery(new Delivery(sms, 2)));
        System.out.println(describeDelivery(new Delivery(chat, 1)));
    }

    static void describeWithInstanceof(Object value) {
        if (value instanceof SMS(String number, String text)) {
            System.out.println("SMS -> number=" + number + ", text=\"" + text + "\"");
        } else if (value instanceof Whatsapp(String id, String name, String phoneNumber)) {
            System.out.println("Whatsapp -> id=" + id + ", name=" + name + ", phone=" + phoneNumber);
        } else {
            System.out.println("Other: " + value);
        }
    }

    static String describeNotification(Notification n) {
        return switch (n) {
            case SMS(var number, var text) ->
                    "SMS from " + number + ": " + text;
            case Whatsapp(var id, var name, var phone) ->
                    "Whatsapp (" + id + ") " + name + " @ " + phone;
            default ->
                    "Unknown notification";
        };
    }

    static String describeDelivery(Delivery d) {
        return switch (d) {
            case Delivery(SMS(var number, var text), int priority) ->
                    "[p=" + priority + "] SMS " + number + " - " + text;
            case Delivery(Whatsapp(var id, var name, var phone), int priority) ->
                    "[p=" + priority + "] Whatsapp " + name + " (" + id + ") " + phone;
            default ->
                    "[p=?] " + d;
        };
    }
}
