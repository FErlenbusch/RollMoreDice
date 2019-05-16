package rollmoredice.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Required Format of JSON object to create a new Item")
public class ItemRequest {
	
	@NotBlank
	@ApiModelProperty("Required")
	private String name;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String type;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String stats;
	
	private String abilities;

	
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
