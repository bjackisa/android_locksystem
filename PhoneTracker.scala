import scala.util. Try
import scala.collection.mutable
import scala.collection.immutable
import java.util.UUID

object PhoneTracker {
  case class Phone(name: String, location: Option[String], lost: Boolean, active: Boolean)
  val phones: mutable.Map[UUID, Phone] = mutable.Map.empty

  def addPhone(phone: Phone): UUID = {
    val uuid = UUID.randomUUID
    phones.put(uuid, phone)
    uuid
  }

  def updateLocation(uuid: UUID, location: String): Try[Unit] = Try {
    phones.get(uuid) match {
      case Some(phone) => phones.put(uuid, phone.copy(location = Some(location)))
      case None => throw new NoSuchElementException("Phone not found")
    }
  }

  def reportLost(uuid: UUID): Try[Unit] = Try {
    phones.get(uuid) match {
      case Some(phone) => 
        phones.put(uuid, phone.copy(lost = true, active = false))
      case None => throw new NoSuchElementException("Phone not found")
    }
  }

  def raiseAlarm(uuid: UUID): Try[Unit] = Try {
    phones.get(uuid) match {
      case Some(phone) => 
        phones.put(uuid, phone.copy(active = true))
      case None => throw new NoSuchElementException("Phone not found")
    }
  }

  def preventShutdown(uuid: UUID): Try[Unit] = Try {
    phones.get(uuid) match {
      case Some(phone) => 
        phones.put(uuid, phone.copy(active = false))
      case None => throw new NoSuchElementException("Phone not found")
    }
  }

  def findNearbyPhones(location: String): immutable.Seq[Phone] =
    phones.values.filter(_.location.contains(location)).toSeq

  def findLostPhone(name: String): Option[Phone] =
    phones.values.find(_.name == name && _.lost)
}
