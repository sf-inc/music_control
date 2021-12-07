# Versions Changelog

* **X versions** are major update with lots of new content. Very rare
* **x.X versions** are either updates that add content or major bug fixes
* **x.x.X versions** are either small content update (language translation, keybind change, ...) or bug fixes

## v1.5.3

* Fixed custom category auto changing after a music was played, when not in creative or cheat disabled
* Display ALL category when enabling it

## 1.5.2

* Added last played musics queue: in order to not have the same musics following too many times
* Better display of custom subcategories

## v1.5.1

* Changed default loop button: was escape key

## v1.5

* Music display now displays the real music names. If not found, displays the same thing as before
* Different display options for music, categories and volume
  * JUKEBOX: print on action bar with changing colors, just like jukebox does
  * ACTION_BAR: print on action bar in white
  * CHAT: print on chat, only visible for you
* When not using cheat, you can change category between the one allowed and custom one
* Custom music can be put in other categories such as nether, if their name is explicit enough
* Added a DEFAULT category, that restores Minecraft music behavior, but yu still can use every command (except previous!)
* Option to choose category when running the game if possible. If you spawn in nether but chose overworld and don't have 
  cheat enabled, it will automatically change. Useful if you want to keep playing with DEFAULT or ALL for example
* New keybinds
  * Volume up and down: to change music category volume
  * Previous music: to play the previous music, only doable once!
  * Loop music: to play the same music over and over
* Every music have now the same probability to be played
* Don't play anymore the same music two following times, if possible
* Added various displays for different commands, all in lang files
* When going in category CUSTOM, now displays also the namespace associated with the sub-category

## v1.4

* Mods musics support: if you installed mods that contains new musics, you will be able to find them in the CUSTOM
  category. A new sub-category will be created for each mod containing musics so that you can switch mods musics in the
  custom category, or resourcepacks.
* The custom category is back and now shared with mods but you still can add musics via resourcepacks
* Fixed huge bug making the game not load with some mods

## v1.2.5

* Rollback some features of 1.3: no mods musics, or custom resourcepacks musics
* Keep small fixes and features from 1.3: music don't resume when changing screen, add pause messages, fix music trying
  to play when main volume off, added indonesian language support

## v1.3

* Multi-version support file: should work for every version (1.16.x at least)
* Fixed pause resuming after closing a screen (inventory, crafting table, ...)
* Fixed a bug that makes minecraft try to play music even though volume is off
* Added a custom category for people who want to add custom musics via datapacks

## v1.2
* Fix crash when joining a server: this occurred if the player didn't load a singleplayer world before joining a server

## v1.1
* Print the category and the music with a keybind
* Better config for boolean values
* Change music category even if not in creative! (this is a config)
* Automatically change category when changing dimension (if cheat not enabled or not creative)

## v1.0

* Change duration between two musics in the mod config
* Added music category system
* Skip a music of the same category
* Pause/resume the current music
* Change music category (only if player in creative)
* Print the category and the music when a music is played (can change in configs)
