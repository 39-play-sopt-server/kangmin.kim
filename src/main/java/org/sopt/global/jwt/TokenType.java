package org.sopt.global.jwt;

public enum TokenType {
    ACCESS, REFRESH;

    public static final String CLAIM_NAME = "type";
}