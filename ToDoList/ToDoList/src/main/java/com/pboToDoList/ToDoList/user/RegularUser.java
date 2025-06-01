package com.pboToDoList.ToDoList.user;

import com.pboToDoList.ToDoList.task.Task;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RegularUser extends user{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "ruser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public RegularUser() {
    }

    public RegularUser(String username, String password, String email){
        super(username, password);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

