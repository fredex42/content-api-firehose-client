import sbtrelease.ReleaseStateTransformations._

name:= "content-api-firehose-client"
organization := "com.gu"
scalaVersion := "2.11.8"
scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-target:jvm-1.8", "-Xfatal-warnings")
scalacOptions in doc in Compile := Nil

pomExtra := (
<url>https://github.com/guardian/content-api-firehose-client</url>
  <developers>
    <developer>
      <id>LATaylor-guardian</id>
      <name>Luke Taylor</name>
      <url>https://github.com/LATaylor-guardian</url>
    </developer>
  </developers>
)
publishArtifact in Test := false
releasePublishArtifactsAction := PgpKeys.publishSigned.value
organization := "com.gu"
licenses := Seq("Apache v2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/content-api-firehose-client"),
  "scm:git:git@github.com:guardian/content-api-firehose-client.git"
))

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

resolvers += "Guardian GitHub Repository" at "http://guardian.github.io/maven/repo-releases"

libraryDependencies ++= Seq(
  "com.gu" % "content-api-models-scala" % "11.1",
  "com.amazonaws" % "amazon-kinesis-client" % "1.6.3",
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.10.77",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "com.twitter" %% "scrooge-core" % "4.5.0")

initialize := {
  val _ = initialize.value
  assert(sys.props("java.specification.version") == "1.8",
    "Java 8 is required for this project.")
}