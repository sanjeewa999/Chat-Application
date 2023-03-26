/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dbmanager.DBManager;
import interfaces.icons.Chat_ball;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import pojos.Groups;
import pojos.Users;
import services.Chat;
import services.ChatService;

/**
 *
 * @author ASUS
 */
public class AppLayout extends javax.swing.JFrame {
    
    int id;
    int active_group;
   Registry reg;
    Chat chat;static int xx, yy;
    static Chat_ball chat_ball;
    ChatClient me;

   

    /**
     * Creates new form java
     */
    public AppLayout() {
        initComponents();
        
        textusername.setBackground(new java.awt.Color(0,0,0,1));
        textpassword.setBackground(new java.awt.Color(0,0,0,1));
        textregemail.setBackground(new java.awt.Color(0,0,0,1));
        textregusername.setBackground(new java.awt.Color(0,0,0,1));
        textregnickname.setBackground(new java.awt.Color(0,0,0,1));
        textregpassword.setBackground(new java.awt.Color(0,0,0,1));
        textgroupname.setBackground(new java.awt.Color(0,0,0,1));
        textgroupdescription.setBackground(new java.awt.Color(0,0,0,1));
    
        edit_username.setBackground(new java.awt.Color(0,0,0,1));
        edit_nickname.setBackground(new java.awt.Color(0,0,0,1));
        edit_password.setBackground(new java.awt.Color(0,0,0,1));
        client_chat_groups_panel.setBackground(new java.awt.Color(0,0,0,1));
        msg_typer.setBackground(new java.awt.Color(0,0,0,1));


        
        login_panel.setVisible(true);
        register_panel.setVisible(false);
        admin_panel.setVisible(false);
        create_chat_panel.setVisible(false);
        list_groups_panel.setVisible(false);
        chat_panel.setVisible(false);
        edit_profile_panel.setVisible(false);
        manage_users_panel.setVisible(false);
        
        
    }
    
    

    public void app_ui_reset(){
        login_panel.setVisible(false);
        register_panel.setVisible(false);
        admin_panel.setVisible(false);
        create_chat_panel.setVisible(false);
        list_groups_panel.setVisible(false);
        chat_panel.setVisible(false);
        edit_profile_panel.setVisible(false);
        manage_users_panel.setVisible(false);
        
    }
    
    public ImageIcon toImageIcon(byte[] img) {
        BufferedImage Imgavatar;
        ImageIcon avatar = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(img);
            Imgavatar = ImageIO.read(bis);
            if (Imgavatar != null) {
                avatar = new ImageIcon(Imgavatar);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return avatar;
    }
    
    public void showUsers(){
          List data = DBManager.getDBM().allUsers();

     JTable tbl = new JTable();
     DefaultTableModel table_users = new DefaultTableModel(0, 0);
     String header[] = new String[] { "Prority", "Task Title", "Start",
      "Pause", "Stop", "Statulses" };
      table_users.setColumnIdentifiers(header);
      tbl.setModel(table_users);



          for (Iterator iterator = data.iterator(); iterator.hasNext();){
            Users user = (Users) iterator.next(); 
            
            table_users.addRow(new Object[] { "data", "data", "data",
        "data", "data", "data" });
 
          }
          
          
      }
    
    public ArrayList<String> validatelogin(String username, String password) {
        ArrayList<String> errors = new ArrayList<>();

        if ("Username".equals(username) || "".equals(username)) {
            errors.add("Username is requird");
        }

        if ("Password".equals(password) || "".equals(password)) {
            errors.add("Password is requird");
        }

        return errors;
    }
    
    
    public ArrayList<String> validateform(String email, String username,String password) {

        ArrayList<String> errors = new ArrayList<>();

        if (email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") == false) {
            errors.add("Invalid email");
        }

        if ("Username".equals(username) || "".equals(username)) {
            errors.add("Username is requird");
        }

        if ("Password".equals(password) || "".equals(password)) {
            errors.add("Password is requird");
        }

        if (password.length() < 4) {
            errors.add("Password must contain more than 4 characters");
        }

        return errors;
    }
    
        public BufferedImage ImageIconToBufferedImage(ImageIcon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        return bi;
    }
        
         public void sender() {
        String m = msg_typer.getText();
        if (m.equalsIgnoreCase("bye")) {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time_now = myDateObj.format(myFormatObj);

            
            Message msg = new Message();
            msg.setDate_time(time_now);
            String user = me.getUsername();
            m = "****** " + user  + " has left the chat " + " ******";

       
            try {
                chat.unsubscribre(enterd_grup_id, me);
            } catch (RemoteException ex) {
                Logger.getLogger(AppLayout.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            app_ui_reset();
            login_panel.setVisible(true);


            System.out.println(m);
            msg.setMessage(m);
            
  
            
            JScrollBar vertical = msgScrollPane.getVerticalScrollBar();
            msgScrollPane.setMaximumSize(vertical.getMaximumSize());
        

            try {
                chat.send_message(msg);
                
                msg_typer.setText("");
            } catch (RemoteException ex) {
                System.out.println(ex);
            }
            
            msgScrollPane.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });
        } else {

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time_now = myDateObj.format(myFormatObj);

            Message msg = new Message();
            msg.setGroup_id(enterd_grup_id);
            msg.setMsgid(msg.hashCode());
            msg.setUserid(me.getId());
            msg.setName(me.getUsername());
            msg.setMessage(m);
            msg.setDate_time(time_now);

            try {
                chat.send_message(msg);
                
                
           
        
                msg_typer.setText("");
            } catch (RemoteException ex) {
                System.out.println(ex);
            }
        }
        


    }
    
    
     int y = 13;
    
    public void load_admin_group(boolean is_called_signin) {
            
         y = 13;
         List groups = DBManager.getDBM().get_chat_groups();

         admin_group_list.removeAll();

         
            for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
            Groups next = (Groups) iterator.next();

            if (is_called_signin) {
             
                DBManager.getDBM().put_offline(next.getId());
            }

            JPanel group = new javax.swing.JPanel();
            group.setBackground(new java.awt.Color(66, 72, 245));
        
            group.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            JLabel g_action = new javax.swing.JLabel();
            g_action.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (DBManager.getDBM().is_online(next.getId())) {
                g_action.setIcon(new javax.swing.ImageIcon(getClass().getResource("../images/end.png"))); // NOI18N
            } else {
                g_action.setIcon(new javax.swing.ImageIcon(getClass().getResource("../images/start.png"))); // NOI18N
            }

            g_action.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                
                    active_group = next.getId();
                    group_action(next.getId(), g_action);

                }
            });
            

            JLabel g_des = new javax.swing.JLabel();
            g_des.setForeground(new java.awt.Color(255, 255, 255));
            g_des.setText("<html>" + next.getDescription() + "</html>");
            
            

            JLabel g_name = new javax.swing.JLabel();
            g_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
            g_name.setForeground(new java.awt.Color(255, 255, 255));
            g_name.setText(next.getName());
            group.add(g_action, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, 29));
            group.add(g_des, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 36, 163, 33));
            group.add(g_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 160, -1));
            admin_group_list.add(group, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, y, 294, 90));
            

            y += 110;
        
        
        
         }
        }
    
      public void start_client() {

        try {
            reg = LocateRegistry.getRegistry("localhost", 2123);
            chat = (Chat) reg.lookup("ChatAdmin");

        } catch (RemoteException | NotBoundException ex) {
            System.out.println(ex);
        }

    }
    
    public void group_action(int chat_id, JLabel g_action) {
            

            File btn_icon = new File("");
            String abspath = btn_icon.getAbsolutePath();

            if (DBManager.getDBM().is_online(chat_id)) {
              DBManager.getDBM().put_offline(chat_id);
                ImageIcon icon = new ImageIcon(abspath + "\\src\\interfaces\\icons\\start.png");
                g_action.setIcon(icon);
            } else if (DBManager.getDBM().put_online(chat_id)) {
                
                 System.out.println("chat is offline");
                ImageIcon icon = new ImageIcon(abspath + "\\src\\interfaces\\icons\\end.png");
                g_action.setIcon(icon);
                
                start_server(chat_id);


            }
    }
    
    public void start_server(int g_id) {
        try {
        Chat chat = new ChatService(g_id);
            Registry reg = LocateRegistry.createRegistry(2123);
            reg.bind("ChatAdmin", chat);

            System.out.println("Chat server is running...");

        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println("Exception ocured : " + e.getMessage());
        }
    }
    
    int y1 = 13;
        
        
                   
               
        public void load_client_groups() {
            
        List chats = DBManager.getDBM().get_chat_groups();
        client_chat_groups_panel.removeAll();
        
            for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
            Groups next = (Groups) iterator.next();
            
       

            JPanel client_grp_panel = new javax.swing.JPanel();
            client_grp_panel.setBackground(new java.awt.Color(66, 72, 245));
            client_grp_panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            client_grp_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
            
            client_grp_panel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    enter_to_chat(next.getId());

                }
            });
            
            boolean is_sub = false;
            
            JLabel subscribe = new javax.swing.JLabel();

            if (is_sub) {
                subscribe.setIcon(new javax.swing.ImageIcon(getClass().getResource("../images/unsubscribe.png"))); // NOI18N
            } else {
                subscribe.setIcon(new javax.swing.ImageIcon(getClass().getResource("../images/subscribe.png"))); // NOI18N
            }

            if (next.isStatus()== true) {
                subscribe.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        subscribe_action(next.getId(), subscribe);
                        String m = msg_typer.getText();
  
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String time_now = myDateObj.format(myFormatObj);
                        Message msg = new Message();
                        msg.setDate_time(time_now);
                        String user = me.getUsername();
                        m = "****** " + user  + " has join the chat " + " ******";
                        msg.setMessage(m);
                        try {
                            
                            chat.send_message(msg);
                            System.out.println(msg);
                        } catch (RemoteException ex) {
                            Logger.getLogger(AppLayout.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    }
                });

            } else {
                subscribe.setEnabled(false);
                subscribe.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }

            JLabel grp_dec = new javax.swing.JLabel();
            grp_dec.setForeground(new java.awt.Color(255, 255, 255));
            grp_dec.setText(next.getDescription());

            JLabel statuts_txt = new javax.swing.JLabel();
            statuts_txt.setBackground(new java.awt.Color(28, 36, 47));
            statuts_txt.setForeground(new java.awt.Color(255, 255, 255));

            JLabel statuts_icon = new javax.swing.JLabel();

            if (next.isStatus()== true) {
                statuts_txt.setText("online");
                statuts_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("../images/online.png")));
            } else {
                statuts_txt.setText("offline");
                statuts_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("../images/offline.png")));
            }

            JLabel grp_name = new javax.swing.JLabel();
            grp_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
            grp_name.setForeground(new java.awt.Color(255, 255, 255));
            grp_name.setText(next.getName());

            client_grp_panel.add(subscribe, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 42, 99, 35));
            client_grp_panel.add(grp_dec, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 42, 160, 35));
            client_grp_panel.add(statuts_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 13, 51, -1));
            client_grp_panel.add(statuts_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 13, 18, 16));
            client_grp_panel.add(grp_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 160, -1));
            client_chat_groups_panel.add(client_grp_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, y1, 299, 96));

            y1 += 110;

     
        }
        }
        
        
            public void subscribe_action(int grp_id, JLabel sub_btn) {
        try {
            File btn_icon = new File("");
            String abspath = btn_icon.getAbsolutePath();

            if (chat.is_subscribed(me.getId())) {
                chat.unsubscribre(grp_id, me);
                ImageIcon icon = new ImageIcon(abspath + "\\src\\images\\subscribe.png");
                sub_btn.setIcon(icon);
            } else {
                chat.subscribre(grp_id, me);
                ImageIcon icon = new ImageIcon(abspath + "\\src\\images\\unsubscribe.png");
                sub_btn.setIcon(icon);
            }

        } catch (RemoteException ex) {
            System.out.println(ex);
        }
    }
    
        
        static int enterd_grup_id;
        public void enter_to_chat(int grup_id) {
            try {
                if (chat.is_subscribed(me.getId())) {
                    app_ui_reset();
                    chat_panel.setVisible(true);
                    
                    enterd_grup_id = grup_id;
                    retrivemsg.start();
                }

            } catch (RemoteException ex) {
                System.out.println(ex);
            }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        login_panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textusername = new javax.swing.JTextField();
        textpassword = new javax.swing.JTextField();
        user = new javax.swing.JLabel();
        disable = new javax.swing.JLabel();
        show = new javax.swing.JLabel();
        btnlogin = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        linkreg = new javax.swing.JLabel();
        text_login_errors = new javax.swing.JLabel();
        register_panel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        signup_profile_pic = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        textregemail = new javax.swing.JTextField();
        textregusername = new javax.swing.JTextField();
        textregnickname = new javax.swing.JTextField();
        textregpassword = new javax.swing.JTextField();
        show2 = new javax.swing.JLabel();
        disable2 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btnreg = new javax.swing.JButton();
        linklog = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        text_reg_errors = new javax.swing.JLabel();
        create_chat_panel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        text_admin_username2 = new javax.swing.JLabel();
        img_profile3 = new javax.swing.JLabel();
        btn_chat_groups = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        logout1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        textgroupname = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        textgroupdescription = new javax.swing.JTextField();
        btn_create_group = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        group_create_text = new javax.swing.JLabel();
        list_groups_panel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        text_user_username = new javax.swing.JLabel();
        img_profile5 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        edit_profile_link_1 = new javax.swing.JLabel();
        logout2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        client_chat_groups_panel = new javax.swing.JPanel();
        chat_panel = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        text_user_username1 = new javax.swing.JLabel();
        img_profile4 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        logout3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        msg_typer = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        send_btn = new javax.swing.JButton();
        msgScrollPane = new javax.swing.JScrollPane();
        chat_background = new javax.swing.JPanel();
        msg_typer1 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        send_btn1 = new javax.swing.JButton();
        edit_profile_panel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        text_user_username2 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        logout4 = new javax.swing.JLabel();
        img_profile2 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        edit_password = new javax.swing.JPasswordField();
        jLabel75 = new javax.swing.JLabel();
        disable3 = new javax.swing.JLabel();
        btnreg1 = new javax.swing.JButton();
        show3 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        edit_username = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        update_msg = new javax.swing.JLabel();
        edit_profile_image = new javax.swing.JLabel();
        text_reg_errors2 = new javax.swing.JLabel();
        edit_nickname = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        manage_users_panel = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        img_profile6 = new javax.swing.JLabel();
        text_admin_username3 = new javax.swing.JLabel();
        btn_chat_groups1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        logout6 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        userlist1 = new javax.swing.JComboBox();
        remove_user = new javax.swing.JLabel();
        text_delete = new javax.swing.JLabel();
        admin_panel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        img_profile = new javax.swing.JLabel();
        text_admin_username = new javax.swing.JLabel();
        create_group3 = new javax.swing.JLabel();
        create_group = new javax.swing.JLabel();
        link_all_users = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        admin_group_list = new javax.swing.JPanel();
        create_group2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(930, 620));

        login_panel.setPreferredSize(new java.awt.Dimension(930, 620));
        login_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setBackground(new java.awt.Color(51, 51, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        login_panel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 630));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 102));
        jLabel2.setText("EasyJetChat");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 190, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 102));
        jLabel3.setText("Java Based Chat Application");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 153));
        jLabel4.setText("Password :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 153));
        jLabel5.setText("User Name :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        textusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textusernameActionPerformed(evt);
            }
        });
        jPanel2.add(textusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 260, 40));

        textpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textpasswordActionPerformed(evt);
            }
        });
        jPanel2.add(textpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 260, 40));

        user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_user_20px_1.png"))); // NOI18N
        jPanel2.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 40, 50));

        disable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_invisible_20px_1.png"))); // NOI18N
        disable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disableMouseClicked(evt);
            }
        });
        jPanel2.add(disable, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 60, 50));

        show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_eye_20px_1.png"))); // NOI18N
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMouseClicked(evt);
            }
        });
        jPanel2.add(show, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 60, 50));

        btnlogin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnlogin.setForeground(new java.awt.Color(0, 51, 51));
        btnlogin.setText("LOGIN");
        btnlogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnloginMouseClicked(evt);
            }
        });
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });
        jPanel2.add(btnlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 120, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 255));
        jLabel6.setText("Don't you have an account ? ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, -1, -1));

        linkreg.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        linkreg.setForeground(new java.awt.Color(102, 0, 255));
        linkreg.setText("REGISTER");
        linkreg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linkregMouseClicked(evt);
            }
        });
        jPanel2.add(linkreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 460, -1, -1));
        jPanel2.add(text_login_errors, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 240, 30));

        login_panel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 590, 630));

        register_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        signup_profile_pic.setFont(new java.awt.Font("Bookman Old Style", 0, 24)); // NOI18N
        signup_profile_pic.setText("signup_profile_pic");
        signup_profile_pic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signup_profile_picMouseClicked(evt);
            }
        });
        jPanel3.add(signup_profile_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 380, 370));

        register_panel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 620));

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 0, 102));
        jLabel22.setText("Registration Form");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 280, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 0, 153));
        jLabel23.setText("User Name");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 0, 153));
        jLabel24.setText("Nick Name");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 0, 153));
        jLabel25.setText("Password");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 0, 153));
        jLabel26.setText("E-Mail");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));
        jPanel4.add(textregemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 310, 40));

        textregusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textregusernameActionPerformed(evt);
            }
        });
        jPanel4.add(textregusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 310, 40));
        jPanel4.add(textregnickname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 310, 40));
        jPanel4.add(textregpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 392, 310, 40));

        show2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_eye_20px_1.png"))); // NOI18N
        show2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show2MouseClicked(evt);
            }
        });
        jPanel4.add(show2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, 60, 60));

        disable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_invisible_20px_1.png"))); // NOI18N
        disable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disable2MouseClicked(evt);
            }
        });
        jPanel4.add(disable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 380, 60, 60));

        jLabel29.setBackground(new java.awt.Color(51, 51, 51));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_user_20px_1.png"))); // NOI18N
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 60, 60));

        btnreg.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnreg.setForeground(new java.awt.Color(0, 51, 51));
        btnreg.setText("REGISTER");
        btnreg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnregMouseClicked(evt);
            }
        });
        btnreg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregActionPerformed(evt);
            }
        });
        jPanel4.add(btnreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 230, -1));

        linklog.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        linklog.setForeground(new java.awt.Color(102, 0, 255));
        linklog.setText("Login");
        linklog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linklogMouseClicked(evt);
            }
        });
        jPanel4.add(linklog, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 560, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 0, 255));
        jLabel28.setText("Do You Have an Account ? ");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 560, -1, -1));
        jPanel4.add(text_reg_errors, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, 310, 30));

        register_panel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 460, 620));

        create_chat_panel.setPreferredSize(new java.awt.Dimension(930, 620));

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(261, 620));

        text_admin_username2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        text_admin_username2.setForeground(new java.awt.Color(102, 0, 102));
        text_admin_username2.setText("Welcome Admin");

        img_profile3.setBackground(new java.awt.Color(102, 102, 255));
        img_profile3.setForeground(new java.awt.Color(102, 102, 255));
        img_profile3.setOpaque(true);

        btn_chat_groups.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/groups.png"))); // NOI18N
        btn_chat_groups.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_chat_groupsMouseClicked(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Create Groups Active.png"))); // NOI18N

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users.png"))); // NOI18N
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });

        logout1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log out.png"))); // NOI18N
        logout1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logout1)
                    .addComponent(jLabel40)
                    .addComponent(jLabel38)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(text_admin_username2)
                        .addComponent(btn_chat_groups)))
                .addGap(53, 53, 53))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(img_profile3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(img_profile3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(text_admin_username2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btn_chat_groups)
                .addGap(32, 32, 32)
                .addComponent(jLabel38)
                .addGap(31, 31, 31)
                .addComponent(jLabel40)
                .addGap(31, 31, 31)
                .addComponent(logout1)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(204, 255, 204));
        jPanel8.setPreferredSize(new java.awt.Dimension(606, 620));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 0, 102));
        jLabel42.setText("CREATE A CHAT GROUP");
        jPanel8.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(51, 102, 255));
        jLabel55.setText("Group Name");
        jPanel8.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        textgroupname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textgroupname.setForeground(new java.awt.Color(255, 255, 255));
        textgroupname.setBorder(null);
        textgroupname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textgroupnameActionPerformed(evt);
            }
        });
        jPanel8.add(textgroupname, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 460, 30));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(51, 102, 255));
        jLabel52.setText("Group Description");
        jPanel8.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        textgroupdescription.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textgroupdescription.setForeground(new java.awt.Color(255, 255, 255));
        textgroupdescription.setBorder(null);
        textgroupdescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textgroupdescriptionActionPerformed(evt);
            }
        });
        jPanel8.add(textgroupdescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 460, 30));

        btn_create_group.setBackground(new java.awt.Color(255, 255, 255));
        btn_create_group.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_create_group.setForeground(new java.awt.Color(0, 153, 255));
        btn_create_group.setText("CREATE GROUP");
        btn_create_group.setBorder(null);
        btn_create_group.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_create_group.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_create_groupMouseClicked(evt);
            }
        });
        btn_create_group.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_groupActionPerformed(evt);
            }
        });
        jPanel8.add(btn_create_group, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 460, 44));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("________________________________________________");
        jPanel8.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 460, -1));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("________________________________________________");
        jPanel8.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 460, -1));

        group_create_text.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        group_create_text.setForeground(new java.awt.Color(255, 0, 51));
        group_create_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel8.add(group_create_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 270, 20));

        javax.swing.GroupLayout create_chat_panelLayout = new javax.swing.GroupLayout(create_chat_panel);
        create_chat_panel.setLayout(create_chat_panelLayout);
        create_chat_panelLayout.setHorizontalGroup(
            create_chat_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(create_chat_panelLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE))
        );
        create_chat_panelLayout.setVerticalGroup(
            create_chat_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        list_groups_panel.setPreferredSize(new java.awt.Dimension(930, 620));

        jPanel9.setBackground(new java.awt.Color(204, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(303, 620));

        text_user_username.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        text_user_username.setForeground(new java.awt.Color(102, 0, 102));
        text_user_username.setText("Welcome User");

        img_profile5.setOpaque(true);

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Join active.png"))); // NOI18N

        edit_profile_link_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        edit_profile_link_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_profile_link_1MouseClicked(evt);
            }
        });

        logout2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log out.png"))); // NOI18N
        logout2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(img_profile5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(edit_profile_link_1)
                            .addComponent(text_user_username)
                            .addComponent(jLabel60)
                            .addComponent(logout2))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(img_profile5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(text_user_username, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(jLabel60)
                .addGap(29, 29, 29)
                .addComponent(edit_profile_link_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(logout2)
                .addGap(99, 99, 99))
        );

        jPanel10.setBackground(new java.awt.Color(204, 255, 204));
        jPanel10.setPreferredSize(new java.awt.Dimension(580, 620));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(204, 0, 102));
        jLabel63.setText("Chat Groups");
        jPanel10.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, -1, -1));

        jScrollPane2.setForeground(new java.awt.Color(0, 51, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setToolTipText("");

        client_chat_groups_panel.setBackground(new java.awt.Color(102, 204, 255));
        client_chat_groups_panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        client_chat_groups_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane2.setViewportView(client_chat_groups_panel);

        jPanel10.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 520, 390));

        javax.swing.GroupLayout list_groups_panelLayout = new javax.swing.GroupLayout(list_groups_panel);
        list_groups_panel.setLayout(list_groups_panelLayout);
        list_groups_panelLayout.setHorizontalGroup(
            list_groups_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_groups_panelLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        list_groups_panelLayout.setVerticalGroup(
            list_groups_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        chat_panel.setPreferredSize(new java.awt.Dimension(930, 620));

        jPanel11.setBackground(new java.awt.Color(204, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(251, 620));

        text_user_username1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        text_user_username1.setForeground(new java.awt.Color(102, 0, 102));
        text_user_username1.setText("Welcome User");

        img_profile4.setOpaque(true);

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Join active.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N

        logout3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log out.png"))); // NOI18N
        logout3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_user_username1)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel46)
                        .addComponent(jLabel13)
                        .addComponent(logout3)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(img_profile4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(img_profile4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(text_user_username1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(logout3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(204, 255, 204));
        jPanel12.setPreferredSize(new java.awt.Dimension(660, 620));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(204, 0, 102));
        jLabel49.setText("GROUP CHAT");
        jPanel12.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        msg_typer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        msg_typer.setForeground(new java.awt.Color(255, 255, 255));
        msg_typer.setBorder(null);
        msg_typer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_typerActionPerformed(evt);
            }
        });
        msg_typer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msg_typerKeyPressed(evt);
            }
        });
        jPanel12.add(msg_typer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 420, 50));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("________________________________________________");
        jPanel12.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 420, -1));

        send_btn.setBackground(new java.awt.Color(255, 255, 255));
        send_btn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        send_btn.setForeground(new java.awt.Color(0, 153, 255));
        send_btn.setText("SEND");
        send_btn.setBorder(null);
        send_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        send_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                send_btnMouseClicked(evt);
            }
        });
        send_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_btnActionPerformed(evt);
            }
        });
        jPanel12.add(send_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 120, 50));

        msgScrollPane.setBackground(new java.awt.Color(102, 204, 255));

        chat_background.setBackground(new java.awt.Color(51, 204, 255));
        chat_background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        msgScrollPane.setViewportView(chat_background);

        jPanel12.add(msgScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 510, 270));

        msg_typer1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        msg_typer1.setForeground(new java.awt.Color(255, 255, 255));
        msg_typer1.setBorder(null);
        msg_typer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_typer1ActionPerformed(evt);
            }
        });
        msg_typer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msg_typer1KeyPressed(evt);
            }
        });
        jPanel12.add(msg_typer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 420, 50));

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("________________________________________________");
        jPanel12.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 420, -1));

        send_btn1.setBackground(new java.awt.Color(255, 255, 255));
        send_btn1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        send_btn1.setForeground(new java.awt.Color(0, 153, 255));
        send_btn1.setText("SEND");
        send_btn1.setBorder(null);
        send_btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        send_btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                send_btn1MouseClicked(evt);
            }
        });
        send_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_btn1ActionPerformed(evt);
            }
        });
        jPanel12.add(send_btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 120, 50));

        javax.swing.GroupLayout chat_panelLayout = new javax.swing.GroupLayout(chat_panel);
        chat_panel.setLayout(chat_panelLayout);
        chat_panelLayout.setHorizontalGroup(
            chat_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chat_panelLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE))
        );
        chat_panelLayout.setVerticalGroup(
            chat_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        edit_profile_panel.setPreferredSize(new java.awt.Dimension(930, 620));

        jPanel13.setBackground(new java.awt.Color(204, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(251, 620));

        text_user_username2.setBackground(new java.awt.Color(255, 255, 255));
        text_user_username2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        text_user_username2.setForeground(new java.awt.Color(102, 0, 102));
        text_user_username2.setText("Welcome User");

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/join group.png"))); // NOI18N
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
        });

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Profile edit Active.png"))); // NOI18N

        logout4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log out.png"))); // NOI18N
        logout4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout4MouseClicked(evt);
            }
        });

        img_profile2.setOpaque(true);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logout4)
                    .addComponent(jLabel56)
                    .addComponent(jLabel58)
                    .addComponent(text_user_username2))
                .addGap(42, 42, 42))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(img_profile2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(img_profile2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(text_user_username2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel58)
                .addGap(42, 42, 42)
                .addComponent(jLabel56)
                .addGap(38, 38, 38)
                .addComponent(logout4)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(204, 255, 204));
        jPanel17.setPreferredSize(new java.awt.Dimension(805, 620));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(204, 0, 102));
        jLabel43.setText("EDIT PROFILE");

        jLabel57.setBackground(new java.awt.Color(255, 255, 255));
        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(51, 0, 153));
        jLabel57.setText("Password");

        edit_password.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edit_password.setForeground(new java.awt.Color(255, 255, 255));
        edit_password.setBorder(null);
        edit_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_passwordActionPerformed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        disable3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        disable3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        disable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disable3MouseClicked(evt);
            }
        });

        btnreg1.setBackground(new java.awt.Color(255, 255, 255));
        btnreg1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnreg1.setForeground(new java.awt.Color(0, 153, 255));
        btnreg1.setText("SUBMIT");
        btnreg1.setBorder(null);
        btnreg1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnreg1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnreg1MouseClicked(evt);
            }
        });
        btnreg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreg1ActionPerformed(evt);
            }
        });

        show3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        show3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        show3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show3MouseClicked(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("_______________________________");

        edit_username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edit_username.setForeground(new java.awt.Color(255, 255, 255));
        edit_username.setBorder(null);
        edit_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_usernameActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 0, 153));
        jLabel21.setText("Nickname");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("_______________________________");

        update_msg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        update_msg.setForeground(new java.awt.Color(255, 0, 51));
        update_msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        edit_profile_image.setBackground(new java.awt.Color(255, 255, 255));
        edit_profile_image.setFont(new java.awt.Font("Bookman Old Style", 0, 14)); // NOI18N
        edit_profile_image.setForeground(new java.awt.Color(255, 255, 255));
        edit_profile_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edit_profile_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        edit_profile_image.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        edit_profile_image.setOpaque(true);
        edit_profile_image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_profile_imageMouseClicked(evt);
            }
        });

        text_reg_errors2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_reg_errors2.setForeground(new java.awt.Color(255, 0, 51));
        text_reg_errors2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        edit_nickname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edit_nickname.setForeground(new java.awt.Color(255, 255, 255));
        edit_nickname.setBorder(null);
        edit_nickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_nicknameActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("_______________________________");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 0, 153));
        jLabel31.setText("Username");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addGap(230, 230, 230)
                            .addComponent(jLabel43))
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addComponent(edit_profile_image, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(60, 60, 60)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addComponent(jLabel31)
                                    .addGap(222, 222, 222)
                                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(edit_username, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30)
                                .addComponent(jLabel21)
                                .addComponent(jLabel27)
                                .addComponent(edit_nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel57)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel79)
                                        .addComponent(edit_password, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(disable3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(show3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addGap(260, 260, 260)
                            .addComponent(update_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(text_reg_errors2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(200, 200, 200)
                                    .addComponent(btnreg1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel43)
                    .addGap(62, 62, 62)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(edit_profile_image, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel31)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(edit_username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(40, 40, 40)
                                    .addComponent(jLabel30)))
                            .addGap(4, 4, 4)
                            .addComponent(jLabel21)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel27))
                                .addComponent(edit_nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jLabel57)
                            .addGap(8, 8, 8)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel79))
                                .addComponent(edit_password, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(disable3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(show3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGap(7, 7, 7)
                    .addComponent(update_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(text_reg_errors2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(btnreg1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 99, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout edit_profile_panelLayout = new javax.swing.GroupLayout(edit_profile_panel);
        edit_profile_panel.setLayout(edit_profile_panelLayout);
        edit_profile_panelLayout.setHorizontalGroup(
            edit_profile_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(edit_profile_panelLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        edit_profile_panelLayout.setVerticalGroup(
            edit_profile_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        manage_users_panel.setBackground(new java.awt.Color(204, 255, 255));
        manage_users_panel.setPreferredSize(new java.awt.Dimension(930, 620));
        manage_users_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(204, 255, 255));

        img_profile6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        text_admin_username3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        text_admin_username3.setForeground(new java.awt.Color(102, 0, 102));
        text_admin_username3.setText("Welcome Admin");

        btn_chat_groups1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/groups.png"))); // NOI18N
        btn_chat_groups1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_chat_groups1MouseClicked(evt);
            }
        });

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Create Group.png"))); // NOI18N
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users active.png"))); // NOI18N

        logout6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log out.png"))); // NOI18N
        logout6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(text_admin_username3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(img_profile6, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(btn_chat_groups1)
                            .addComponent(jLabel33)
                            .addComponent(logout6))))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(img_profile6, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(text_admin_username3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btn_chat_groups1)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addGap(59, 59, 59)
                .addComponent(logout6)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        manage_users_panel.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 620));

        jPanel16.setBackground(new java.awt.Color(204, 255, 204));
        jPanel16.setPreferredSize(new java.awt.Dimension(420, 620));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 0, 102));
        jLabel16.setText("MANAGE USERS");
        jPanel16.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 260, 40));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 0, 153));
        jLabel14.setText("Delete Users :");
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 150, -1));

        jPanel16.add(userlist1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 190, 30));

        remove_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete user.png"))); // NOI18N
        remove_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                remove_userMouseClicked(evt);
            }
        });
        jPanel16.add(remove_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, -1, -1));
        jPanel16.add(text_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 340, 40));

        manage_users_panel.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 550, 620));

        admin_panel.setPreferredSize(new java.awt.Dimension(930, 620));

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(396, 620));

        img_profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        text_admin_username.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        text_admin_username.setForeground(new java.awt.Color(102, 0, 102));
        text_admin_username.setText("Welcome Admin");

        create_group3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Groups Active.png"))); // NOI18N

        create_group.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Create Group.png"))); // NOI18N
        create_group.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                create_groupMouseClicked(evt);
            }
        });

        link_all_users.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users.png"))); // NOI18N
        link_all_users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                link_all_usersMouseClicked(evt);
            }
        });

        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log out.png"))); // NOI18N
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_admin_username, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(img_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(create_group)
                            .addComponent(create_group3)
                            .addComponent(link_all_users)
                            .addComponent(logout))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(img_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(text_admin_username, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(create_group3)
                .addGap(18, 18, 18)
                .addComponent(create_group)
                .addGap(18, 18, 18)
                .addComponent(link_all_users)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout)
                .addGap(88, 88, 88))
        );

        jPanel6.setBackground(new java.awt.Color(204, 255, 204));
        jPanel6.setPreferredSize(new java.awt.Dimension(570, 620));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 102));
        jLabel12.setText("All Chat Groups");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 240, -1));

        admin_group_list.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane1.setViewportView(admin_group_list);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 560, 530));

        javax.swing.GroupLayout admin_panelLayout = new javax.swing.GroupLayout(admin_panel);
        admin_panel.setLayout(admin_panelLayout);
        admin_panelLayout.setHorizontalGroup(
            admin_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(admin_panelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE))
            .addGroup(admin_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(admin_panelLayout.createSequentialGroup()
                    .addGap(0, 484, Short.MAX_VALUE)
                    .addComponent(create_group2)
                    .addGap(0, 495, Short.MAX_VALUE)))
        );
        admin_panelLayout.setVerticalGroup(
            admin_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            .addGroup(admin_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(admin_panelLayout.createSequentialGroup()
                    .addGap(0, 405, Short.MAX_VALUE)
                    .addComponent(create_group2)
                    .addGap(0, 319, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 962, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(login_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(register_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(create_chat_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(list_groups_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(chat_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(edit_profile_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(manage_users_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 32, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(admin_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(login_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(register_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(create_chat_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(list_groups_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(chat_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(edit_profile_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(manage_users_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 15, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(admin_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 11, Short.MAX_VALUE)))
        );
        jLayeredPane1.setLayer(login_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        login_panel.getAccessibleContext().setAccessibleName("");
        jLayeredPane1.setLayer(register_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(create_chat_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(list_groups_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(chat_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(edit_profile_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(manage_users_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(admin_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textusernameActionPerformed

    private void textpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textpasswordActionPerformed

    private void textregusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textregusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textregusernameActionPerformed

    private void btnregActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnregActionPerformed

    private void linklogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linklogMouseClicked
        // TODO add your handling code here:
        register_panel.setVisible(false);
        login_panel.setVisible(true);
    }//GEN-LAST:event_linklogMouseClicked

    private void linkregMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linkregMouseClicked
        login_panel.setVisible(false);
        register_panel.setVisible(true);
        
    }//GEN-LAST:event_linkregMouseClicked

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnloginActionPerformed

    private void btnloginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnloginMouseClicked
        String user_name = textusername.getText();
        String user_pwd = textpassword.getText();

        ArrayList<String> error = validatelogin(user_name, user_pwd);

        if (error.isEmpty() == false) {
            text_login_errors.setText(error.get(0));
        } else {

            List data = DBManager.getDBM().loginHandler(user_name, user_pwd);
            Iterator i = data.iterator();
            if (i.hasNext()) {
                Users user = (Users) i.next();

                String email = user.getEmail();
                String username = user.getUsername();
                String nickname = user.getNickname();
                String password = user.getPassword();
                byte[] profile_image = user.getProfileImage();
                id = user.getId();

      
                edit_username.setText(username);
                edit_nickname.setText(nickname);
                edit_password.setText(password);

                if(profile_image != null){

                    ImageIcon imageicon = toImageIcon(profile_image);

                    ImageIcon iconresized1 = new ImageIcon(imageicon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
                    img_profile.setIcon(iconresized1);
                    img_profile2.setIcon(iconresized1);
                    img_profile3.setIcon(iconresized1);
                    img_profile4.setIcon(iconresized1);
                    img_profile5.setIcon(iconresized1);
                    img_profile6.setIcon(iconresized1);

                    ImageIcon iconresized2 = new ImageIcon(imageicon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
                    edit_profile_image.setIcon(iconresized2);
                }

                if (user.getUserType().equalsIgnoreCase("admin")) {
                    //admin user

                    text_admin_username.setText("Welcome " + username);
                    text_admin_username2.setText("Welcome " + username);
                    text_admin_username3.setText("Welcome " + username);
                    
                   List users = DBManager.getDBM().get_all_users();
            
                    for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                        
                        Users next = (Users) iterator.next();
                        String del_userid =next.getId().toString(); 
                        String del_username = next.getUsername();
 
                        userlist1.addItem(del_userid +"- "+ del_username);

                    }
                    
                    List groups = DBManager.getDBM().get_chat_groups();

                    for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
                        
                        Groups next = (Groups) iterator.next();
                        String del_groupid =next.getId().toString(); 
                        String del_groupname = next.getName();

                        
                    }
                    

                    

                    login_panel.setVisible(false);
                    load_admin_group(true);
                    admin_panel.setVisible(true);

                } else{
                    //Normal user

                    text_user_username.setText("Welcome " + username);
                    text_user_username1.setText("Welcome " + username);
                    text_user_username2.setText("Welcome " + username);

                    me = new ChatClient(user.getId(), user.getUsername(), user.getNickname(), user.getEmail());

                    load_client_groups();
                    this.start_client();
                    login_panel.setVisible(false);
                    list_groups_panel.setVisible(true);

                }

            } else {
                System.out.println("Username or Password Incorrect");
                text_login_errors.setText("Username or Password Incorrect");
            }

        }
    }//GEN-LAST:event_btnloginMouseClicked

    private void showMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMouseClicked
//        textpassword.setEchoChar((char)8226);
        disable.setVisible(true);
        disable.setEnabled(true);
        show.setVisible(false);
        show.setEnabled(false);
    }//GEN-LAST:event_showMouseClicked

    private void disableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disableMouseClicked
       // textpassword.setEchoChar((char)0);
        disable.setVisible(false);
        disable.setEnabled(false);
        show.setVisible(true);
        show.setEnabled(true);
    }//GEN-LAST:event_disableMouseClicked

    private void show2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show2MouseClicked
      //  textregpassword.setEchoChar((char)8226);
        disable2.setVisible(true);
        disable2.setEnabled(true);
        show2.setVisible(false);
        show2.setEnabled(false);
    }//GEN-LAST:event_show2MouseClicked

    private void disable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disable2MouseClicked
      //  textregpassword.setEchoChar((char)0);
        disable2.setVisible(false);
        disable2.setEnabled(false);
        show2.setVisible(true);
        show2.setEnabled(true);
    }//GEN-LAST:event_disable2MouseClicked

    private void btnregMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnregMouseClicked
        String email = textregemail.getText();
        String username = textregusername.getText();
        String nickname = textregnickname.getText();
        String password = textregpassword.getText();

        //error array
        ArrayList<String> error = validateform(email, username, password);

        if (error.isEmpty() == false) {
            text_reg_errors.setText(error.get(0));
        } else {
            text_reg_errors.setText(null);
            //intsert details
            byte[] img = null;
            ImageIcon avatar = (ImageIcon) signup_profile_pic.getIcon();
            if (avatar != null) {
                try {
                    //                img = this.encodeToString(this.ImageIconToBufferedImage(avatar),"jpg");
                    BufferedImage bImage = ImageIconToBufferedImage(avatar);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "jpg", bos);
                    img = bos.toByteArray();

                } catch (IOException ex) {
                    Logger.getLogger(AppLayout.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            if (DBManager.getDBM().insert(img, email, username,nickname, password)) {
                text_reg_errors.setText("You Registered Successfully");
            }

        }
    }//GEN-LAST:event_btnregMouseClicked

    private void textgroupnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textgroupnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textgroupnameActionPerformed

    private void textgroupdescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textgroupdescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textgroupdescriptionActionPerformed

    private void btn_create_groupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_create_groupMouseClicked
        // TODO add your handling code here:

        String name = textgroupname.getText();
        String desc = textgroupdescription.getText();

        if (DBManager.getDBM().create_chat_group(name, desc)) {
            group_create_text.setText("Group created sucessfully");
        } else {
            group_create_text.setText("Group can not create!");
        }
    }//GEN-LAST:event_btn_create_groupMouseClicked

    private void btn_create_groupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_groupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_create_groupActionPerformed

    private void msg_typerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_typerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msg_typerActionPerformed

    private void msg_typerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msg_typerKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.sender();
        }
    }//GEN-LAST:event_msg_typerKeyPressed

    private void send_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_send_btnMouseClicked
        // TODO add your handling code here:
        this.sender();

        msgScrollPane.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });

    }//GEN-LAST:event_send_btnMouseClicked

    private void send_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_send_btnActionPerformed

    private void msg_typer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_typer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msg_typer1ActionPerformed

    private void msg_typer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msg_typer1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_msg_typer1KeyPressed

    private void send_btn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_send_btn1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_send_btn1MouseClicked

    private void send_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_btn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_send_btn1ActionPerformed

    private void edit_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_passwordActionPerformed

    private void disable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disable3MouseClicked
        // TODO add your handling code here:
        edit_password.setEchoChar((char)0);
        disable3.setVisible(false);
        disable3.setEnabled(false);
        show3.setVisible(true);
        show3.setEnabled(true);
    }//GEN-LAST:event_disable3MouseClicked

    private void btnreg1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreg1MouseClicked
        // TODO add your handling code here:

        String username = edit_username.getText().trim();
        String nickname = edit_nickname.getText().trim();
        String password = edit_password.getText().trim();

        byte[] img = null;
        ImageIcon avatar = (ImageIcon) edit_profile_image.getIcon();
        if (avatar != null) {
            try {

                BufferedImage bImage = ImageIconToBufferedImage(avatar);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                img = bos.toByteArray();

            } catch (IOException ex) {
                Logger.getLogger(AppLayout.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        DBManager.getDBM().update(img, username, nickname,password, id);
        update_msg.setText("Sucessfully Updated..");
        img_profile2.setIcon(avatar);
        img_profile2.setIcon(avatar);

    }//GEN-LAST:event_btnreg1MouseClicked

    private void btnreg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreg1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnreg1ActionPerformed

    private void show3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show3MouseClicked
        // TODO add your handling code here:

        edit_password.setEchoChar((char)8226);
        disable3.setVisible(true);
        disable3.setEnabled(true);
        show3.setVisible(false);
        show3.setEnabled(false);
    }//GEN-LAST:event_show3MouseClicked

    private void edit_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_usernameActionPerformed

    private void edit_profile_imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_profile_imageMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser(); //open image file file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png"); //set image type filter
        chooser.setFileFilter(filter); //filter
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) { //if image selected
            File file = chooser.getSelectedFile(); //get selected file
            String strfilepath = file.getAbsolutePath(); //get abs path
            //            System.out.println(strfilepath);
            try {
                ImageIcon icon = new ImageIcon(strfilepath); //string image path open as a image icon
                ImageIcon iconresized = new ImageIcon(icon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)); //resize image icon fit for profile icon label
                edit_profile_image.setText(null); // remove label text
                edit_profile_image.setIcon(iconresized); //set seleted image to profile icon label

                //               String img = this.encodeToString(this.ImageIconToBufferedImage(iconresized),"jpg");
                //               BufferedImage bimg = this.decodeToImage(img);
                //
                //               signup_profile_pic.setIcon(new ImageIcon(bimg));
            } catch (Exception e) {
                System.out.println("Exception occurred : " + e.getMessage());
            }
        }
    }//GEN-LAST:event_edit_profile_imageMouseClicked

    private void edit_nicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_nicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_nicknameActionPerformed

    private void signup_profile_picMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signup_profile_picMouseClicked
        JFileChooser chooser = new JFileChooser(); //open image file file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png"); //set image type filter
        chooser.setFileFilter(filter); //filter
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) { //if image selected
            File file = chooser.getSelectedFile(); //get selected file
            String strfilepath = file.getAbsolutePath(); //get abs path
//            System.out.println(strfilepath);
            try {
                ImageIcon icon = new ImageIcon(strfilepath); //string image path open as a image icon
                ImageIcon iconresized = new ImageIcon(icon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)); //resize image icon fit for profile icon label
                signup_profile_pic.setText(null); // remove label text
                signup_profile_pic.setIcon(iconresized); //set seleted image to profile icon label 

//               String img = this.encodeToString(this.ImageIconToBufferedImage(iconresized),"jpg"); 
//               BufferedImage bimg = this.decodeToImage(img);
//               
//               signup_profile_pic.setIcon(new ImageIcon(bimg));
            } catch (Exception e) {
                System.out.println("Exception occurred : " + e.getMessage());
            }
        }
    }//GEN-LAST:event_signup_profile_picMouseClicked

    private void btn_chat_groupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_chat_groupsMouseClicked
       app_ui_reset();
       admin_panel.setVisible(true);
    }//GEN-LAST:event_btn_chat_groupsMouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        app_ui_reset();
        manage_users_panel.setVisible(true);
    }//GEN-LAST:event_jLabel40MouseClicked

    private void logout1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout1MouseClicked
        app_ui_reset();
        login_panel.setVisible(true);
    }//GEN-LAST:event_logout1MouseClicked

    private void create_groupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_create_groupMouseClicked
        app_ui_reset();
        create_chat_panel.setVisible(true);
    }//GEN-LAST:event_create_groupMouseClicked

    private void link_all_usersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_link_all_usersMouseClicked
        app_ui_reset();
        manage_users_panel.setVisible(true);
    }//GEN-LAST:event_link_all_usersMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        app_ui_reset();
        login_panel.setVisible(true);
    }//GEN-LAST:event_logoutMouseClicked

    private void edit_profile_link_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_profile_link_1MouseClicked
       app_ui_reset();
       edit_profile_panel.setVisible(true);
    }//GEN-LAST:event_edit_profile_link_1MouseClicked

    private void logout2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout2MouseClicked
        app_ui_reset();
        login_panel.setVisible(true);
    }//GEN-LAST:event_logout2MouseClicked

    private void logout3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout3MouseClicked
        app_ui_reset();
        login_panel.setVisible(true);
    }//GEN-LAST:event_logout3MouseClicked

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
        app_ui_reset();
        list_groups_panel.setVisible(true);
    }//GEN-LAST:event_jLabel58MouseClicked

    private void logout4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout4MouseClicked
        app_ui_reset();
        login_panel.setVisible(true);
    }//GEN-LAST:event_logout4MouseClicked

    private void btn_chat_groups1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_chat_groups1MouseClicked
        app_ui_reset();
        admin_panel.setVisible(true);
    }//GEN-LAST:event_btn_chat_groups1MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        app_ui_reset();
        create_chat_panel.setVisible(true);
    }//GEN-LAST:event_jLabel32MouseClicked

    private void logout6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout6MouseClicked
        app_ui_reset();
        login_panel.setVisible(true);
    }//GEN-LAST:event_logout6MouseClicked

    private void remove_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remove_userMouseClicked
       String del_user = (String) userlist1.getSelectedItem();
       String del_user_id = del_user.split("-")[0];
       
       int user_id = Integer.parseInt(del_user_id);
       DBManager.getDBM().delete_user(user_id);
       text_delete.setText("User Successfully Deleted ");
       userlist1.removeItem(userlist1.getSelectedItem());
    }//GEN-LAST:event_remove_userMouseClicked

    /**
     * @param args the command line arguments
     */
    
     int y2 = 210;

    public void recive_msg_handler(Message msg) {

        chat_background.repaint();
        chat_background.revalidate();

        JLabel msg_content = new javax.swing.JLabel();
        msg_content.setForeground(new java.awt.Color(255, 255, 255));
        msg_content.setText("<html>" + msg.getMessage() + "</html>");

        JLabel msg_time = new javax.swing.JLabel();
        msg_time.setForeground(new java.awt.Color(255, 255, 255));
        msg_time.setText(msg.getDate_time());

        JLabel msg_name = new javax.swing.JLabel();
        msg_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        msg_name.setForeground(new java.awt.Color(255, 255, 255));
        msg_name.setText(msg.getName());

        JLabel msg_dp = new javax.swing.JLabel();
        msg_dp.setBackground(new java.awt.Color(17, 89, 153));

        List data = DBManager.getDBM().get_avatart(msg.getUserid());
        Iterator i = data.iterator();
        if (i.hasNext()) {
            Users user = (Users) i.next();
            ImageIcon iconresized = new ImageIcon(toImageIcon(user.getProfileImage()).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
            msg_dp.setIcon(iconresized);
        }

        JPanel msg_layer = new javax.swing.JPanel();

        msg_layer.setBackground(
                new java.awt.Color(54, 63, 77));
        msg_layer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        msg_layer.setLayout(
                new org.netbeans.lib.awtextra.AbsoluteLayout());

        msg_layer.add(msg_content,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 260, 40));
        msg_layer.add(msg_time,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 210, -1));
        msg_layer.add(msg_name,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 210, -1));
        msg_layer.add(msg_dp,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, 35, 35));

//        chat_background.add(msg_layer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 280, 110));
        chat_background.add(msg_layer,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(20, y2, 280, 110));

        chat_background.repaint();
        chat_background.revalidate();

        
        
        chat_background.repaint();
        chat_background.revalidate();
        

        y2 += 120;

    }
    
    
    public void send_msg_handler(Message msg) {

        chat_background.repaint();
        chat_background.revalidate();

        JLabel msg_content = new javax.swing.JLabel();
        msg_content.setForeground(new java.awt.Color(255, 255, 255));
        msg_content.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        msg_content.setText("<html>" + msg.getMessage() + "</html>");

        JLabel msg_time = new javax.swing.JLabel();
        msg_time.setForeground(new java.awt.Color(255, 255, 255));
        msg_time.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        msg_time.setText(msg.getDate_time());

        JLabel msg_name = new javax.swing.JLabel();
        msg_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        msg_name.setForeground(new java.awt.Color(255, 255, 255));
        msg_name.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        msg_name.setText(msg.getName());

        JLabel msg_dp = new javax.swing.JLabel();
        msg_dp.setBackground(new java.awt.Color(54, 63, 77));

        List data = DBManager.getDBM().get_avatart(msg.getUserid());
        Iterator i = data.iterator();
        if (i.hasNext()) {
            Users user = (Users) i.next();
            ImageIcon iconresized = new ImageIcon(toImageIcon(user.getProfileImage()).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
            msg_dp.setIcon(iconresized);
        }

        JPanel msg_layer = new javax.swing.JPanel();
        msg_layer.setBackground(new java.awt.Color(42, 50, 61));
        msg_layer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        msg_layer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        msg_layer.add(msg_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 260, 40));
        msg_layer.add(msg_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 210, -1));
        msg_layer.add(msg_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, -1));
        msg_layer.add(msg_dp, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 35, 35));

        //chat_background.add(msg_layer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 280, 110));
        chat_background.add(msg_layer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, y2, 280, 110));

        JScrollBar sb = msgScrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());

        chat_background.repaint();
        chat_background.revalidate();

        y2 += 120;
    }
    
    
      Thread retrivemsg = new Thread() {
        public void run() {

            int preiv = 0;

            while (true) {
                try {

                    Message nmsg = chat.broadcast();
                    if (nmsg != null) {
                        if (preiv != nmsg.getMsgid()) {
                            //System.out.println(nmsg.getDate_time() + "\t" + nmsg.getName() + " : " + nmsg.getMessage() + "\n");

                            System.out.println(nmsg.getMsgid() + "-" + me.getId());
                            if (nmsg.getUserid() == me.getId()) {
                                send_msg_handler(nmsg);
                            } else {
                                recive_msg_handler(nmsg);
                            }

                            preiv = nmsg.getMsgid();
                        }
                    }

//                    if(newmsg!=preiv){
//                        System.out.println(chat.broadcast().getMessage());
//                        preiv = newmsg;
//                    }
                    Thread.sleep(100);
                } catch (RemoteException | NullPointerException ex) {
                    System.out.println(ex);
                } catch (InterruptedException ex) {

                }
            }

        }
    }; 
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppLayout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel admin_group_list;
    private javax.swing.JPanel admin_panel;
    private javax.swing.JLabel btn_chat_groups;
    private javax.swing.JLabel btn_chat_groups1;
    private javax.swing.JButton btn_create_group;
    private javax.swing.JButton btnlogin;
    private javax.swing.JButton btnreg;
    private javax.swing.JButton btnreg1;
    private javax.swing.JPanel chat_background;
    private javax.swing.JPanel chat_panel;
    private javax.swing.JPanel client_chat_groups_panel;
    private javax.swing.JPanel create_chat_panel;
    private javax.swing.JLabel create_group;
    private javax.swing.JLabel create_group2;
    private javax.swing.JLabel create_group3;
    private javax.swing.JLabel disable;
    private javax.swing.JLabel disable2;
    private javax.swing.JLabel disable3;
    private javax.swing.JTextField edit_nickname;
    private javax.swing.JPasswordField edit_password;
    private javax.swing.JLabel edit_profile_image;
    private javax.swing.JLabel edit_profile_link_1;
    private javax.swing.JPanel edit_profile_panel;
    private javax.swing.JTextField edit_username;
    private javax.swing.JLabel group_create_text;
    private javax.swing.JLabel img_profile;
    private javax.swing.JLabel img_profile2;
    private javax.swing.JLabel img_profile3;
    private javax.swing.JLabel img_profile4;
    private javax.swing.JLabel img_profile5;
    private javax.swing.JLabel img_profile6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel link_all_users;
    private javax.swing.JLabel linklog;
    private javax.swing.JLabel linkreg;
    private javax.swing.JPanel list_groups_panel;
    private javax.swing.JPanel login_panel;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel logout1;
    private javax.swing.JLabel logout2;
    private javax.swing.JLabel logout3;
    private javax.swing.JLabel logout4;
    private javax.swing.JLabel logout6;
    private javax.swing.JPanel manage_users_panel;
    private javax.swing.JScrollPane msgScrollPane;
    private javax.swing.JTextField msg_typer;
    private javax.swing.JTextField msg_typer1;
    private javax.swing.JPanel register_panel;
    private javax.swing.JLabel remove_user;
    private javax.swing.JButton send_btn;
    private javax.swing.JButton send_btn1;
    private javax.swing.JLabel show;
    private javax.swing.JLabel show2;
    private javax.swing.JLabel show3;
    private javax.swing.JLabel signup_profile_pic;
    private javax.swing.JLabel text_admin_username;
    private javax.swing.JLabel text_admin_username2;
    private javax.swing.JLabel text_admin_username3;
    private javax.swing.JLabel text_delete;
    private javax.swing.JLabel text_login_errors;
    private javax.swing.JLabel text_reg_errors;
    private javax.swing.JLabel text_reg_errors2;
    private javax.swing.JLabel text_user_username;
    private javax.swing.JLabel text_user_username1;
    private javax.swing.JLabel text_user_username2;
    private javax.swing.JTextField textgroupdescription;
    private javax.swing.JTextField textgroupname;
    private javax.swing.JTextField textpassword;
    private javax.swing.JTextField textregemail;
    private javax.swing.JTextField textregnickname;
    private javax.swing.JTextField textregpassword;
    private javax.swing.JTextField textregusername;
    private javax.swing.JTextField textusername;
    private javax.swing.JLabel update_msg;
    private javax.swing.JLabel user;
    private javax.swing.JComboBox userlist1;
    // End of variables declaration//GEN-END:variables

}  
