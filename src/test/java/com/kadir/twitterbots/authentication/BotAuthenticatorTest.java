package com.kadir.twitterbots.authentication;

import com.kadir.twitterbots.exceptions.IllegalApiTokenException;
import org.junit.Test;

/**
 * @author akadir
 * Date: 21/12/2018
 * Time: 19:58
 */
public class BotAuthenticatorTest {

    @Test(expected = IllegalApiTokenException.class)
    public void authenticateTest() {
        BotAuthenticator.authenticate(null, null, null, null);
    }
}
