/*
    Release Type Utils (RTU)
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/releasetypeutils
    Licence: CC0-1.0
*/

package com.mclegoman.releasetypeutils.common.version;

import com.mclegoman.releasetypeutils.common.util.Translation;

public class Version implements Comparable<Version> {
	private final String name;
	private final String id;
	private final int major;
	private final int minor;
	private final int patch;
	private final Helper.ReleaseType type;
	private final int build;
	private final boolean dirty;
	public Version(String name, String id, int major, int minor, int patch, Helper.ReleaseType type, int build) {
		this(name, id, major, minor, patch, type, build, false);
	}
	public Version(String name, String id, int major, int minor, int patch, Helper.ReleaseType type, int build, boolean dirty) {
		this.name = name;
		this.id = id;
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.type = type;
		this.build = build;
		this.dirty = dirty;
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return id;
	}
	public int getMajor() {
		return major;
	}
	public int getMinor() {
		return minor;
	}
	public int getPatch() {
		return patch;
	}
	public Helper.ReleaseType getType() {
		return type;
	}
	public int getBuild() {
		return build;
	}
	public String getFriendlyString() {
		return String.format("%s.%s.%s-%s.%s", getMajor(), getMinor(), getPatch(), Translation.releaseTypeString(getType(), Translation.ReleaseTypeTranslationType.CODE), getBuild());
	}
	public boolean isDevelopmentBuild() {
		return !type.equals(Helper.ReleaseType.RELEASE);
	}
	public boolean isDeveloperBuild() {
		return this.dirty;
	}
	public String getLoggerPrefix() {
		return String.format("[%s %s] ", getName(), getFriendlyString());
	}
	@Override
	public int compareTo(Version other) {
		if (major != other.major) {
			return Integer.compare(major, other.major);
		} else if (minor != other.minor) {
			return Integer.compare(minor, other.minor);
		} else if (patch != other.patch) {
			return Integer.compare(patch, other.patch);
		} else if (type != other.type) {
			return type.compareTo(other.type);
		} else {
			return Integer.compare(build, other.build);
		}
	}
	public void sendToLog(Helper.LogType logType, String logMessage) {
		if (logType.equals(Helper.LogType.INFO)) System.out.println("[INFO] " + getLoggerPrefix() + logMessage);
		if (logType.equals(Helper.LogType.WARN)) System.out.println("[WARN] " + getLoggerPrefix() + logMessage);
	}
}