package com.hazbinhotel.CustomSwing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.hazbinhotel.App;
import com.hazbinhotel.entity.Room;
import com.hazbinhotel.entity.RoomType;
import com.hazbinhotel.entity.Service;

public class RoomLabel extends JLabel{
    App app;
    int id;
    public RoomLabel(App app, int ID){
        this.app = app;
        this.id = ID;

        setOpaque(true);
        setPreferredSize(new Dimension(200 , 120));
        setBorder(new LineBorder(new Color(201, 38, 84) , 2));
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        setBackground(Color.black);
        setForeground(new Color(201, 38, 84));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.app.roomChanged.connectSignal((arg) -> {
            changed();
        });
        changed();
    }

    public void setID(int id){
        this.id = id;
    }

    void changed(){
        Room room = app.roomDAO.get(id);
        Service service = app.serviceDAO.get(room.getService());
        RoomType roomType = app.roomTypeDAO.get(room.getType());
        
        setText(String.format("<html>Room: %s <br/>Service: %s <br/>Room type: %s</html>", room.getName(), service.getName(), roomType.getName()));
        boolean inUse = app.roomDAO.isInUsed(room.getId());
        setBorder(new LineBorder(inUse?Color.red:Color.green , 2));
    }
}
