scalaVersion := "2.13.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "5.1.0" % Test
scalacOptions ++= Seq("-deprecation", "-feature")
