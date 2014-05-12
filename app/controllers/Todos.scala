package controllers

import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models._
import models.JsonFormats._
import play.api.libs.json._
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import scala.concurrent.Future

// Reactive Mongo imports
import reactivemongo.api._

// Reactive Mongo plugin, including the JSON-specialized collection
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection

object Todos extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("todos")

  def index = Action.async {
    // start query
    val cursor: Cursor[Todo] = collection.find(Json.obj()).cursor[Todo]

    // gather all the JsObjects in a list
    val futureTodoList: Future[List[Todo]] = cursor.collect[List]()

    // transform the todos list into a JsArray
    val futureTodosJsonArray: Future[JsArray] = futureTodoList.map { todos =>
      Json.arr(todos)
    }

    // reply with array
    futureTodosJsonArray.map { todos =>
      Ok(todos(0))
    }
  }

  case class TodoForm(content: String) {
    def toTodo: Todo = Todo(BSONObjectID.generate, content)
  }

  implicit val todoFormFormat = Json.format[TodoForm]


  def create = Action.async(BodyParsers.parse.json) { request =>
    val data = request.body.validate[TodoForm]
    data.map { todo =>
    collection.insert(todo).map(lastError =>
      Ok(Json.toJson(todo)))
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: String) = TODO //Action {}

  def delete(id: String) = Action.async(parse.empty) { request =>
      val selector = Json.obj("_id" -> Json.obj("$oid" -> id))
      collection.remove(selector).map(lastError =>
        Ok(s"Success"))

  }

//  private def saveTodo(todo: Todo) = {
//    collection.insert(todo).map(_ => Created)
//  }
}
