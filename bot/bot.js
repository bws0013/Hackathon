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

controller.hears(['What\'s trending?'],'direct_message,direct_mention,mention',function(bot, message) {
    bot.reply(message, 'Here is what is trending ...');
    var exec = require('child_process').exec;
    var child = exec('java -jar Scraper.jar -trends', function(error, stdout, stderr) {
      //bot.reply(message, 'Done!');
      bot.reply(message, stdout);
      if (error !== null) {
        console.log('exec error: ' + error);
        bot.reply(message, 'ERROR: ' + error);
      }
    });
});


controller.hears(['Find (.*) on Twitter.'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Find (.*) on Twitter\./i);
    if (matches === null) {
      bot.reply(message, 'Sorry, I don\'t understand you.');
    } else {
      var name = matches[1];
      bot.reply(message, 'Looking up ' + name + " on Twitter...");
      var exec = require('child_process').exec;
      var child = exec('java -jar Scraper.jar -find ' + '"' + name +'"', function(error, stdout, stderr) {
        //bot.reply(message, 'Done!');
        bot.reply(message, stdout);
        if (error !== null) {
          console.log('exec error: ' + error);
          bot.reply(message, 'ERROR: ' + error);
        }
      });
    }
});

controller.hears(['When is (.*)\'s birthday?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/When is (.*)\'s birthday\?/i);
    if (matches === null) {
      bot.reply(message, 'Sorry, I don\'t understand you.');
    } else {
      var name = matches[1];
      bot.reply(message, 'I will try to find ' + name + "\'s birthday...");
      var exec = require('child_process').exec;
      var child = exec('java -jar Scraper.jar -bday ' + '"' + name + '"', function(error, stdout, stderr) {
        //bot.reply(message, 'Done!');
        bot.reply(message, stdout);
        if (error !== null) {
          console.log('exec error: ' + error);
          bot.reply(message, 'ERROR: ' + error);
        }
      });
    }
});


controller.hears(['Tell me about (.*).'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Tell me about (.*)\./i);
    if (matches === null) {
      bot.reply(message, 'Sorry, I don\'t understand you.');
    } else {
      var name = matches[1];
      bot.reply(message, 'I will find what I can about ' + name + "...");
      var exec = require('child_process').exec;
      var child = exec('java -jar Scraper.jar -info ' + '"' + name + '"', function(error, stdout, stderr) {
        //bot.reply(message, 'Done!');
        bot.reply(message, stdout);
        if (error !== null) {
          console.log('exec error: ' + error);
          bot.reply(message, 'ERROR: ' + error);
        }
      });
    }
});

controller.hears(['Who is in (.*) from (.*)?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Who is in (.*) from (.*)\?/i);
    if (matches === null) {
      bot.reply(message, 'Sorry, I don\'t understand you.');
    } else {
      var major = matches[1];
      var place = matches[2];
      bot.reply(message, 'Finding people in ' + major + ' from ' + place + "...");
      var exec = require('child_process').exec;
      var child = exec('java -jar Scraper.jar -mjpl ' + '"' + major + '" ' + '"' + place +'"', function(error, stdout, stderr) {
        //bot.reply(message, 'Done!');
        bot.reply(message, stdout);
        if (error !== null) {
          console.log('exec error: ' + error);
          bot.reply(message, 'ERROR: ' + error);
        }
      });
    }
});


controller.hears(['help'], 'direct_message,direct_mention,mention',function(bot, message) {
  bot.reply(message, 'I can do lots of things! Try one of the following:');
  bot.reply(message, 'Who is in [MAJOR] from [COUNTY/STATE/ZIP/CITY]?');
  bot.reply(message, 'Tell me about [PERSON].');
  bot.reply(message, 'What\'s trending?');
  bot.reply(message, 'Find [PERSON] on Twitter.');
  bot.reply(message, 'When is [PERSON]\'s birthday?');
  bot.reply(message, 'What\'s up with [HASHTAG]?');
  bot.reply(message, 'Where\'s [TWITTERHANDLE]?');
});

controller.hears(['Where\'s (.*)?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/Where\'s (.*)\?/i);
    if (matches === null) {
      bot.reply(message, 'Sorry, I don\'t understand you.');
    } else {
      var tag = matches[1];
      if (tag.charAt(0) === '@') {
        bot.reply(message, 'Searching Twitter for *' + tag + '*...');
        var handle = tag.substring(1);
        var exec = require('child_process').exec;
        var child = exec('java -jar Scraper.jar -loc ' + '"' + handle + '"', function(error, stdout, stderr) {
          //bot.reply(message, 'Done!');
          bot.reply(message, stdout);
          if (error !== null) {
            console.log('exec error: ' + error);
            bot.reply(message, 'ERROR: ' + error);
          }
        });
      } else {
        bot.reply(message, 'Sorry, "' + tag + '" is not a valid Twitter handle.');
      }
    }

});

controller.hears(['What\'s up with (.*)?'],'direct_message,direct_mention,mention',function(bot, message) {
    var matches = message.text.match(/What\'s up with (.*)\?/i);
    if (matches === null) {
      bot.reply(message, 'Sorry, I don\'t understand you.');
    } else {
      var tag = matches[1];
      if (tag.charAt(0) === '#') {
        bot.reply(message, 'Searching for the *' + tag + '* hashtag on Twitter...');
        var tagContent = tag.substring(1);
        var exec = require('child_process').exec;
        var child = exec('java -jar Scraper.jar -hash ' + '"' + tagContent + '"', function(error, stdout, stderr) {
          //bot.reply(message, 'Done!');
          bot.reply(message, stdout);
          if (error !== null) {
            console.log('exec error: ' + error);
            bot.reply(message, 'ERROR: ' + error);
          }
        });
      } else {
        bot.reply(message, 'Sorry, "' + tag + '" is not a valid hashtag.');
      }
    }

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

controller.hears([''],'direct_message,direct_mention,mention',function(bot, message) {
    bot.reply(message,'I don\'t understand that! For a list of valid queries, type "help."');

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
