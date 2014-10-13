/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao.calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.model.User;
import server.model.calendar.CalendarAccess;
import server.util.AccessBD;
import server.util.SingleConnection;

/**
 *
 * @author Renan
 */
public class CalendarAcessDAO {
    
    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public CalendarAcessDAO() {
        db = SingleConnection.getAccessDB();
    }

    /**
     * contrutor recebe por parâmetro um acesso BD;
     * 
     * @param db
     */
    public CalendarAcessDAO(AccessBD db) {
        this.db = db;
    }

    /**
     * retorna a conexão que está sendo usada pela classe
     *
     * @return 
     */
    public Connection getConnection() {
        return db.getConnection();
    }
    
    /**
     * Insere informações de acesso ao Google Calendar
     *
     * @param o
     */
    public void insert(CalendarAccess o) {
        String sql =
                " INSERT INTO calendar_access "
                + "(user_id, auth_code, refresh_token, calendar_mail) "
                + "VALUES (?,?,?); ";
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            
            pstmt.setInt(1, o.getUserId());
            pstmt.setString(2, o.getAuthCode());
            pstmt.setString(3, o.getRefreshToken());
            pstmt.setString(4, o.getCalendarMail());
            
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
