package my.game.server.dictionary

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._
import my.game.pkg.entity.utils.Job._

object ServerDictionary{
	case object Connect
	case object Disconnected
	case class Quit(uuid:String)
	case class Join(uuid:String, map:String)
	case class Move(uuid:String, map:String, direction:Direction)
	case class StandStill(uuid:String, job:Job, map:String, x:Float, y:Float)
	case class ChangeMap(uuid:String, job:Job, mapFrom:String, mapTo:String, x:Float, y:Float)
	case class Alive(uuid:String, job:Job, map:String, x:Float, y:Float, direction:Direction, state:State, frameTime:Float)
	case class NotAlive(uuid:String, map:String)
	case class UpdateNPC(uuid:String, map:String, direction:Direction, x:Float, y:Float, range:Float)
}