package main.entity;

public enum StatusType {
    RELIABLE, // no delays
    FLAKE, // some delays
    BANNED // no access before payment
}
