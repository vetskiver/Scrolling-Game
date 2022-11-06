# Scrolling-Game

This project is a fan-inspired recreation of "Deku’s fight against the meta liberation army" from the anime "***My Hero Academia***." This is a Japanese superhero manga series written and illustrated by ***Kohei Horikoshi*** and the story follows protagonist Izuku Midoriya, also known as “Deku,” who is a boy born with no “Quirks,” similar to superpowers in a world where they have become commonplace, but still dreams of becoming a superhero himself. Long story short, Deku “inherits” the quirk “All for one,” which is usually transferred between generations of intrinsically hero-like individuals.
This game follows the “Meta Liberation Army” arc, upon which Tomura Shigaraki, a villain with a fearsome quirk which enables him to decay anything he touches with his fingers, plots his plan to destroy the fabric of this hero society and bring destruction. 

This game utilizes “Nomus,” in place of avoids, as subordinate enemies of Shigaraki, medkits and civilians. The aim of the game is to collect 300 “hero points” and keep health above 0. 

## Details of my custom features and game mechanics:

* When compared to the basic game, I implement numerous interesting features to the creative game. Most prominently is the projectile ability of Deku, as he is able to fire lethal projectile shots indiscriminately. However, as you play through the game, it can be soon found out that taking advantage of projectiles without regard to the contents of entities is not wise, and certainly unherolike…
* For example, if the projectile collides with a medkit, nothing changes with regards to the scores and health points. 
* If the projectile collides with a civilian, of which there are multiple randomly selected, then 20 points are deducted from the score. 
* If the projectile collides with a “nomu,” 20 points is gained. It is worth noting that I decided to make it so that the projectile “carries” on even after the first collision. For example, picture a sequence of two entities, with a nomu and civilian in front and back respectively. If a player chose to shoot, it would gain 20 points for killing a nomu but lose 20 points immediately for accidental murder of the civilian. I decided this would be appropriate as often players in action games need no regard for their surroundings, which is a far cry from reality.
* Also, I added a “cooldown” timer so players may not abuse the special ability and increase decision making. 
* Regarding “normal” player collision, here are the following changes to score/health points. If a player collides with a nomu, the player loses 1 HP and 20 points from score. 
* If a player collides with a medkit, the player gains 1 HP. 
* If a player collides, or rather “saves” a civilian, the player gains 20 hero points.  

* One thing I noticed in the basic game was the lack of energy, and so I sought to correct that by adding background music, projectile sound effects, game winning and losing clips. In my opinion, this brings the game to life and makes it more interactive. 
* To accurately reflect Shigaraki’s “quirk,” I had to implement a “death touch.” This means that if the player was to collide with Shigaraki even just once, then the game would end, as Shigaraki’s quirk allows for immediate decay. Once again, I decided to remain faithful to the anime, and personally, it makes the player more attentive as even a great start or accumulation of points could be ended by one slip up. I changed the speed of Shigaraki relative to the nomus to force interactivity and to enhance the gaming experience. 

## The specific win and lose conditions for the game:

* Health 0: 0 <= Score < 300 leads to a loss. This is typically through either 3 collisions with “nomus” or one collision with Shigaraki. 
* Health != 0, Score = 300 leads to a win. This is typically through attaining points through “saving” civilians by collecting them, killing Nomus, evading multiple Shigaraki attacks, and ensuring health never amounts to 0. 
Note that scores can go to below 0. 

## Sources for any "borrowed" assets (images, sound effects, etc)
### Images In-Game

Avoid (Nomus) = https://knowyourmeme.com/photos/948619
Deku = https://www.deviantart.com/damgames/art/Midoriya-Sprite-JUS-842725728
Tomura = https://www.deviantart.com/zacharyleebrown/art/Tomura-Shigaraki-JUS-750037417
Civilians = https://opengameart.org/content/pixel-people
Background = https://www.deviantart.com/norma2d/art/Green-space-812418009
Medkit = https://gamebanana.com/sprays/66410
Projectile = https://www.pngegg.com/en/png-bygvd

### Images Splash: 
space bar = https://icon-library.com/icon/space-bar-icon-9.html
P = https://www.shareicon.net/letter-p-washing-machine-button-wash-signs-washing-646595
Arrows = https://thenounproject.com/icon/arrow-keys-302301/
Esc = https://www.dreamstime.com/print-image175679655
Textured Background = https://toppng.com/free-image/dark-textured-background-PNG-free-PNG-Images_138318
Deku = https://www.pinterest.com/pin/274015958564183571/
Tomura = https://myheroacademia.fandom.com/wiki/Tomura_Shigaraki
Flame = https://pngset.com/download-free-png-ydixc

### Music / Sound Effects / Clips: 
Gamewon = https://tenor.com/view/deku-smash-izuku-midoriya-boku-no-hero-academia-my-hero-academia-gif-13154950
Gamewon sound clip = https://www.youtube.com/watch?v=sSfN5LSHPUI
Gamelost =  https://tenor.com/view/my-hero-academia-hero-villain-league-of-villains-tomura-shigaraki-gif-16516147
Gamelost sound clip = https://www.youtube.com/watch?v=tsB-tFCKG2Q
Background music (Threat of Offense and Defense) = https://www.youtube.com/watch?v=pjFVtO0QEnU
Energy projectile sound clip (BLASTER (multiple)) = https://pixabay.com/sound-effects/blaster-multiple-14893/

