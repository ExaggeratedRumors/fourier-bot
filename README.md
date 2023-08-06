# Fourier Bot

![](https://shields.io/badge/JDA-5.0-violet) ![](https://shields.io/badge/v1.0b-purple)

Discord desktop application bot based on JDA for kotlin, processing images and sending it to users including private key as watermark.

## Release

`
version 1.0b
`

## Technologies

- Kotlin 1.8.22
- JDA 5.0.0-beta.5

## Requirements

- JDK 17
- Gradle 7.4.1

## Application execution

1. Make sure your JAVA_HOME paths to jdk directory.
2. Clone repository:
```
git clone https://github.com/ExaggeratedRumors/fourier-bot
```
3. Create `config.yaml` file in directory `src/main/resources/data` and fill with content:
```yaml
client:
  client_name: "YOUR_BOT_NAME"
  client_id: "YOUR_CLIENT_ID"
  client_secret: "YOUR_CLIENT_SECRET"
  public_key: "YOUR_BOT_PUBLIC_KEY"
  token: "YOUR_BOT_TOKEN"
  permission_integer: YOUR_PERMISSIONS_INTEGER
prefix: "!"
key_length: 10
admin_id: YOUR_DISCORD_ID 
```
4. Add image `items.png` to directory `src/main/resources/images`.
5. Open application root directory and run application:
```
./gradlew run
```

## Bot activity

Basic bot activity includes `registerCommands`, `registerListener` and `build` methods.
```kotlin
    fun startActivity() {
        reloadResources()
        try {
            botBuilder.registerCommands(Resources.commandsList)
            botBuilder.registerListener()
            botBuilder.build(Resources.configuration)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
```

Listener is responsible for handling registered commands.
```kotlin
override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.author.isBot) return
        val prefix = event.message.contentRaw.substring(
            0, Resources.configuration.prefix.length
        )
        if(prefix != Resources.configuration.prefix) return
        val message = event.message.contentRaw.drop(
            Resources.configuration.prefix.length
        )
        registeredCommands.forEach { (_, command) ->
            if(message.split(" ")[0] == command.call) {
                println("ENGINE: Attempt to execute command ${command.call}")
                command.execute(event.message)
            }
        }
    }
```

## Commands

Implemented commands are placed in `CommandsContainer` object:
```kotlin
class CommandsContainer {
    companion object {
        val implementedCommands: List<Command> = listOf (
            Leak(),
            Share()
        )
    }
}
```

Registered commands and their calls are played in `commands.yaml` file:
```yaml
commands:
  "Leak":
    call: somecall
    enabled: true
  "Share":
    call: someothercall
    enabled: true
```





