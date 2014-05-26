package com.zmack

import javax.mail._
import javax.mail.internet._
import java.util.Properties

class MailClient(server:String, user:String, password:String) {

  val props = System.getProperties()
  props.setProperty("mail.store.protocol", "imaps")
  val session = Session.getDefaultInstance(props, null)
  val store = session.getStore("imaps")
  store.connect(server, user, password)

  def getEmails(folder:String) {
    try {
      // use imap.gmail.com for gmail
      val inbox = store.getFolder(folder)
      val trash = store.getFolder("[Gmail]/Trash")

      inbox.open(Folder.READ_WRITE)
      trash.open(Folder.READ_WRITE)

      for(run <- 1 to 10 ) {
        println(s"Run $run: Total Messages: ${inbox.getMessageCount}")
        
        // limit this to 20 message during testing
        val messages = inbox.getMessages(1, 1000)
        var count = 0
        println(s"Got ${messages.length} messages")
        // val deleted = inbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true)
        val moved = trash.appendMessages(messages)

        println(s"Moved ${messages.length} messags -> $moved")
        inbox.expunge()
        trash.expunge()
      }
/*
      for (message <- messages) {
        count = count + 1
        if (count > limit) return
        // println(s"$count. ${message.getSubject} -> ${message.getReceivedDate}")
        if (count % 50 == 0) {
          println(count)
        } else {
          print(".")
        }
        message.setFlag(Flags.Flag.DELETED, true)
      }
      */
      inbox.close(true)
    } catch {
      case e: NoSuchProviderException =>  e.printStackTrace()
                                          System.exit(1)
      case me: MessagingException =>      me.printStackTrace()
                                          System.exit(2)
    } finally {
      store.close()
    }
  }
}

