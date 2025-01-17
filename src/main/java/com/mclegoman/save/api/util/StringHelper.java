/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.util;

import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.rtu.util.LogType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModContributor;
import org.quiltmc.loader.api.ModLicense;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.*;

public class StringHelper {
    private static final Map<String, StringVariable> variables = new HashMap<>();
    public static void addVariable(String namespace, String input, String output) {
        addVariable(namespace + "_" + input, output, false);
    }
    public static void addVariable(String namespace, String input, String output, boolean replace) {
        addVariable(namespace + "_" + input, output, replace);
    }
    private static void addVariable(String input, String output) {
        addVariable(input, output, false);
    }
    private static void addVariable(String input, String output, boolean replace) {
        if (!variables.containsKey(input) || replace) variables.put(input, new StringVariable("[" + input + "]", output));
        else Data.getVersion().sendToLog(LogType.WARN, input + " was already registered as a string variable!");
    }
    public static void init() {
        addQuiltMods();
        // We need to replace the [minecraft_version] otherwise it becomes UNKNOWN.minecraft-client...
        addVariable("minecraft", "version", Data.mcVersion, true);
    }
    private static void addQuiltMods() {
        for (ModContainer modContainer : QuiltLoader.getAllMods()) addModVariables(modContainer.metadata().id());
    }
    public static void addModVariables(String modId) {
        Optional<ModContainer> modContainer = QuiltLoader.getModContainer(modId);
        if (modContainer.isPresent()) {
            // [modId]
            addVariable(modId, modContainer.get().metadata().name());
            // [modId]
            addVariable(modId, "id", modContainer.get().metadata().id());
            // [modId_version]
            addVariable(modId, "version", modContainer.get().metadata().version().raw());
            // [modId_description]
            addVariable(modId, "description", modContainer.get().metadata().description());
            StringBuilder licenses = new StringBuilder();
            for (ModLicense license : modContainer.get().metadata().licenses()) licenses.append((licenses.length() == 0) ? "" : ", ").append(license.id());
            // [modId_licenses]
            addVariable(modId, "licenses", licenses.toString());
        }
    }
    public static List<ModContributor> getContributors(ModContainer modContainer) {
	    return new ArrayList<>(modContainer.metadata().contributors());
    }
    public static List<String> getFormattedContributors() {
        Optional<ModContainer> modContainer = Data.getVersion().getModContainer();
        return modContainer.map(StringHelper::getFormattedContributors).orElseGet(ArrayList::new);
    }
    public static List<String> getFormattedContributors(ModContainer modContainer) {
        List<String> contributors = new ArrayList<>();
        for (ModContributor person : getContributors(modContainer)) {
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
