package com.kadir.twitterbots.exceptions;

/**
 * @author akarakoc
 * Date :   11.12.2018
 * Time :   15:40
 */
public class IllegalApiTokenException extends IllegalArgumentException {

    public IllegalApiTokenException(String message) {
        super(message);
    }

}
