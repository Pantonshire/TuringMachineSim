head 0

=>  0   read        -> { blank -> 1, else -> done }
    1   write a     -> 2
    2   right       -> 3
    3   read        -> { blank -> 4, else -> 14 }
    4   write a     -> 5
    5   left        -> 6
    6   read        -> { blank -> 5, a -> 7, else -> error }
    7   write blank -> 8
    8   left        -> 9
    9   read        -> { blank -> 10, else -> 19 }
    10  write a     -> 11
    11  right       -> 12
    12  read        -> { blank -> 11, a -> 13, else -> error }
    13  write blank -> 2

    // Clean up marker on left hand side
    14  left        -> 15
    15  read        -> { blank -> 14, a -> 16, else -> error }
    16  write blank -> 17
    17  right       -> 18
    18  read        -> { blank -> 17, else -> done }

    // Clean up marker on right hand side
    19  right       -> 20
    20  read        -> { blank -> 19, a -> 21, else -> error }
    21  write blank -> 22
    22  left        -> 23
    23  read        -> { blank -> 22, else -> 24 }
    24  left        -> 25
    25  read        -> { blank -> 26, else -> 24 }
    26  right       -> done

    done return done
    error return error