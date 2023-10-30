# Versions Changelog

* **X versions** are major update with lots of new content. Very rare
* **x.X versions** are either updates that add content or major bug fixes
* **x.x.X versions** are either small content update (language translation, keybind change, ...) or bug fixes

## v1.6.4: beta

* Update to 1.20.2

## v1.6.3: beta

* Biome names are fetched from minecraft lang file
* Improve event formatting with prefixes (eg. "Biome: ", "Disc: ")
* Improve category formatting with translated names
* Add japanese translation
* Fixed music name typo ("Subwoofer Lullaby")
* Fixed crash caused by badly registered music

## v1.6.2: beta

* Update to 1.20.1
* Increased text length on text fields for filtering

## v1.6.1: beta

Big thanks to [@BillStark001](https://github.com/BillStark001).

* Update to 1.19.4
* Filter music/event by identifiers (translated by default).
  Some flags can be added to enhance searching.
  * \<str1> \<str2> will search for ids having strings \<str1> or \<str2> etc.
  * /and \<str1> \<str2> will search for ids having strings \<str1> and \<str2> etc.
  * @\<str> will search for ids that contains \<str> in their namespace (useful for datapacks)
  * #\<str> will search for ids that contains \<str> in their path
  * $\<str> will search for raw ids instead of translated ids (\<raw> = \<namespace>:\<path>)
  * !\<str> will search for ids having string \<str> case-sensitive.
* Change description when no music is playing
* Added possibility to print remaining time before next music
* Fixed GUI panels warnings
* Fixed music being overridden, causing no music in the end
* Remove option panel and moved save button to config panel
* Added random music delay option
  * If Off, delay between music is fixed to your configuration X
  * If On, it is random between [X / 2 ; X]
* Added option to not fallback on default game music.
  If you want creepy biomes without music, you now can.

## v1.6: beta

* Previous music works with last played music list, meaning we can go back further
* Removed dimension category, use default by default which is minecraft sound event behavior
* Controls work with default category
* Added sound events for each overworld biomes
* Display music name when changing category
* Replaced random keybinding with previous category keybinding
* Added new custom gui, bound to 'M' key
  * Has a panel to play a specific music or event
  * Has a panel to see last played music list
  * Has a panel to configure in which sound events can be played a music/which musics can be played by a sound event
  * Has a panel to save the customized config and reload the custom resource pack
* Play "game" sound event if trying to play an empty sound event
* Display correct music name for disc events, as we can add several musics to a disc
* Custom gui dimensions are configurable

## v1.5.4

Thanks to [@naturecodevoid](https://github.com/naturecodevoid).

* Added volume increment setting
* Added setting that allows user to increment the music volume up to 200% (disabled by default)
* Improved config: added categories and tooltips
* Improved volume display by adding percent symbol to the end of the displayed text

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
