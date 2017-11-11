package my.game.server.actor

import akka.actor.Actor

import my.game.server.Server
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

import scala.collection.mutable.Set

class GameServerActor extends Actor{

	/**
	 * Called when received actor message
	 */
	def receive = {

		case Move(uuid, map, direction) => 
			Server.players.foreach{player =>
				player.map match{
					case Some(somemap) => if(somemap.equals(map)) player.actorRef ! PlayerMove(uuid, direction)
					case None => 
				}
			}

		case StandStill(uuid, map, x, y) => 
			Server.players.foreach{player =>
				player.map match{
					case Some(somemap) => if(somemap.equals(map)) player.actorRef ! PlayerStandStill(uuid, x, y)
					case None => 
				}
			}

		case ChangeMap(uuid, mapFrom, mapTo, x, y) => 
			Server.players.foreach{player =>
				if(player.uuid.equals(uuid)){
					player.map = Option(mapTo)
				} else {
					player.map match{
						case Some(somemap) => 
							if(somemap.equals(mapFrom)){
								player.actorRef ! KillPlayer(uuid)
							} else if(somemap.equals(mapTo)){
								player.actorRef ! PlayerStandStill(uuid, x, y)
							}
						case None => 
					}
				}
			}

		case Alive(uuid, map, x, y, direction, state) => 
			Server.players.foreach{player =>
				if(player.uuid.equals(uuid)){
					player.aliveFlag = true
				} else {
					player.map match{
						case Some(somemap) => if(somemap.equals(map)) player.actorRef ! Correction(uuid, x, y, direction, state)
						case None => 
					}
				}
			}

		case NotAlive(uuid, map) => 
			Server.players.foreach{player =>
				player.map match{
					case Some(somemap) => if(somemap.equals(map)) player.actorRef ! KillPlayer(uuid)
					case None => 
				}
			}
	}
}