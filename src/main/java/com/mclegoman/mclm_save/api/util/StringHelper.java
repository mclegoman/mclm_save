/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.api.util;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.rtu.util.LogType;

import java.util.HashMap;
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
        addVariable("minecraft_version", Data.mcVersion);
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
