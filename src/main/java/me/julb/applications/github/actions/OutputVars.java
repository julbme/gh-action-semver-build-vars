package me.julb.applications.github.actions;

/**
 * The output variables. <br>
 * @author Julb.
 */
enum OutputVars {
    /**
     * The version.
     */
    ORIGINAL_VERSION("original_version"),

    /**
     * The version.
     */
    VERSION("version"),

    /**
     * The version with the sha as build number.
     */
    SHA_BUILD_VERSION("sha_build_version"),

    /**
     * The version with a timestamp.
     */
    TIMESTAMP_BUILD_VERSION("timestamp_build_version"),

    /**
     * The version with a timestamp and a SHAs.
     */
    TIMESTAMP_SHA_BUILD_VERSION("timestamp_sha_build_version"),

    /**
     * The SHA used to build the version number.
     */
    SHA_BUILD_VERSION_BUILD("sha_build_version_build"),

    /**
     * The timestamp used to build the version number.
     */
    TIMESTAMP_BUILD_VERSION_BUILD("timestamp_build_version_build"),

    /**
     * The timestamp/SHA used to build the version number.
     */
    TIMESTAMP_SHA_BUILD_VERSION_BUILD("timestamp_sha_build_version_build"),

    /**
     * The docker tag with the version number
     */
    DOCKER_TAG("docker_tag"),

    /**
     * The docker tag with the sha as build number.
     */
    DOCKER_SHA_BUILD_TAG("docker_sha_build_tag"),

    /**
     * The docker tag with a timestamp.
     */
    DOCKER_TIMESTAMP_BUILD_TAG("docker_timestamp_build_tag"),

    /**
     * The docker tag with a timestamp and a SHAs.
     */
    DOCKER_TIMESTAMP_SHA_BUILD_TAG("docker_timestamp_sha_build_tag");

    /**
     * The variable name.
     */
    private String key;

    /**
     * Default constructor.
     * @param key the key name.
     */
    private OutputVars(String key) {
        this.key = key;
    }

    /**
     * Getter for property key.
     * @return Value of property key.
     */
    public String key() {
        return key;
    }
}