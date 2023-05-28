package org.example.whatsapp;

import it.auties.whatsapp.api.WebHistoryLength;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.model.contact.ContactStatus;

public class Agent {
    

  public static void main(String []args) throws Exception {   
    System.out.println("Starting ...");
    Whatsapp whatsapp=Whatsapp.webBuilder()
    .newConnection()
    .historyLength(WebHistoryLength.ZERO)
    .build();
    whatsapp.addNewMessageListener(info -> {
       whatsapp.changePresence(info.chat(), ContactStatus.COMPOSING);
       System.out.println("Waiting for messages");
    }
    ).addLoggedInListener(api -> {})
    .addContactsListener((api, contacts) -> {    })
    .addChatsListener(chats -> System.out.printf("Chats: %s%n", chats.size()))
    .addNodeReceivedListener(incoming -> System.out.printf("Received node %s%n", incoming))
    .addNodeSentListener(outgoing -> System.out.printf("Sent node %s%n", outgoing))
    .addActionListener((action, info) -> System.out.printf("New action: %s, info: %s%n", action, info))
    .addSettingListener(setting -> System.out.printf("New setting: %s%n", setting))
    .addContactPresenceListener((chat, contact, status) -> System.out.printf("Status of %s changed in %s to %s%n", contact.name(), chat.name(), status.name()))
    .addAnyMessageStatusListener((chat, contact, info, status) -> System.out.printf("Message %s in chat %s now has status %s for %s %n", info.id(), info.chatName(), status, contact == null ? null : contact.name()))
    .addChatMessagesSyncListener((chat, last) -> System.out.printf("%s now has %s messages: %s%n", chat.name(), chat.messages().size(), !last ? "waiting for more" : "done"))
    .addDisconnectedListener(reason -> System.out.printf("Disconnected: %s%n", reason))
    .connect()
    .join();

System.out.println("Connected");
whatsapp.awaitDisconnection();
System.out.println("Disconnected");
}
}
