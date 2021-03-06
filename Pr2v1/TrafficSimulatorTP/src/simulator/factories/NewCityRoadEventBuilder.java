package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	@Override
	Event createTheRoad() {
		return new NewCityRoadEvent(time, id, j1, j2, length, co2limit, maxspeed, w);
	}
}
