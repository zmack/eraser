package com.zmack

import akka.actor._

import javax.mail._
import javax.mail.internet._
import java.util.Properties

class Fetcher(store:Store) extends Actor {
  val inbox = store.getFolder("[Gmail]/All Mail")
  inbox.open(Folder.READ_ONLY)

  private var messageBuffer:List[Message] = List()

  def receive = {
    case RequestMessage => {
      println("Got a message request")
      sender ! ArchiveMessage(getMessage)
    }
  }

  private def getMessage = {
    if (messageBuffer.isEmpty) {
      messageBuffer = inbox.getMessages(1, 2000).toList
    }

    val message :: bufferTail = messageBuffer
    messageBuffer = bufferTail

    message
  }
}
