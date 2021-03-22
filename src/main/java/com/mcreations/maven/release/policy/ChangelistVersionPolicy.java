package com.mcreations.maven.release.policy;

import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.release.policy.PolicyException;
import org.apache.maven.shared.release.policy.version.VersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.apache.maven.shared.release.policy.version.VersionPolicyResult;
import org.apache.maven.shared.release.versions.DefaultVersionInfo;
import org.apache.maven.shared.release.versions.VersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;
import org.codehaus.plexus.component.annotations.Component;

@Component(role = VersionPolicy.class,
           hint = "changelist",
           description = "A VersionPolicy implementation that uses '$changelist' instead of '-SNAPSHOT'")
public class ChangelistVersionPolicy implements VersionPolicy {

    private static final String snapshotPostfix = "-SNAPSHOT";
    private static final String changelistPostfix = "${changelist}";

    public ChangelistVersionPolicy() {
    }

    /**
     * {@inheritDoc}
     */
    public VersionPolicyResult getReleaseVersion(final VersionPolicyRequest versionPolicyRequest)
            throws PolicyException, VersionParseException {
        final String version = versionPolicyRequest.getVersion();
        if(version.endsWith(snapshotPostfix)) {
            return new VersionPolicyResult()
                    .setVersion(StringUtils.removeEnd(version, snapshotPostfix));
        } else {
            return new VersionPolicyResult()
                    .setVersion(StringUtils.removeEnd(version, changelistPostfix));
        }
    }

    /**
     * {@inheritDoc}
     */
    public VersionPolicyResult getDevelopmentVersion(final VersionPolicyRequest versionPolicyRequest)
            throws PolicyException, VersionParseException {
        final VersionPolicyResult result = new VersionPolicyResult();
        String version = versionPolicyRequest.getVersion();

        if (version.endsWith(snapshotPostfix)) {
            version = StringUtils.removeEnd(version, snapshotPostfix);
        } else if (version.endsWith(changelistPostfix)) {
            version = StringUtils.removeEnd(version, snapshotPostfix);
        }

        String nextVersion = new DefaultVersionInfo(version).getNextVersion().getReleaseVersionString();

        result.setVersion(nextVersion + changelistPostfix);

        return result;
    }
}
