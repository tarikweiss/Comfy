package me.rojetto.comfy.argument;

import me.rojetto.comfy.CommandContext;
import me.rojetto.comfy.exception.CommandArgumentException;
import me.rojetto.comfy.tree.CommandArgument;

import java.util.*;

public class BooleanArgument extends CommandArgument {
    private final Map<Boolean, String[]> booleanNames;

    public BooleanArgument(String name) {
        this(name, new String[]{"true", "on", "yes", "enable", "1"}, new String[]{"false", "off", "no", "disable", "0"});
    }

    public BooleanArgument(String name, String[] trueAliases, String[] falseAliases) {
        super(name);

        this.booleanNames = new HashMap<>();
        booleanNames.put(true, trueAliases);
        booleanNames.put(false, falseAliases);
    }

    @Override
    protected Object parse(String argument) throws CommandArgumentException {
        for (boolean key : booleanNames.keySet()) {
            for (String alias : booleanNames.get(key)) {
                if (alias.equalsIgnoreCase(argument)) {
                    return key;
                }
            }
        }

        throw new CommandArgumentException(argument + " is not a valid boolean.");
    }

    @Override
    public boolean matches(String segmentString) {
        try {
            parse(segmentString);
        } catch (CommandArgumentException e) {
            return false;
        }

        return true;
    }

    @Override
    public List<String> getSuggestions(CommandContext context) {
        List<String> suggestions = new ArrayList<>();
        suggestions.addAll(Arrays.asList(booleanNames.get(true)));
        suggestions.addAll(Arrays.asList(booleanNames.get(false)));

        return suggestions;
    }
}