
# xRoulette 🍸

Plugin developed for a virtual roulette game 
that handles users' money by placing bets

It supports hexadecimal RGB: **&#FF0000**!

> Plugins Command 🔍!
```
/roulette
```

> Preview Roulette InGame 🔍!

![Roulette1](https://cdn.discordapp.com/attachments/1493997341554901094/1500008370264866867/aTA8V5b.gif?ex=69f6df28&is=69f58da8&hm=4933b9d9d4bc9f17880baee4cf2506fafd3fe3c9c365c3e0ae73f7698d9a4a30&)

> Preview Roulette Announce 💬!

![Roulette2](https://media.discordapp.net/attachments/1447248025771839685/1499996556303138827/image.png?ex=69f6d427&is=69f582a7&hm=5b9eb520d30ded6f5adb68fd7b2582cff2db02f1251fa7277c506ff4ba51d887&=&format=webp&quality=lossless)
# config.yml Archive ⚡
```
#
#        xRoulette - v1.0
#       Made for DevAdvvy
#
# GITHUB: https://github.com/DevAdvvy

# MULTIPLIERS FOR ANY COLOR
multipliers:
  red: 2.0
  black: 2.0
  green: 14.0

# MATERIAL FOR COLORS (Example: RED_CONCRETE, REDSTONE_BLOCK, ...)
colors:
  red: RED_STAINED_GLASS_PANE
  black: BLACK_STAINED_GLASS_PANE
  green: LIME_STAINED_GLASS_PANE

# ROULETTE BET
roulette:
  min-bet: 10000
  max-bet: 450000

  chances:
    red: 48
    black: 48
    green: 4

# WINNER ROULETTE MIN BET
Broadcast:
  Enabled: true
  MinAmount: 800000
  Message:
    - ""
    - "[center] &#FF2F2F&l¡GRAN VICTORIA EN RULETA!"
    - ""
    - "[center] &#f0f0f0El jugador &#20914C%player% &#f0f0f0ha ganado en la ruleta"
    - "[center] &#f0f0f0Llevándose &#FF2F2F$%amount%&#f0f0f0 apostando al &#FF2F2F%color%"
    - ""
```
