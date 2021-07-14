package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
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
}
