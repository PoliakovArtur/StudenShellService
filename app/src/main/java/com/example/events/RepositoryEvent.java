package com.example.events;

public record RepositoryEvent(RepositoryMethodEnum method, String... data) {

    public enum RepositoryMethodEnum {
        FIND_ALL, FIND_BY_ID, DELETE_BY_ID, SAVE, DELETE_ALL
    }
}
