package com.tibod.anorm

import anorm.{RowParser, SqlParser, Column}

/**
 * Created by tibo on 20/04/15.
 */
class metamodel {

}

trait Parseable[A] {
  def parser: RowParser[A]
}

trait Field {
  def name:String
  def columnAlias:Option[String]
  def tableAlias:Option[String]

  override def toString =  tableAlias.map(_+".").getOrElse("") +  columnAlias.getOrElse(name)

}

trait ParseableField[A] extends Field with Parseable[A]

case class SimpleColumn[A] (
                     name:String,
                     columnAlias:Option[String] = None,
                     tableAlias:Option[String] = None,
                     extractor:  Column[A]) extends Field with Parseable[A] {
  override def parser = SqlParser.get[A](toString)(extractor)

}


trait Table[T] extends Parseable[T]{
  def name : String
  def alias:Option[String] = None
  def parser: RowParser[T]


  val * : ParseableField[T] = new ParseableField[T] {
    override val parser: RowParser[T] = Table.this.parser

    override val columnAlias: Option[String] = None

    override val name: String = "*"

    override val tableAlias: Option[String] = alias.orElse(Some(name))
  }

  def get[A](fieldName: String)(implicit extractor: Column[A] ) = new SimpleColumn[A](fieldName, None, alias, extractor)


}