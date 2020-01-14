package com.service.entity;

import com.service.application.StadiumStateBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StadiumState {

    /* what is the seat status (value - Map) of a certain sector (key - string) */
    private Map<String, Map<String, Integer>> sectorToSeats = new HashMap<>();

    /* at what seat (value - string) is a device (key - string) detected at */
    private Map<String, String> deviceToSeat = new HashMap<>();

    /* all the sectors names in full - something like 1_premium.103 */
    private List<String> fullSectorNames = new ArrayList<>();

    /* the contents of fullSectorNames as array */
    private String[] fullSectorNamesArray = null;

    public Map<String, Map<String, Integer>> getSectorToSeats() {
        return sectorToSeats;
    }

    public void setSectorToSeats(Map<String, Map<String, Integer>> sectorToSeats) {
        this.sectorToSeats = sectorToSeats;
    }

    public List<String> getFullSectorNames() {
        return fullSectorNames;
    }

    public void setFullSectorNames(List<String> fullSectorNames) {
        this.fullSectorNames = fullSectorNames;
    }

    public Map<String, String> getDeviceToSeat() {
        return deviceToSeat;
    }

    public void setDeviceToSeat(Map<String, String> deviceToSeat) {
        this.deviceToSeat = deviceToSeat;
    }

    public String[] getFullSectorNamesArray() {
        if (fullSectorNamesArray == null) {
            fullSectorNamesArray = new String[fullSectorNames.size() + 2];

            int i = 0;
            for (String fullSectorName : fullSectorNames) {
                fullSectorNamesArray[i++] = fullSectorName;
            }

            /* add sector information at the end of the array */
            fullSectorNamesArray[i++] = String.valueOf(StadiumStateBuilder.rowsPerSector);
            fullSectorNamesArray[i++] = String.valueOf(StadiumStateBuilder.seatsPerRow);
        }

        return fullSectorNamesArray;
    }
}
