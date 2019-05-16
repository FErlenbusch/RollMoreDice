package rollmoredice.payload;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import rollmoredice.entities.User;


@ApiModel(description = "Format of JSON object returned from the API, and to be sent to the API "
		+ "for updates of a Game")
public class GameSummary {
	private long id;
	
	private String gameName;
	
	private UserSummary dungeonMaster;
	
	private Set<UserSummary> players = new HashSet<>();
	
	private Set<CharacterSheetSummary> playerCharacters = new HashSet<>();
	
	private Set<CharacterSheetSummary> nonPlayerCharacters = new HashSet<>();
	
	private Set<ItemSummary> items = new HashSet<>();
	
	private Set<SpellSummary> spells = new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public UserSummary getDungeonMaster() {
		return dungeonMaster;
	}

	public void setDungeonMaster(User dungeonMaster) {
		this.dungeonMaster = new UserSummary(dungeonMaster.getID(),
				dungeonMaster.getUsername(), dungeonMaster.getEmail());
	}

	public Set<UserSummary> getPlayers() {
		return players;
	}

	public void setPlayers(Set<UserSummary> players) {
		this.players = players;
	}

	public Set<CharacterSheetSummary> getPlayerCharacters() {
		return playerCharacters;
	}

	public void setPlayerCharacters(Set<CharacterSheetSummary> playerCharacters) {
		this.playerCharacters = playerCharacters;
	}

	public Set<CharacterSheetSummary> getNonPlayerCharacters() {
		return nonPlayerCharacters;
	}

	public void setNonPlayerCharacters(Set<CharacterSheetSummary> nonPlayerCharacters) {
		this.nonPlayerCharacters = nonPlayerCharacters;
	}

	public Set<ItemSummary> getItems() {
		return items;
	}

	public void setItems(Set<ItemSummary> items) {
		this.items = items;
	}

	public Set<SpellSummary> getSpells() {
		return spells;
	}

	public void setSpells(Set<SpellSummary> spells) {
		this.spells = spells;
	}
	
	
}
