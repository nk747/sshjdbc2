package com.journaldev.java.ssh;

import java.sql.*;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

class MySqlConnectionOverSsh {
    static int lport;
    static String rhost;
    static int rport;
    public static void go(){
        String user = "compaq";
        String password = "compaq";
        String host = "192.168.0.103";
        int port=22;
        try
        {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            lport = 4321;
            rhost = "localhost";
            rport = 3306;
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
        }
        catch(Exception e){System.err.print(e);}
    }
    public static void main(String[] args) {
        try{
            go();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("An example for updating a Row from Mysql Database!");
        Connection con = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://" + rhost +":" + lport + "/";
        String db = "studentdb";
        String dbUser = "compaqdbuser";
        String dbPasswd = "Cross@123";
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url+db, dbUser, dbPasswd);
            try{
                Statement st = con.createStatement();
                String sql = "insert into student values(36,'mannu')";

                int update = st.executeUpdate(sql);
                if(update >= 1){
                    System.out.println("Row is updated.");
                }
                else{
                    System.out.println("Row is not updated.");
                }
            }
            catch (SQLException s){
                System.out.println("SQL statement is not executed!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

