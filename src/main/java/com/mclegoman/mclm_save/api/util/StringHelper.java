/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.api.util;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.rtu.util.LogType;
import org.quiltmc.loader.api.ModContributor;
import org.quiltmc.loader.api.ModLicense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringHelper {
    private static final Map<String, StringVariable> variables = new HashMap<>();
    public static void addVariable(String input, String output) {
        addVariable(input, output, false);
    }
    public static void addVariable(String input, String output, boolean replace) {
        if (!variables.containsKey(input) || replace) variables.put(input, new StringVariable("[" + input + "]", output));
        else Data.version.sendToLog(LogType.WARN, input + " was already registered as a string variable!");
    }
    public static void init() {
        addVariable("save", Data.version.getName());
        addVariable("save_version", Data.version.getFriendlyString());
        if (Data.version.getModContainer().isPresent()) {
            addVariable("save_description", Data.version.getModContainer().get().metadata().description());
            StringBuilder licenses = new StringBuilder();
            for (ModLicense license : Data.version.getModContainer().get().metadata().licenses()) licenses.append((licenses.length() == 0) ? "" : ", ").append(license.id());
            addVariable("save_license", licenses.toString());
        }
        addVariable("minecraft_version", Data.mcVersion);
    }
    public static List<ModContributor> getContributors() {
        List<ModContributor> contributors = new ArrayList<>();
        if (Data.version.getModContainer().isPresent()) {
            for (ModContributor person : Data.version.getModContainer().get().metadata().contributors()) contributors.add(person);
        }
        return contributors;
    }
    public static List<String> getFormattedContributors() {
        List<String> contributors = new ArrayList<>();
        for (ModContributor person : getContributors()) {
            StringBuilder data = new StringBuilder();
            for (String role : person.roles()) data.append((data.length() == 0) ? "" : ", ").append(role);
            contributors.add(person.name() + " (" + data + ")");
        }
        return contributors;
    }
    public static String getFormattedString(String string) {
        boolean finished = false;
        while (!finished) {
            boolean replaced = false;
            for (StringVariable variable : variables.values()) {
                if (string.contains(variable.getInput())) {
                    string = string.replace(variable.getInput(), variable.getOutput());
                    replaced = true;
                }
            }
            if (!replaced) finished = true;
        }
        return string;
    }
    private static class StringVariable {
        private final String input;
        private final String output;
        private StringVariable(String input, String output) {
            this.input = input;
            this.output = output;
        }
        public String getInput() {
            return this.input;
        }
        public String getOutput() {
            return this.output;
        }
    }
}
