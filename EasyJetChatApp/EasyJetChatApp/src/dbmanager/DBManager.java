/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import controller.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos.Files;
import pojos.Groups;
import pojos.Users;

/**
 *
 * @author Sanjeewa
 */
public class DBManager {
    private static DBManager dbm;

    private DBManager() {
    }

    static {
        dbm = new DBManager();
    }

    public static DBManager getDBM() {
        return dbm;
    }
    
        public List loginHandler(String username, String password) {
        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Users WHERE username='" + username + "' AND password='" + password + "'";
        Query qu = sess.createQuery(sql);
        List User = qu.list();
        return User;
    }
        

        
        
        public boolean insert(byte[] avatar64based, String email, String username, String nickname, String password) {
        Session s = Connection.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Users user = new Users();

        if (avatar64based != null) {
            user.setProfileImage(avatar64based);
        }
        user.setEmail(email);
        user.setUsername(username);
        user.setNickname(nickname);
         user.setUserType("normal");
        user.setPassword(password);

        s.save(user);
        t.commit();
        s.close();
        return true;

    }
        
      public void update(byte[] img, String username, String nickname,String password, int id) {
        Session s = Connection.getSessionFactory().openSession();
        Transaction tran = s.beginTransaction();

        Users user = (Users) s.load(Users.class, id);
        user.setProfileImage(img);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);
        
        
        s.update(user);
        tran.commit();
        System.out.println("User updated successfully..");
        s.close();
    }
        
      public boolean create_chat_group(String chatname, String chatdes) {
        Session s = Connection.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Groups groups = new Groups();

        Time time = Time.valueOf(LocalTime.now());
        Date date = Date.valueOf(LocalDate.now());

        groups.setName(chatname);
        groups.setDescription(chatdes);
        groups.setCreatedDate(date);
        groups.setCreatedTime(time);
        groups.setStatus(false);

        try {
            s.save(groups);
            t.commit();
            s.close();
            
            System.out.println("create group ");
            this.init_msg_file(chatname);
            
            return true;
        } catch (Exception e) {
            return false;
        }

    }
      
          public void init_msg_file(String chat_name) {

        Session s = Connection.getSessionFactory().openSession();
        String sql = "FROM Groups WHERE name='" + chat_name + "'";
        Query qu = s.createQuery(sql);
        List Group = qu.list();

        Iterator i = Group.iterator();
        int chat_id=0;
        if (i.hasNext()) {
            Groups g = (Groups) i.next();
            chat_id = g.getId();
        }


        Transaction t = s.beginTransaction();
        Files files = new Files();

        files.setChatId(chat_id);
        files.setLink("chat_log/"+chat_id + "_.ser");

        s.save(files);
        t.commit();
        s.close();

    }
      
      
//      Get Chat Groups 
      public List get_chat_groups() {
        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Groups";
        Query qu = sess.createQuery(sql);
        List Group = qu.list();
        
        return Group;
    }
      
      //      Get Chat Groups 
      public List get_all_users() {
        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Users";
        Query qu = sess.createQuery(sql);
        List Group = qu.list();
        
        return Group;
    }
      
       public void delete_user(int user_id) {
        
        Session sess = Connection.getSessionFactory().openSession();
        Transaction tran = sess.beginTransaction();
        
        Object object = sess.load(Users.class, user_id);

        sess.delete(object);
        
        tran.commit();
        sess.close();
    }
      
      
      
      
      public boolean is_online(int chat_id) {
        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Groups WHERE status=1 AND id=" + chat_id;
        Query qu = sess.createQuery(sql);
        List Group = qu.list();

        Iterator i = Group.iterator();
        if (i.hasNext()) {
            return true;
        } else {
            return false;
        }
    }
      
    public boolean check_all_offline() {
        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Groups WHERE status=1";
        Query qu = sess.createQuery(sql);
        List Group = qu.list();

        Iterator i = Group.iterator();
        if (i.hasNext()) {
            return false;
        } else {
            return true;
        }
    }
      
      
    public boolean put_online(int chat_id) {
        
       

        if (check_all_offline()) {
            
   

            Session s = Connection.getSessionFactory().openSession();
            Transaction tran = s.beginTransaction();

            Groups group = (Groups) s.load(Groups.class, chat_id);
            group.setStatus(true);

            s.update(group);
            tran.commit();
            s.close();

            return true;
        } else {
       
            return false;
        }
    }
    
    
        public void put_offline(int id) {
        Session s = Connection.getSessionFactory().openSession();
        Transaction tran = s.beginTransaction();

        Groups groups = (Groups) s.load(Groups.class, id);
        groups.setStatus(false);

        s.update(groups);
        tran.commit();
        System.out.println(id + "offline...");
        s.close();
    }
    
    
      
      
      
      
      
      
      
//      All Users
          
        public List allUsers() {
        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Users WHERE";
        Query qu = sess.createQuery(sql);
        List Users = qu.list();
        return Users;
    }
        
    
     public List get_avatart(int user_id) {

        Session sess = Connection.getSessionFactory().openSession();
        String sql = "FROM Users WHERE id='" + user_id + "'";
        Query qu = sess.createQuery(sql);
        List User = qu.list();
        return User;
    }
        
    
}
