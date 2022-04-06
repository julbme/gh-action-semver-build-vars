/**
 * MIT License
 *
 * Copyright (c) 2017-2022 Julb
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
    OutputVars(String key) {
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
