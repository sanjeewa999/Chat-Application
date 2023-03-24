
package interfaces;

import java.io.Serializable;


public class ChatClient implements Serializable {
    int id;
    String username;
    String nickname;
    String email;
    //ImageIcon avatar;

    public ChatClient(int id, String username, String nickname, String email) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        //this.avatar = avatar;
    }

    
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

//    public ImageIcon getAvatar() {
//        return avatar;
//    }

 
    
    
}
