package com.example.user.databasetest;

public class StudentInfo {
    String name, level, credit;

    public StudentInfo(String name, String level, String credit) {
        this.name = name;
        this.level = level;
        this.credit = credit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
