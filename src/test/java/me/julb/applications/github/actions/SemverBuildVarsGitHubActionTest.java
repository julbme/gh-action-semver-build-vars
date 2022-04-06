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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.julb.sdk.github.actions.kit.GitHubActionsKit;

/**
 * Test class for {@link SemverBuildVarsGitHubAction} class. <br>
 * @author Julb.
 */
@ExtendWith(MockitoExtension.class)
class SemverBuildVarsGitHubActionTest {

    /**
     * The class under test.
     */
    private SemverBuildVarsGitHubAction githubAction = null;

    /**
     * A mock for GitHub action kit.
     */
    @Mock
    private GitHubActionsKit ghActionsKitMock;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        githubAction = new SemverBuildVarsGitHubAction();
        githubAction.setGhActionsKit(ghActionsKitMock);
    }

    /**
     * Test method.
     */
    @Test
    void whenGetInputPackageVersion_thenReturnValue() throws Exception {
        when(this.ghActionsKitMock.getInput("package_version")).thenReturn(Optional.of("1.0.0"));

        assertThat(this.githubAction.getInputPackageVersion()).contains("1.0.0");

        verify(this.ghActionsKitMock).getInput("package_version");
    }

    /**
     * Test method.
     */
    @Test
    void whenGetInputPackageVersionWithPrefix_thenReturnValue() throws Exception {
        when(this.ghActionsKitMock.getInput("package_version")).thenReturn(Optional.of("v1.0.0"));

        assertThat(this.githubAction.getInputPackageVersion()).contains("1.0.0");

        verify(this.ghActionsKitMock).getInput("package_version");
    }

    /**
     * Test method.
     */
    @Test
    void whenGetInputPackageVersionNotProvided_thenThrowNoSuchElementException() {
        when(this.ghActionsKitMock.getInput("package_version")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> this.githubAction.getInputPackageVersion());

        verify(this.ghActionsKitMock).getInput("package_version");
    }

    /**
     * Test method.
     */
    @Test
    void whenGetSemverVersion_thenReturnValidVersion() throws Exception {
        var semver1 = this.githubAction.getSemverVersion("1.0.3-rc1+abcde1234");
        assertThat(semver1.getMajor()).isEqualTo(1);
        assertThat(semver1.getMinor()).isZero();
        assertThat(semver1.getPatch()).isEqualTo(3);
        assertThat(semver1.getSuffixTokens()).contains("rc1");
        assertThat(semver1.getBuild()).contains("abcde1234");

        var semver2 = this.githubAction.getSemverVersion("1.0.3-rc1+abcde1234");
        assertThat(semver2.getMajor()).isEqualTo(1);
        assertThat(semver2.getMinor()).isZero();
        assertThat(semver2.getPatch()).isEqualTo(3);
        assertThat(semver2.getSuffixTokens()).contains("rc1");
        assertThat(semver2.getBuild()).contains("abcde1234");

        var semver3 = this.githubAction.getSemverVersion("1.0.3-SNAPSHOT");
        assertThat(semver3.getMajor()).isEqualTo(1);
        assertThat(semver3.getMinor()).isZero();
        assertThat(semver3.getPatch()).isEqualTo(3);
        assertThat(semver3.getSuffixTokens()).contains("unstable");
        assertThat(semver3.getBuild()).isNull();

        var semver4 = this.githubAction.getSemverVersion("1.0.3");
        assertThat(semver4.getMajor()).isEqualTo(1);
        assertThat(semver4.getMinor()).isZero();
        assertThat(semver4.getPatch()).isEqualTo(3);
        assertThat(semver4.getSuffixTokens()).isEmpty();
        assertThat(semver4.getBuild()).isNull();
    }

    /**
     * Test method.
     */
    @Test
    void whenGetSemverVersionInvalid_thenThrowIllegalArgumentException() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> this.githubAction.getSemverVersion("abcd"));
    }

    /**
     * Test method.
     */
    @Test
    void whenGetSemverVersionNull_thenThrowNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> this.githubAction.getSemverVersion(null));
    }

    /**
     * Test method.
     */
    @Test
    void whenGetCurrentTimestamp_thenReturnValue() throws Exception {
        assertThat(this.githubAction.getCurrentTimestamp()).matches("^[0-9]{14}$");
    }

    /**
     * Test method.
     */
    @Test
    void whenExecute_thenReturnValidValues() throws Exception {
        var spy = spy(this.githubAction);

        when(this.ghActionsKitMock.getGitHubAbbreviatedSha()).thenReturn("abcdef");
        doReturn("1.1.0-SNAPSHOT").when(spy).getInputPackageVersion();
        doReturn("20210101001122").when(spy).getCurrentTimestamp();

        spy.execute();

        verify(this.ghActionsKitMock).getGitHubAbbreviatedSha();
        verify(spy).getInputPackageVersion();
        verify(spy).getCurrentTimestamp();

        verify(this.ghActionsKitMock).setOutput(OutputVars.ORIGINAL_VERSION.key(), "1.1.0-SNAPSHOT");
        verify(this.ghActionsKitMock).setOutput(OutputVars.VERSION.key(), "1.1.0-unstable");
        verify(this.ghActionsKitMock).setOutput(OutputVars.SHA_BUILD_VERSION.key(), "1.1.0-unstable+abcdef");
        verify(this.ghActionsKitMock).setOutput(OutputVars.SHA_BUILD_VERSION_BUILD.key(), "abcdef");
        verify(this.ghActionsKitMock)
                .setOutput(OutputVars.TIMESTAMP_BUILD_VERSION.key(), "1.1.0-unstable+20210101001122");
        verify(this.ghActionsKitMock).setOutput(OutputVars.TIMESTAMP_BUILD_VERSION_BUILD.key(), "20210101001122");
        verify(this.ghActionsKitMock)
                .setOutput(OutputVars.TIMESTAMP_SHA_BUILD_VERSION.key(), "1.1.0-unstable+20210101001122.abcdef");
        verify(this.ghActionsKitMock)
                .setOutput(OutputVars.TIMESTAMP_SHA_BUILD_VERSION_BUILD.key(), "20210101001122.abcdef");
        verify(this.ghActionsKitMock).setOutput(OutputVars.DOCKER_TAG.key(), "1.1.0-unstable");
        verify(this.ghActionsKitMock).setOutput(OutputVars.DOCKER_SHA_BUILD_TAG.key(), "1.1.0-unstable+abcdef");
        verify(this.ghActionsKitMock)
                .setOutput(OutputVars.DOCKER_TIMESTAMP_BUILD_TAG.key(), "1.1.0-unstable+20210101001122");
        verify(this.ghActionsKitMock)
                .setOutput(OutputVars.DOCKER_TIMESTAMP_SHA_BUILD_TAG.key(), "1.1.0-unstable+20210101001122.abcdef");
    }

    /**
     * Test method.
     */
    @Test
    void whenExecuteWithError_thenThrowCompletionException() throws Exception {
        var spy = spy(this.githubAction);

        doThrow(RuntimeException.class).when(spy).getInputPackageVersion();

        assertThrows(CompletionException.class, () -> spy.execute());

        verify(spy).getInputPackageVersion();
    }
}
