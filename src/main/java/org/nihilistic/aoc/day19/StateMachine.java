package org.nihilistic.aoc.day19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public record StateMachine(Map<String, State> states) {
    private static final String START_TOKEN = "START";
    private record State(boolean terminal, Map<Character, String> next) {
        State finalise() {
            return new State(true, next);
        }
    };

    public String toString() {
        var out = new StringBuilder();
        for (var stateName : states.keySet()) {
            State state = states.get(stateName);
            out.append("State: " + stateName);
            for (var nextChar : state.next().keySet()) {
                var nextState = state.next().get(nextChar);
                out.append("  " + nextChar + " -> " + nextState );
                if (states.get(nextState).terminal) {
                    out.append(" [terminal]");
                }
                out.append("\n");
            }
            out.append("\n");
        }
        return out.toString();
    }

    public static StateMachine fromString(String input) {
        var startState = new State(false, new HashMap<Character, String>());
        var states = new HashMap<String, State>(Map.of(START_TOKEN, startState));
        var stateMachine = new StateMachine(states);
        stateMachine.addAllStrings(input.split(", "));
        return stateMachine;
    }

    public void addAllStrings(String strings[]) {
        for(var s : strings) {
            addString(s);
        }
    }

    public void addString(String input) {
        var current = states.get("START");
        for (var i = 0; i < input.length(); i++) {
            Character c = input.charAt(i);

            if (current.next().containsKey(c)) {
                current = states.get(current.next().get(c));
            } else {
                String stateName = input.substring(0, i + 1);
                if (!states.containsKey(stateName)) {
                    states.put(stateName, new State(false, new HashMap<Character, String>()));
                }
                current.next().put(c, stateName);
                current = states.get(stateName);
            }
        }
        states.put(input, current.finalise());
    }

    public long matches(String input) {
        var currentStates = new HashSet<String>();
        currentStates.add(START_TOKEN);
        for (var i=0; i < input.length(); i++) {
            Character c = input.charAt(i);
            var newCurrentStates = new HashSet<String>();
            for (var currentStateName : currentStates) {
                var current = states.get(currentStateName);
                if (current.next().containsKey(c)) {
                    var nextStateName = current.next().get(c);
                    if (states.get(nextStateName).terminal) {
                        newCurrentStates.add(START_TOKEN);
                    }
                    newCurrentStates.add(nextStateName);
                } 
            }
            currentStates = newCurrentStates;
        }
        // how many of our current states are terminal?
        return currentStates.stream().map(states::get).filter(State::terminal).count();
    }

}
