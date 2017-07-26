package org.hammerlab.spark

import org.apache.spark.serializer.{ KryoRegistrator, KryoSerializer }

trait KryoConfs {
  self: SparkConfBase ⇒

  def registrationRequired: Boolean = true
  def referenceTracking: Boolean = false
  def registrar: Class[_ <: KryoRegistrator] = null

//  def registrar(clz: Class[_ <: KryoRegistrator]): Unit =
//    sparkConf(
//      "spark.kryo.registrator" → getClass.getCanonicalName
//    )

  sparkConf(
    "spark.serializer" → classOf[KryoSerializer].getCanonicalName,
    "spark.kryo.referenceTracking" → referenceTracking.toString,
    "spark.kryo.registrationRequired" → registrationRequired.toString
  )

  Option(registrar)
    .foreach(
      clz ⇒
        sparkConf(
          "spark.kryo.registrator" → clz.getCanonicalName
        )
    )
}
