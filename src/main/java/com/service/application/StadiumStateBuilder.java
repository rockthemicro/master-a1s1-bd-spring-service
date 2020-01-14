package com.service.application;

import com.service.entity.StadiumState;

import java.util.HashMap;
import java.util.List;

public class StadiumStateBuilder {

    public static final Integer categoriesCnt = 6;

    public static final Integer rowsPerSector = 1;
    public static final Integer seatsPerRow = 1;

    public static String[] categories = {
            "1_premium",
            "1_superior",
            "1",
            "2_longside",
            "2",
            "3"
    };

    /* Camp Nou static configuration */
    private static Pair<Integer, Integer>[][] sectors =  new Pair[][] {
            new Pair[] { // 1 premium
                    new Pair<Integer, Integer>(3, 9),
                    new Pair<Integer, Integer>(103, 109),
                    new Pair<Integer, Integer>(202, 209),
                    new Pair<Integer, Integer>(302, 309),
                    new Pair<Integer, Integer>(29, 37),
                    new Pair<Integer, Integer>(129, 137),
                    new Pair<Integer, Integer>(228, 234),
                    new Pair<Integer, Integer>(332, 338)
            },
            new Pair[] { // 1 superior
                    new Pair<Integer, Integer>(1, 2),
                    new Pair<Integer, Integer>(10, 11),
                    new Pair<Integer, Integer>(101, 102),
                    new Pair<Integer, Integer>(110, 111),
                    new Pair<Integer, Integer>(200, 201),
                    new Pair<Integer, Integer>(210, 211),
                    new Pair<Integer, Integer>(300, 301),
                    new Pair<Integer, Integer>(310, 311),
                    new Pair<Integer, Integer>(24, 28),
                    new Pair<Integer, Integer>(124, 128),
                    new Pair<Integer, Integer>(225, 227),
                    new Pair<Integer, Integer>(329, 331),
                    new Pair<Integer, Integer>(38, 41),
                    new Pair<Integer, Integer>(138, 142),
                    new Pair<Integer, Integer>(235, 237),
                    new Pair<Integer, Integer>(339, 341)
            },
            new Pair[] { // 1
                    new Pair<Integer, Integer>(401, 412),
                    new Pair<Integer, Integer>(429, 441)
            },
            new Pair[] { // 2 longisde
                    new Pair<Integer, Integer>(528, 542)
            },
            new Pair[] { // 2
                    new Pair<Integer, Integer>(42, 153),
                    new Pair<Integer, Integer>(143, 152),
                    new Pair<Integer, Integer>(238, 250),
                    new Pair<Integer, Integer>(342, 358),
                    new Pair<Integer, Integer>(16, 23),
                    new Pair<Integer, Integer>(113, 123),
                    new Pair<Integer, Integer>(212, 224),
                    new Pair<Integer, Integer>(312, 328)
            },
            new Pair[] { // 3
                    new Pair<Integer, Integer>(442, 457),
                    new Pair<Integer, Integer>(543, 549),
                    new Pair<Integer, Integer>(413, 428),
                    new Pair<Integer, Integer>(521, 527)
            }
    };

    public static StadiumState getInitialStadiumState() {
        StadiumState stadiumState = new StadiumState();
        var sectorToSeats = stadiumState.getSectorToSeats();

        for (int categoryId = 0; categoryId < StadiumStateBuilder.categoriesCnt; categoryId++) {
            for (Pair<Integer, Integer> sectorRange : sectors[categoryId]) {
                for (int sector = sectorRange.getFirst(); sector <= sectorRange.getSecond(); sector++) {

                    String fullSectorName = categories[categoryId] + "." + String.valueOf(sector);
                    stadiumState.getFullSectorNames().add(fullSectorName);
                    sectorToSeats.put(fullSectorName, new HashMap<>());

                    for (int row = 1; row <= StadiumStateBuilder.rowsPerSector; row++) {
                        for (int seat = 1; seat <= StadiumStateBuilder.seatsPerRow; seat++) {

                            String seatName = String.valueOf(row) + "." + String.valueOf(seat);
                            sectorToSeats.get(fullSectorName).put(seatName, 0);
                        }
                    }
                }
            }
        }

        return stadiumState;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}