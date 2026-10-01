### Added

- Added item tooltips for inline item links in quest text and for objectives with a specific item, with a client option to disable them.
- Added optional recipe clicks for those item links and objective names when EMI or JEI is installed.
- Added optional `details` text on a separate quest page, with a matching field in the in-game editor.
- Added optional reward previews below objectives before quest completion. Previews are off by default and can be overridden per quest.
- Added mouse wheel navigation to the in-game editor's autocomplete lists while keeping click and keyboard selection.

### Changed

- Quest titles display on at most two lines; newly entered titles are limited to 50 characters. Objective and reward names continue to wrap within their panels.
- Moved the Collect action below the rewards panel so the quest navigation button remains available while rewards are unclaimed.
- Included the Objectives heading in the right panel's scrolling area.

### Fixed

- Ignored malformed hover-image links instead of crashing when quest text is hovered.
- Fixed independent scrolling for the Description and Details fields in the quest editor.
- Resolved translated quest descriptions and details using the current client language, including inline item links.
- Centered reward icons on the first line of their names in the right panel.
- Enforced the 50-character title limit while editing without truncating existing longer titles.
- Aligned iconless reward choices with icon-bearing choices and corrected the Choice and Experience editor button positions.
