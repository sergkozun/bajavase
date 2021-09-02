package com.urise.webapp.model;

import java.lang.annotation.Inherited;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype"){
        @Override
        public String toHtml0(String value){
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта"){
        @Override
        public String toHtml0(String value){
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    HOME_PAGE("Домашнаяя страница");

    private final String title;

    public String getTitle() {
        return title;
    }

    ContactType(String title){
        this.title = title;
    }

    public String toHtml0(String value){
        return title + ": " + value;
    }

    public String toHtml(String value){
        return (value == null) ? "" : toHtml0(value);
    }
}
