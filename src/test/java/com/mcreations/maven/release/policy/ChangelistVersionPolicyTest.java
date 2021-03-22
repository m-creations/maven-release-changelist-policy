package com.mcreations.maven.release.policy;

import org.apache.maven.shared.release.policy.version.VersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.apache.maven.shared.release.policy.version.VersionPolicyResult;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChangelistVersionPolicyTest {
    private VersionPolicy testee;
    private VersionPolicyResult result;

    private static VersionPolicyRequest version(String version) {
        return new VersionPolicyRequest().setVersion(version);
    }

    @Test
    public void shouldRemoveSnapshotWhenYearIsCurrent() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getReleaseVersion(version("1.0-SNAPSHOT"));

        assertThat(result.getVersion(), is("1.0"));
    }

    @Test
    public void shouldDefaultToFirstReleaseOfYearOnNonsenseInput() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getReleaseVersion(version("This doesn't look like a version"));

        assertThat(result.getVersion(), is("This doesn't look like a version"));
    }

    @Test
    public void shouldPreserveExtraSubVersion() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getReleaseVersion(version("1.3.2.1-SNAPSHOT"));

        assertThat(result.getVersion(), is("1.3.2.1"));
    }

    @Test
    public void shouldReturnSameVersionIfNoSnapShotPresent() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getReleaseVersion(version("1.0.3"));

        assertThat(result.getVersion(), is("1.0.3"));
    }

    @Test
    public void shouldIncrementMinorVersionAndAppendSnapshotPostfix() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getDevelopmentVersion(version("1.3"));

        assertThat(result.getVersion(), is("1.4${changelist}"));
    }

    @Test
    public void shouldIncrementFixVersionAndAppendSnapshotPostfix() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getDevelopmentVersion(version("1.3.1"));

        assertThat(result.getVersion(), is("1.3.2${changelist}"));
    }

    @Test
    public void shouldGoToNextMinorVersionWhenExtraSubVersionsPresent() throws Exception {
        testee = new ChangelistVersionPolicy();

        result = testee.getDevelopmentVersion(version("1.3.2.1"));

        assertThat(result.getVersion(), is("1.3.2.2${changelist}"));
    }
}
