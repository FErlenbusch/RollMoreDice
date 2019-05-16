package rollmoredice.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Required Format of JSON object to create a new Game")
public class GameRequest {
	
	@NotBlank
	@ApiModelProperty("Required")
	private String gameName;
	
	private Set<UserSummary> players = new HashSet<>();
	
	private Set<CharacterSheetSummary> playerCharacters = new HashSet<>();
	
	private Set<CharacterSheetSummary> nonPlayerCharacters = new HashSet<>();
	
	private Set<ItemSummary> items = new HashSet<>();
	
	private Set<SpellSummary> spells = new HashSet<>();
	

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
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
