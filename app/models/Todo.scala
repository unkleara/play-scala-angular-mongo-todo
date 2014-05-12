package models

import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Todo(_id: BSONObjectID, content: String) {

  def toJson = {
    Json.obj(
    "id" -> _id.stringify,
    "content" -> content)
  }
}
object JsonFormats {

  implicit val todoFormat: Format[Todo] = (
    (JsPath \ "_id").format[BSONObjectID] and
    (JsPath \ "content").format[String]
    )(Todo.apply, unlift(Todo.unapply))
}