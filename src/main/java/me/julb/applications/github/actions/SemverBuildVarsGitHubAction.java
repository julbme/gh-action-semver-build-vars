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

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletionException;

import org.apache.commons.lang3.ArrayUtils;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;

import me.julb.sdk.github.actions.kit.GitHubActionsKit;
import me.julb.sdk.github.actions.spi.GitHubActionProvider;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;

/**
 * The action to compute SemVer build vars. <br>
 * @author Julb.
 */
public class SemverBuildVarsGitHubAction implements GitHubActionProvider {

    /**
     * The SNAPSHOT_SUFFIX attribute.
     */
    private static final String SNAPSHOT_SUFFIX = "SNAPSHOT";

    /**
     * The UNSTABLE_SUFFIX attribute.
     */
    private static final String UNSTABLE_SUFFIX = "unstable";

    /**
     * The GitHub action kit.
     */
    @Setter(AccessLevel.PACKAGE)
    private GitHubActionsKit ghActionsKit = GitHubActionsKit.INSTANCE;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            // Get inputs
            var packageVersion = getInputPackageVersion();

            // Trace parameters
            ghActionsKit.debug(String.format("parameters: [package_version: %s]", packageVersion));

            // Get release version
            var semverVersion = getSemverVersion(packageVersion);

            // Parse version
            var sha = ghActionsKit.getGitHubAbbreviatedSha();
            var timestamp = getCurrentTimestamp();
            var timestampAndSha = String.format("%s.%s", timestamp, sha);

            var version = semverVersion.getValue();
            var versionWithSha = semverVersion.withBuild(sha).getValue();
            var versionWithTimestamp = semverVersion.withBuild(timestamp).getValue();
            var versionWithTimestampAndSha =
                    semverVersion.withBuild(timestampAndSha).getValue();

            // Set output variables.
            // -- release version
            this.ghActionsKit.setOutput(OutputVars.ORIGINAL_VERSION.key(), packageVersion);
            this.ghActionsKit.setOutput(OutputVars.VERSION.key(), version);
            this.ghActionsKit.setOutput(OutputVars.SHA_BUILD_VERSION.key(), versionWithSha);
            this.ghActionsKit.setOutput(OutputVars.SHA_BUILD_VERSION_BUILD.key(), sha);
            this.ghActionsKit.setOutput(OutputVars.TIMESTAMP_BUILD_VERSION.key(), versionWithTimestamp);
            this.ghActionsKit.setOutput(OutputVars.TIMESTAMP_BUILD_VERSION_BUILD.key(), timestamp);
            this.ghActionsKit.setOutput(OutputVars.TIMESTAMP_SHA_BUILD_VERSION.key(), versionWithTimestampAndSha);
            this.ghActionsKit.setOutput(OutputVars.TIMESTAMP_SHA_BUILD_VERSION_BUILD.key(), timestampAndSha);
            this.ghActionsKit.setOutput(OutputVars.DOCKER_TAG.key(), version);
            this.ghActionsKit.setOutput(OutputVars.DOCKER_SHA_BUILD_TAG.key(), versionWithSha);
            this.ghActionsKit.setOutput(OutputVars.DOCKER_TIMESTAMP_BUILD_TAG.key(), versionWithTimestamp);
            this.ghActionsKit.setOutput(OutputVars.DOCKER_TIMESTAMP_SHA_BUILD_TAG.key(), versionWithTimestampAndSha);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    // ------------------------------------------ Utility methods.

    /**
     * Gets the "package_version" input.
     * @return the "package_version" input.
     */
    String getInputPackageVersion() {
        return ghActionsKit
                .getInput("package_version")
                .map(v -> v.replaceFirst("^v", ""))
                .orElseThrow();
    }

    /**
     * Gets the semver verison object from the given version. <br>
     * Replaces a <code>SNAPSHOT</code> suffix by <code>unstable</code> suffix.
     * @param version the version.
     * @return the semver object for that version.
     * @throws IllegalArgumentException if the version is not semver-valid.
     */
    Semver getSemverVersion(@NonNull String version) {
        try {
            var semverVersion = new Semver(version);
            if (ArrayUtils.isNotEmpty(semverVersion.getSuffixTokens())
                    && SNAPSHOT_SUFFIX.equals(semverVersion.getSuffixTokens()[0])) {
                semverVersion = semverVersion.withSuffix(UNSTABLE_SUFFIX);
            }
            return semverVersion;
        } catch (SemverException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Gets the current timestamp.
     * @return the current timestamp formatted.
     */
    String getCurrentTimestamp() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneId.of("UTC"))
                .format(Instant.now());
    }
}
