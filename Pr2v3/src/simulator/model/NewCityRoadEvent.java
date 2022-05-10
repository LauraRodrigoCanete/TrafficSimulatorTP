package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

	public NewCityRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	Road createRoadObject() {
		return new CityRoad(id, realSrcJunc, realDestJunc, maxSpeed, co2Limit, length, weather);
	}

	@Override
	public String toString() {
		return "New CityRoad '"+ id+"'";
	}
}
