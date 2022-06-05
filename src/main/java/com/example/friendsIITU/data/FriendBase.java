package com.example.friendsIITU.data;

import com.example.friendsIITU.model.Friend;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FriendBase {
    private static final String URL = "jdbc:mysql://localhost:3306/Java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    @PostConstruct
    public void Init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @PreDestroy
    public void Destroy() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM friends");
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setId(resultSet.getInt("id"));
                friend.setUsername(resultSet.getString("username"));
                friend.setPassword(resultSet.getString("password"));
                friend.setName(resultSet.getString("name"));
                friend.setLastname(resultSet.getString("lastname"));
                friend.setEmail(resultSet.getString("email"));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friends;
    }

    public Friend find(int id) {
        Friend friend = new Friend();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            friend.setId(resultSet.getInt("id"));
            friend.setUsername(resultSet.getString("username"));
            friend.setPassword(resultSet.getString("password"));
            friend.setName(resultSet.getString("name"));
            friend.setLastname(resultSet.getString("lastname"));
            friend.setEmail(resultSet.getString("email"));

            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friend;
    }

    public void save(Friend newFriend) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO friends(username,password,name,lastname,email) VALUES(?,?,?,?,?)");
            statement.setString(1, newFriend.getUsername());
            statement.setString(2, newFriend.getPassword());
            statement.setString(3, newFriend.getName());
            statement.setString(4, newFriend.getLastname());
            statement.setString(5, newFriend.getEmail());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM friends WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(int id, Friend updatedFriend) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE friends SET username=?,password=?,name=?,lastname=?,email=? WHERE id=?");
            statement.setString(1, updatedFriend.getUsername());
            statement.setString(2, updatedFriend.getPassword());
            statement.setString(3, updatedFriend.getName());
            statement.setString(4, updatedFriend.getLastname());
            statement.setString(5, updatedFriend.getEmail());
            statement.setInt(6, id);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
