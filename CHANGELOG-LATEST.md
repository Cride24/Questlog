### Added

- Added inline quest/chapter translations, searchable in-game language selection, per-field US-English/authored-order fallback and legacy None mode.
- Added a personal quest tracking overlay with server-side order and local window geometry, passive during gameplay and interactive in Minecraft chat.
- Added author validation for required fields, registered types, values and available references, with clickable in-game reports and required-field markers.
- Added explicit draft saving and activation, plus isolated functional and intentionally malformed test examples.

### Changed

- Editing deactivates a quest on the server until the author explicitly reactivates it. Incomplete drafts retain saved progression; unchanged entries recover their state after reordering.
- Disabled quests stay followed in grey. Completed quests move after incomplete quests and leave tracking once all rewards are collected, or immediately when no rewards exist.
- Updated the NeoForge network protocol to 2.2 for inline-language definitions; clients and servers must use matching versions.

### Fixed

- Opened quest/chapter editors in the player language for new definitions, or the sole authored language then player/US-English/JSON-order priority for existing definitions; language buttons now show Minecraft language names and regions.
- Made the whole brown tracking frame capture resizing, added the journal heading separator and previewed quest reordering continuously while dragging.
- Prefilled new quest ordering after the highest order in the selected chapter, including inactive drafts and legacy ordering values.
- Prefilled new quests with the chapter currently selected in the journal, including their automatic identity preview.
- Restyled the Follow button and tracking overlay using Questlog textures, with configurable background/frame opacity.
- Fixed dragging in chat by checking the physical mouse button instead of the gameplay-only MouseHandler state.
- Hid inactive/nonfunctional followed quests outside author mode while preserving their personal order.
- Moved required markers after field labels and removed optional markers; new titles and required descriptions start empty.
- Added automatic read-only quest identities allocated by the server on first save and retained on subsequent edits.
- Made draft validation silent and returned failed activation reports to the same editor; removed the analysis buttons.
- Prevented inactive quests from accepting normal progress, reading, resets and reward collection.
- Kept malformed ordering/title values and absent quest references accessible to validation.
