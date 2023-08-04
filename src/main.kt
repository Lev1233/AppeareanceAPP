fun main() {
    AppAppearance.allComands()
    AppAppearance.start()

}

object AppAppearance {
    var appExit: Boolean = true
    val oblik = OblikVitart()

    fun  start() {
        while (appExit) {

            print("> Введіть свою команду: ")
            println(AppInput(readLine()).processCommand())

        }
    }

     fun allComands() {
        println("Усі доступні команди :\n" +
                "Додати категорію --> addCategory\n"+
                "Додати витрату --> addvitrata\n"+
                "Видалити витрату --> deleteVitrata\n"+
                 "Показати витрату  --> displayVitrataByID\n"+
                "Змінити витрату --> changeVitrata\n"+
                "Показати всі витрати за категорією --> AllVitrataByCategory\n"+
                "Відобразити гістрограмму --> gistogramma\n"+
                "Вихід --> exit")
    }

    class AppInput(arg: String?) {
        private val input = arg ?: ""

        fun processCommand() = when (input) {
            "addCategory" -> addCategory()
            "addvitrata" -> addVitrata()
            "deleteVitrata"->deleteVitrata()
            "displayVitrataByID"->displayVitrata()
            "changeVitrata"-> changeVitrata()
            "allVitrataByCategory"->displayAllVitrataByCategory()
            "gistogramma"->showGistopgram()
            
            "exit" -> exitApp()
            else -> commandNotFound()
        }
        private fun commandNotFound() = "немає такої команди!"
    }

    private fun showGistopgram()  {
        println("Gistogramm :")
    }


    private fun displayVitrata() {
        println("Введіть id витрати яку хочети подивитися: ")
        val inputID = readLine()?.toIntOrNull()

        if (inputID != null) {
            val obl =  oblik.getVitrataById(inputID)
            if (obl != null) {
                println("ID: ${obl.id}, Назва: ${obl.name}, Сума: ${obl.costs}, Категорія: ${obl.category.name}")
            }else{
                println("немає такого ID")
            }
        }
    }


    private fun addVitrata() {
        println("Додати витрату уведіть ID :")
        val inputIDVitrata = readLine()?.toIntOrNull()

        println("Додати витрату уведіть ім'я :")
        val inputNameVitrata = readLine()?:""

        println("Додати витрату уведіть ціну :")
        val inputCostVitrata = readLine()

        val vitrataCost: Number = if (inputCostVitrata != null) {
            if (inputCostVitrata.contains(".")) {
                inputCostVitrata.toDoubleOrNull() ?: 0.0
            } else {
                inputCostVitrata.toIntOrNull() ?: 0
            }
        } else {
            0
        }

        println("Оберіть ID потрібноі категоріі :")
        oblik.categories.forEach { category ->
            println("ID: ${category.id}, Назва: ${category.name}")
        }

        val selectedCategoryId = readLine()?.toIntOrNull() ?: -1
        val selectedCategory = oblik.categories.find { it.id == selectedCategoryId }

        if (selectedCategory==null){
            println("Спочатку додайте категорію :")
              addCategory()
        }


        selectedCategory?.let {
            if (inputCostVitrata != null) {
                if (inputIDVitrata != null) {
                 oblik.addVitrata(Vitrata(inputIDVitrata,inputNameVitrata,vitrataCost, it))
                }
            }
        }
    }



    private fun addCategory() {
        println("Додати категорію уведіть ID :")
        val inputIDcategory = readLine()?.toIntOrNull()

        println("Додати категорію уведіть ім'я :")
        val inputNameCategory = readLine()?:""
        val category = inputIDcategory?.let { Category(it, inputNameCategory) }
        category?.let { oblik.addCategory(it) }
    }

    private fun exitApp(){
        appExit = false
    }

    private fun displayAllVitrataByCategory() {

        oblik.categories.forEach { categories ->
            println("ID: ${categories.id}, Назва: ${categories.name}")
        }
        println("Введіть категорію :")
        val inputCategory = readLine()
        val vitrataCategory = oblik.vitrata.filter { it.category.name == inputCategory }

        if (vitrataCategory.isNotEmpty()) {
            for (vitrata in vitrataCategory) {
                println("ID: ${vitrata.id}, Сума: ${vitrata.costs}, Ім'я: ${vitrata.name}")
            }
        }else{
            println("Немає такоі категорії")
        }


    }

    private fun changeVitrata() {
        println("Введіть id витрати, яку хочете змінити:")
        val inputID = readLine()?.toIntOrNull()

        if (inputID?.let { oblik.getVitrataById(it) } !=null){
          val newVitrata = oblik.getVitrataById(inputID)
            println("Введіть нову назву витрати:")
            val updatedName = readLine() ?: newVitrata?.name

            println("Введіть нову суму витрати:")
            val newVitrataSum= readLine()
            val newVitrataCost: Number = if (newVitrataSum != null) {
                if (newVitrataSum.contains(".")) {
                    newVitrataSum.toDoubleOrNull() ?: 0.0
                } else {
                    newVitrataSum.toIntOrNull() ?: 0
                }
            } else {
                0
            }
            if (newVitrataSum != null) {
                if (updatedName != null) {
                    oblik.updateVitrataById(inputID,updatedName,newVitrataCost)
                }
            }
        }else{
            println("немає такого ID")
        }
    }

    private fun deleteVitrata() {
        println("Введіть ID витрати яку потрібно видалити :")
        oblik.vitrata.forEach { vitrata ->
            println("ID: ${vitrata.id}, Назва: ${vitrata.name}")
            }
           val inputIdDelete = readLine()?.toIntOrNull()
           if (inputIdDelete != null) {
               if (oblik.getVitrataById(inputIdDelete)!=null) {
                   oblik.deleteVitarta(inputIdDelete)
               }else{
                   println("немає такого ID")
               }
        }else{
               println("Не корректний ввод")
        }
    }
}

