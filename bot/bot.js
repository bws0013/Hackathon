/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
          ______     ______     ______   __  __     __     ______
          /\  == \   /\  __ \   /\__  _\ /\ \/ /    /\ \   /\__  _\
          \ \  __<   \ \ \/\ \  \/_/\ \/ \ \  _"-.  \ \ \  \/_/\ \/
          \ \_____\  \ \_____\    \ \_\  \ \_\ \_\  \ \_\    \ \_\
           \/_____/   \/_____/     \/_/   \/_/\/_/   \/_/     \/_/


This is a sample Slack bot built with Botkit.

This bot demonstrates many of the core features of Botkit:

* Connect to Slack using the real time API
* Receive messages based on "spoken" patterns
* Reply to messages
* Use the conversation system to ask questions
* Use the built in storage system to store and retrieve information
  for a user.

# RUN THE BOT:

  Get a Bot token from Slack:

    -> http://my.slack.com/services/new/bot

  Run your bot from the command line:

    token=<MY TOKEN> node bot.js

# USE THE BOT:

  Find your bot inside Slack to send it a direct message.

  Say: "Hello"

  The bot will reply "Hello!"

  Say: "who are you?"

  The bot will tell you its name, where it running, and for how long.

  Say: "Call me <nickname>"

  Tell the bot your nickname. Now you are friends.

  Say: "who am I?"

  The bot will tell you your nickname, if it knows one for you.

  Say: "shutdown"

  The bot will ask if you are sure, and then shut itself down.

  Make sure to invite your bot into other channels using /invite @<my bot>!

# EXTEND THE BOT:

  Botkit is has many features for building cool and useful bots!

  Read all about it here:

    -> http://howdy.ai/botkit

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


if (!process.env.token) {
    console.log('Error: Specify token in environment');
    process.exit(1);
}

var Botkit = require('./lib/Botkit.js');
var os = require('os');

var controller = Botkit.slackbot({
    debug: true,
});

var bot = controller.spawn({
    token: process.env.token
}).startRTM();

controller.hears(['Tell me about (.*).'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Tell me about (.*)\./i);
    var name = matches[1];
    bot.reply(message, 'I will find what I can about ' + name + "...");
});

controller.hears(['Who is in (.*)?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Who is in (.*)\?/i);
    var major = matches[1];
    bot.reply(message, 'Finding people in ' + major + "...");
});

controller.hears(['Who is from (.*)?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Who is from (.*)\?/i);
    var place = matches[1];
    bot.reply(message, 'Finding people from ' + place + "...");
});

controller.hears(['help'], 'direct_message,direct_mention,mention',function(bot, message) {
  bot.reply(message, 'I can do lots of things! Try one of the following:');
  bot.reply(message, 'Who is in [MAJOR]?');
  bot.reply(message, 'Who is from [COUNTY]?');
  bot.reply(message, 'Whose email is [EMAIL]?');
  bot.reply(message, 'Tell me about [PERSON].');
});

controller.hears(['Whose email is (.*)?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Whose email is (.*)\?/i);
    var mailTo = matches[1];
    var start = mailTo.indexOf(":") + 1;
    var end = mailTo.indexOf("|");
    var email = mailTo.substring(start, end);
    bot.reply(message, 'Searching for ' + email + "...");
});

controller.hears(['shutdown'],'direct_message,direct_mention,mention',function(bot, message) {

    bot.startConversation(message,function(err, convo) {
        convo.ask('Are you sure you want me to shutdown?',[
            {
                pattern: bot.utterances.yes,
                callback: function(response, convo) {
                    convo.say('Bye!');
                    convo.next();
                    setTimeout(function() {
                        process.exit();
                    },3000);
                }
            },
        {
            pattern: bot.utterances.no,
            default: true,
            callback: function(response, convo) {
                convo.say('*Phew!*');
                convo.next();
            }
        }
        ]);
    });
});


controller.hears(['uptime'],'direct_message,direct_mention,mention',function(bot, message) {

    var hostname = os.hostname();
    var uptime = formatUptime(process.uptime());

    bot.reply(message,':robot_face: I am a bot named <@' + bot.identity.name + '>. I have been running for ' + uptime + ' on ' + hostname + '.');

});

function formatUptime(uptime) {
    var unit = 'second';
    if (uptime > 60) {
        uptime = uptime / 60;
        unit = 'minute';
    }
    if (uptime > 60) {
        uptime = uptime / 60;
        unit = 'hour';
    }
    if (uptime != 1) {
        unit = unit + 's';
    }

    uptime = uptime + ' ' + unit;
    return uptime;
}
