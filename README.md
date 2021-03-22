[![Build Status](https://travis-ci.org/m-creations/maven-release-changelist-policy.svg?branch=master)](https://travis-ci.org/m-creations/maven-release-changelist-policy) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.m-creations/maven-release-changelist-policy/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.m-creations/maven-release-changelist-policy)

Maven Release Changelist Policy
---------------------------

The code in this repo was originally created by
[Keir Lawson](https://github.com/keirlawson) and demonstrated nicely how to
implement a 'yearly' release strategy for use with Maven's 
[maven-release-plugin](http://maven.apache.org/maven-release/maven-release-plugin/).
I just modified it to implement the following, much simpler strategy:

**The development version which is created after the release contains
the [Maven friendly](https://maven.apache.org/maven-ci-friendly.html)
'${changelist}' instead of '-SNAPSHOT'.**

This versioning policy for use with Maven's
[maven-release-plugin](http://maven.apache.org/maven-release/maven-release-plugin/)
specifies a versioning scheme along the lines of that currently used
by [IntelliJ](https://www.jetbrains.com/idea/download/previous.html), namely `year.major.minor`.  

## Usage

This plugin requires Java 8 along with a recent version of the release
plugin.  To use in your project edit your POM and set the
`projectVersionPolicyId` configuration property of the
maven-release-plugin to `changelist`, along with adding a dependency
to this artifact, like so:

```xml
<plugin>
  <artifactId>maven-release-plugin</artifactId>
  <version>3.0.0-M1</version>
  <configuration>
    <projectVersionPolicyId>changelist</projectVersionPolicyId>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.m-creations</groupId>
      <artifactId>maven-release-changelist-policy</artifactId>
      <version>1.0</version>
    </dependency>
  </dependencies>
</plugin>
```

And do not forget to specify a default property `changelist`:

```xml
<properties>
  <changelist>-SNAPSHOT</changelist>
</properties>
```

