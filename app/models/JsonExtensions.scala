package models

import reactivemongo.bson.BSONObjectID
object JsonExtensions {

  import play.api.libs.json._

  def withDefault[A](key: String, default: A)(implicit writes: Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))
  def copyKey(fromPath: JsPath,toPath:JsPath ) = __.json.update(toPath.json.copyFrom(fromPath.json.pick))
  def copyOptKey(fromPath: JsPath,toPath:JsPath ) = __.json.update(toPath.json.copyFrom(fromPath.json.pick orElse Reads.pure(JsNull)))
  def moveKey(fromPath:JsPath, toPath:JsPath) =(json:JsValue)=> json.transform(copyKey(fromPath,toPath) andThen fromPath.json.prune).get
}