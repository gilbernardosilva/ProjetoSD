package edu.ufp.inf.sd.rmi.project.variables;

public class User {

    private String username;
    private String password;
    private Token token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.token = new Token(username);
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Token getToken(){
        return this.token;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", token=" + this.token +
                '}';
    }
}
