resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.4" % "test"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.10.4"
)

logBuffered in Test := false

scalaVersion := "2.10.4"

parallelExecution in test := false

testGrouping <<= definedTests in Test map { tests =>
  tests.map { test =>
    import Tests._
    new Group(
      name = test.name,
      tests = Seq(test),
      runPolicy = InProcess)
  }.sortWith(_.name < _.name)
}
