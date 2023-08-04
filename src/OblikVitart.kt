class OblikVitart {
     val vitrata = mutableListOf<Vitrata>()
     val categories = mutableListOf<Category>()

    fun addVitrata(expense: Vitrata) {
        vitrata.add(expense)
    }

    fun addCategory(category: Category) {
        categories.add(category)
    }

    fun deleteVitarta(id:Int){
      //  vitrata.removeAt(id)
      val del =  vitrata.find { it.id ==id }
        vitrata.remove(del)
    }

    fun getVitrataById(id: Int): Vitrata? {
        return vitrata.find { it.id == id }
    }

    fun updateVitrataById(id: Int, updatedName: String, updatedAmount: Number){
        val VitrataToUpdate = vitrata.find { it.id == id }
        if (VitrataToUpdate != null) {
            VitrataToUpdate.name = updatedName
            VitrataToUpdate.costs = updatedAmount
            println("Витрату з id $id було оновлено.")
        }

    }


}