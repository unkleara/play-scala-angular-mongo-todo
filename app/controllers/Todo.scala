package controllers

import play.api._
import play.api.mvc._

object Todo extends Controller {

  def index = Action {
    Ok(s"This is the id route")
  }

  def todo(id: Long) = Action {
    Ok(s"This is the id route: $id")
  }

  def create = TODO //Action {}

  def update(id: Long) = TODO //Action {}

  def delete(id: Long) = TODO //Action {}
}
