package com.service.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StadiumState {
    /* how many devices (value - integer) are detected at a certain seat (key - string) */
    private Map<String, Integer> seatToDevices = new HashMap<>();

    /* at what seat (value - string) is a device (key - string) detected at */
    private Map<String, String> deviceToSeat = new HashMap<>();

    /* all the sectors names in full - something like 1_premium.103 */
    private List<String> fullSectorNames = new ArrayList<>();

    public List<String> getFullSectorNames() {
        return fullSectorNames;
    }

    public void setFullSectorNames(List<String> fullSectorNames) {
        this.fullSectorNames = fullSectorNames;
    }

    public Map<String, Integer> getSeatToDevices() {
        return seatToDevices;
    }

    public void setSeatToDevices(Map<String, Integer> seatToDevices) {
        this.seatToDevices = seatToDevices;
    }

    public Map<String, String> getDeviceToSeat() {
        return deviceToSeat;
    }

    public void setDeviceToSeat(Map<String, String> deviceToSeat) {
        this.deviceToSeat = deviceToSeat;
    }

}
