# bot-authenticator

[![Build Status](https://travis-ci.org/akadir/bot-authenticator.svg?branch=master)](https://travis-ci.org/akadir/bot-authenticator)
[![gradle-version](https://img.shields.io/badge/gradle-5.5.1-brightgreen)](https://img.shields.io/badge/gradle-5.5.1-brightgreen)

Project to authenticate twitter accounts using Twitter API tokens and twitter4j library.

### Usage

to use in your gradle project add this into your settings.gradle and build.gradle files respectively:

settings.gradle:

```groovy
sourceControl {
  gitRepository("https://github.com/akadir/bot-authenticator.git") {
    producesModule("com.kadir.twitterbots.commons:bot-authenticator")
  }
}
```

build.gradle:

```groovy
dependencies {
  implementation 'com.kadir.twitterbots.commons:bot-authenticator:1.0'
}
```

And create property file which contains twitter api tokens. Then call `BotAuthenticator.authenticate()` method to authenticate your account.
This method will return `twitter4j.Twitter` object after successful authentication. 

ps: if you provide only consumer-key and consumer-secret then you will be prompted a link in console to authenticate your account. 
For further information please look at [official twitter documentation](https://developer.twitter.com/en/docs/basics/authentication/overview/pin-based-oauth.html)

ex:

```
Twitter twitter = BotAuthenticator.authenticate(PROPERTIES_FILE_NAME, API_KEYS_PREFIX);
```

### Projects Used bot-authenticator

- [alligator](https://github.com/akadir/alligator)
- [crocodile](https://github.com/akadir/crocodile)
- [eagle](https://github.com/akadir/eagle)
- [elephant](https://github.com/akadir/elephant)
- [giraffe](https://github.com/akadir/giraffe)
- [squirrel](https://github.com/akadir/squirrel)
- [worm](https://github.com/akadir/worm)
