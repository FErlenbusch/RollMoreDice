package rollmoredice.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import rollmoredice.entities.audit.UserDateAudit;

@Entity
public class Game extends UserDateAudit {
	
	private static final long serialVersionUID = -4443790978791299683L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User dungeonMaster;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> players = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_character_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<CharacterSheet> playerCharacters = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "non_player_character_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<CharacterSheet> nonPlayerCharacters = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "spell_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "spell_id"))
    private Set<Spell> spells = new HashSet<>();
	
	@NotBlank
	private String gameName;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getDungeonMaster() {
		return dungeonMaster;
	}

	public void setDungeonMaster(User dungeonMaster) {
		this.dungeonMaster = dungeonMaster;
	}

	public Set<User> getPlayers() {
		return players;
	}

	public void setPlayers(Set<User> players) {
		this.players = players;
	}

	public Set<CharacterSheet> getPlayerCharacters() {
		return playerCharacters;
	}

	public void setPlayerCharacters(Set<CharacterSheet> playerCharacters) {
		this.playerCharacters = playerCharacters;
	}

	public Set<CharacterSheet> getNonPlayerCharacters() {
		return nonPlayerCharacters;
	}

	public void setNonPlayerCharacters(Set<CharacterSheet> nonPlayerCharacters) {
		this.nonPlayerCharacters = nonPlayerCharacters;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Spell> getSpells() {
		return spells;
	}

	public void setSpells(Set<Spell> spells) {
		this.spells = spells;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
}
