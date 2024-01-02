package pl.edu.wszib.model;

public class User {
    private String name;
    private String password;
    private String group;

    public User(String name, String password, String group) {
        this.name = name;
        this.password = password;
        this.group = group;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.group = "user";
    }

    public User() { }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return this.name;
    }

    public String getGroup() {
        return this.name;
    }

    public boolean isInGroup(String group) {
        return this.group.equals(group);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
