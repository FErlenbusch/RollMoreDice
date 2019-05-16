package rollmoredice.payload;

import io.swagger.annotations.ApiModel;
import rollmoredice.entities.User;


@ApiModel(description = "Format of JSON object returned from the API, and to be sent to the API "
		+ "for updates of a Item")
public class ItemSummary {
	
	private long id;
	
	private UserSummary creator;
	
	private String name;
	
	private String type;
	
	private String stats;
	
	private String abilities;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserSummary getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = new UserSummary(creator.getID(),
				creator.getUsername(), creator.getEmail());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getAbilities() {
		return abilities;
	}

	public void setAbilities(String abilities) {
		this.abilities = abilities;
	}
}
