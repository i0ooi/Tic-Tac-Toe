fun main() {
    val grid = MutableList(3) { ((1..3).map { ' ' }) as MutableList<Char> }
    showGrid(grid)
    move(grid, 'X')
}

fun move(grid: MutableList<MutableList<Char>>, symbol: Char) {
    var coord: MutableList<Int> = mutableListOf()
    try {
        coord = readln().split(" ").map { it.toInt() } as MutableList<Int>
    } catch (e: NumberFormatException) {
        println("You should enter numbers!")
        move(grid, symbol)
    }

    when {
        coord[0] !in 1..3 || coord[1] !in 1..3 -> {
            println("Coordinates should be from 1 to 3!")
            move(grid, symbol)
        }
        grid[coord[0] - 1][coord[1] - 1] != ' ' -> {
            println("This cell is occupied! Choose another one! ")
            move(grid, symbol)
        }
        else -> {
            grid[coord[0] - 1][coord[1] - 1] = symbol
            showGrid(grid)
            checker(grid, symbol)
        }
    }
}

fun showGrid(grid: MutableList<MutableList<Char>>) {
    println("---------")
    grid.forEach { println("| ${it[0]} ${it[1]} ${it[2]} |") }
    println("---------")
}

fun checker(grid: MutableList<MutableList<Char>>, symbol: Char) {
    var winState = false
    var emptyState = false

    val checks = listOf(0, 4, 8, 2, 4, 6, 0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 3, 6, 1, 4, 7, 2, 5, 8).chunked(3)
    val checkList = mapOf(
        0 to grid[0][0], 1 to grid[0][1], 2 to grid[0][2],
        3 to grid[1][0], 4 to grid[1][1], 5 to grid[1][2],
        6 to grid[2][0], 7 to grid[2][1], 8 to grid[2][2],
    )

    loop@ for (i in checks) {
        var line = ""
        for (x in i) line += checkList[x]
        if (line == "$symbol$symbol$symbol") {
                winState = true
                break@loop
            }
    }

    for (i in grid) {
        if (' ' in i) {
            emptyState = true
            break
        }
    }

    when {
        emptyState && !winState -> move(grid, if (symbol == 'X') 'O' else 'X')
        winState -> println("$symbol wins")
        else ->  println("Draw")
    }
}