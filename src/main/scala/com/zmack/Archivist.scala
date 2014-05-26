package com.zmack

import akka.actor._

import javax.mail._
import javax.mail.internet._
import java.util.Properties

case object RequestMessage

case class FakeMessage(message: String)
case class ArchiveMessage(message:Message)
case class MessageProcessed(subject:String, result:String)

class Archivist(fetcher: ActorRef, store:Store) extends Actor {
  val name = self.path.name

  val inbox = store.getFolder("[Gmail]/All Mail")
  val trash = store.getFolder("[Gmail]/Trash")

  val prod_error = new InternetAddress("prod_error@reverbnation.com")

  inbox.open(Folder.READ_WRITE)
  trash.open(Folder.READ_WRITE)

  fetcher ! RequestMessage

  def receive = {
    case ArchiveMessage(m) => {
      val message = inbox.getMessage(m.getMessageNumber)
      val recipients = message.getRecipients(Message.RecipientType.TO)
      println("Got a message on " + name + ": " + message.getSubject)

      if (recipients.exists(_ == prod_error)) {
        trash.appendMessages(Array(message))
      }

      fetcher ! RequestMessage
    }
    case FakeMessage(s) => {
      println("A fake message? omg: " + s);
      fetcher ! RequestMessage
    }
  }
}
