# Teams command for admins

Admin command for the teams related stuff.

_Teamsdata will be saved in teams.yml._
_Member of a team will be saved in players.yml._

_For more infos to the teams function, check here: Teams Function._
_For the player part of the command, check here: Teams Command for players_
_To give some the admin role, check here: Admin Command_

_Admin can disable the function with the Functions Command.

---
## Command Usage

### Creating a team
`/teams create <teamname> <color> [decoration]`
`/teams create <teamname> <prefix> <suffix> <color> [decoration]`

- `<teamname>` - The name of the team.
- `<prefix>` - The prefix the team will have.
- `<suffix>` - The suffix the team will have.
- `<color>` - The color of the team.
- `[decoration]` - The decoration of the team.

### Deleting a team
`/teams delete <teamname>`

- `<teamname>` - The name of the team getting deleted.

### Renaming a team
`/teams rename <teamname> <newname>`

- `<teamname>` - The current team name.
- `<newname>` - The new name to change to.

### Recolor a team
`/teams color <teamname> <color>`

- `<teamname>` - The name of the team whose color is getting changed.
- `<color>` - The new color of the team.

### Redecorate a team
`/teams color <teamname> <decoration/remove>`

- `<teamname>` - The name of the team whose decoration is getting changed.
- `<decoration/...>` - The new decoration of the team.
- `<.../remove>` - Remove the current decoration of the team.

### Adding a player to a team
`/teams add <teamname> <player>`

- `<teamname>` - The team the player will be added.
- `<player>` - The player that is getting added.

### Removing a player from a team
`/teams remove <player>`

- `<player>` - The player, that is getting removed from the team, if has any.

### Setting prefix and suffix to a team
`/teams setprefixsuffix <teamname> <prefix> <suffix>`

- `<teamname>` - The team which gets the prefix and suffix.
- `<prefix>` - The prefix the team gets.
- `<suffix>` - The suffix the team gets.

### Removing prefix and suffix from a team
`/teams removeprefixsuffix <teamname>`

- `<teamname>` - The team which gets the prefix and suffix removed.

### Getting the list of teams
`/teams list`

### Getting the list of members of a team
`/teams list <teamname>`

- `<teamname>` - The team from whom the list of members is taken.