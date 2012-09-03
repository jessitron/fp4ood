package com.jessitron.functionalprinciples;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Guava {

    private Iterable<StateInfo> states;

    public static enum StateAbbreviation {
        MO,KS;
    }

    public static class City {
        public final String name;

        public City(String name) {
            this.name = name;
        }
    }

    public static class StateInfo {
        public final StateAbbreviation abbreviation;
        public final City capitalCity;

        public StateInfo(StateAbbreviation abbreviation, City capitalCity) {
            this.abbreviation = abbreviation;
            this.capitalCity = capitalCity;
        }

        public boolean hasGoodBbq() {
            return abbreviation == StateAbbreviation.KS;
        }
    }

    @Test
    public void uniqueIndex() {

        final Iterable<StateInfo> stateInfos = ImmutableList.of(new StateInfo(StateAbbreviation.MO, new City("Jefferson City")),
                new StateInfo(StateAbbreviation.KS, new City("Topeka")));

        final Map<StateAbbreviation,StateInfo> stateInfoByAbbreviation = Maps.uniqueIndex(stateInfos, new Function<StateInfo, StateAbbreviation>() {
            @Override
            public StateAbbreviation apply(StateInfo stateInfo) {
                return stateInfo.abbreviation;
            }
        });

        final List<StateAbbreviation> stateAbbreviations = ImmutableList.of(StateAbbreviation.KS, StateAbbreviation.MO);

        List<StateInfo> states = Lists.transform(stateAbbreviations, Functions.forMap(stateInfoByAbbreviation));
    }

    public void transform() {
        final Iterable<StateInfo> stateInfos = ImmutableList.of(new StateInfo(StateAbbreviation.MO, new City("Jefferson City")),
                new StateInfo(StateAbbreviation.KS, new City("Topeka")));

        // Iterables.transform(stateInfos, (state) -> state.capitalCity); // Java 8

        Iterables.filter(states, new Predicate<StateInfo>() {
            @Override
            public boolean apply(StateInfo stateInfo) {
                return stateInfo.hasGoodBbq();
            }
        });

    }

    private List<City> getAllCapitalCities(List<StateInfo> states) {
        List<City> output = newLinkedList();
        for (StateInfo state : states) {
            output.add(state.capitalCity);
        }
        return output;
    }

    private List<StateInfo> statesThatMatter()
    {
        List<StateInfo> output = newLinkedList();
        for (StateInfo state : states) {
            if (state.hasGoodBbq()) {
                output.add(state);
            }
        }
        return output;
    }
}
