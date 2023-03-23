package services;

import interfaces.ChatClient;
import interfaces.Message;
import pojos.Groups;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatService extends UnicastRemoteObject implements Chat {

    Message newmsg = null;
    int group_id;
    ArrayList<ChatClient> subs = new ArrayList<>();

    public ChatService(int group_id) throws RemoteException {
        super();
        this.group_id = group_id;
    }

    @Override
    public void send_message(Message msg) {
        System.out.println(msg.getMessage());
        this.newmsg = msg;
        this.save_msg();
    }

    @Override
    public Message broadcast() {
        return this.newmsg;
    }

    public void save_msg() {

        try {
            try (FileOutputStream fileOut = new FileOutputStream("chat_log/"+this.newmsg.getGroup_id()+"_.ser", true); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                
                out.writeObject(this.newmsg);
                out.flush();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void retrive_subs() {
        subs = null;
        FileInputStream filein;
        
        try {
            filein = new FileInputStream("subscribers_log/"+group_id + "_subscribers.ser");
            ObjectInputStream inobj = new ObjectInputStream(filein);
            subs = (ArrayList<ChatClient>) inobj.readObject();
            
        } catch ( IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public boolean is_subscribed(int client_id) {
        //retrive_subs();
      
        boolean subscribed=false;
        for (ChatClient next : subs) {
              System.out.println("for loop");
            
            if (next.getId()==client_id) {
                subscribed=true;
                break;
            }
        }
        return subscribed;
    }
        public boolean is_subscribed_client(int client_id) {
        //retrive_subs();
      
        boolean subscribed=false;
        for (ChatClient next : subs) {
              System.out.println("for loop");
            
            if (next.getId()==client_id) {
                subscribed=true;
                break;
            }
        }
        return subscribed;
    }

    @Override
    public void subscribre(int group_id, ChatClient c) throws RemoteException {
        

        subs.add(c);
        try {
            try (FileOutputStream fileOut = new FileOutputStream("subscribers_log/"+group_id + "_subscribers.ser", true)) {
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                
                out.writeObject(c);
                out.flush();
                out.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @Override
    public void unsubscribre(int group_id, ChatClient c ) throws RemoteException {
        
        System.out.println(subs.get(0).getId());
        subs.removeIf(n -> (n.getId()==c.getId()));
        

//        try {
//            FileInputStream filein = new FileInputStream(group_id + "_subscribers.ser");
//            ObjectInputStream inobj = new ObjectInputStream(filein);
//            ArrayList<ChatClient> subs_list = (ArrayList<ChatClient>) inobj.readObject();
//
//        } catch (Exception e) {
//
//        }

    }

}
