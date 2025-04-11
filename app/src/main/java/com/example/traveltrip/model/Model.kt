package com.example.traveltrip.model

class Model private constructor() {
    val travels: MutableList<Travel> = ArrayList()

    companion object {
        val instance: Model = Model()
    }

    init {
        for (i in 0..20)
            travels.add(
                Travel(
                    "$i",
                    "the trip is $i",
                    "/"
                )
            )
    }


}