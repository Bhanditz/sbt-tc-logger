package jetbrains.buildServer


class TCLogAppender extends LogAppender {

  def log(level: sbt.Level.Value, message: => String, flowId: String) = {
    val status = discoverStatus(level)
    val escaped = jetbrains.buildServer.messages.serviceMessages.MapSerializerUtil.escapeStr(message, jetbrains.buildServer.messages.serviceMessages.MapSerializerUtil.STD_ESCAPER2)
    println(s"##teamcity[message status='$status' flowId='$flowId' text='$escaped']")

  }

  def discoverStatus(level: sbt.Level.Value): String = {
    val status = level match {
      case Level.Error => "ERROR"
      case Level.Warn => "WARNING"
      case other => "NORMAL"
    }
    status
  }

  def compilationBlockStart() {
    println(s"##teamcity[compilationStarted compiler='Scala']")
  }

  def compilationBlockEnd() {
    println(s"##teamcity[compilationFinished compiler='Scala']")
  }


  def testSuitStart(name: String, flowId: String) {
    println(s"##teamcity[testSuiteStarted name='$name' flowId='$flowId']")
  }

  def testEventOccurred(event: TestEvent, flowId: String) {
    println(s"##teamcity[test event='$event.result' flowId='$flowId']")
  }

  def testSuitSuccessfulResult(name: String, flowId: String) {
    println(s"##teamcity[testSuiteFinished name='$name' flowId='$flowId']")
  }

  def testSuitFailResult(name: String, t: Throwable, flowId: String) {
    val message = t.getMessage
    val details = t.getStackTrace
    println(s"##teamcity[testSuiteFinished name='$name' message='$message' details='$details' flowId='$flowId']")
  }
}