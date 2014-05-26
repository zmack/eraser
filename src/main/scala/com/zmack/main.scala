package com.zmack

import akka.actor._

import com.typesafe.config._

import javax.mail._
import javax.mail.internet._
import java.util.Properties

object Main extends App {

  override def main(args: Array[String]):Unit = {
    val system = ActorSystem("MailFetchingAsshole")
    val conf = ConfigFactory.load()
    val password = conf.getString("password")
    val email = conf.getString("email")

    val fetcher = system.actorOf(Props(new Fetcher(newStore(email, password))), name = "Fetcher")
    system.actorOf(Props(new Archivist(fetcher, newStore(email, password))))
  }


  def newStore(email:String, password:String) = {
    val props = System.getProperties()
    props.setProperty("mail.store.protocol", "imaps")

    val session = Session.getDefaultInstance(props, null)

    val store = session.getStore("imaps")

    store.connect("imap.gmail.com", email, password)
    store
  }

}

