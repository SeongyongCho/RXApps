package com.sycho.rxlib.sample.common

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.06
 */
class Shape {
    companion object {
        const val HEXAGON = "HEXAGON"
        const val OCTAGON = "OCTAGON"
        const val RECTANGLE = "RECTANGLE"
        const val TRIANGLE = "TRIANGLE"
        const val DIAMOND = "DIAMOND"
        const val PENTAGON = "PENTAGON"
        const val BALL = "BALL"
        const val STAR = "STAR"
        const val NO_SHAPE = "NO_SHAPE"
        const val FLIPPED = "(flipped)"

        //Colors for shape
        const val RED = "1"
        const val YELLOW = "2"
        const val GREEN = "3"
        const val SKY = "4"
        const val BLUE = "5"
        const val PUPPLE = "6"
        const val ORANGE = "7"

        @JvmStatic
        fun getColor(shape: String) : String {
            if (shape.endsWith("<>")) { // diamond
                return shape.replace("<>", "").trim()
            }
            val hyphen = shape.indexOf("-")
            if (hyphen > 0) {
                return shape.substring(0, hyphen)
            }

            return shape // for ball
        }

        @JvmStatic
        fun getSuffix(shape: String) : String {
            // NonNull로 받을거라서 그냥 처리하면 될 듯
            return when (shape) {
                HEXAGON -> "-H"
                OCTAGON -> "-O"
                RECTANGLE -> "-R"
                TRIANGLE -> "-T"
                DIAMOND -> "<>"
                PENTAGON -> "-P"
                STAR -> "-S"
                else -> ""
            }
        }

        @JvmStatic
        fun getShape(obj: String?) : String {
            // Nullable이므로 null 체크 한다.
            return if (obj != null && obj.isNotEmpty()) {
                when {
                    obj.endsWith("-H") -> HEXAGON
                    obj.endsWith("-O") -> OCTAGON
                    obj.endsWith("-R") -> RECTANGLE
                    obj.endsWith("-T") -> TRIANGLE
                    obj.endsWith("-<>") -> DIAMOND
                    obj.endsWith("-P") -> PENTAGON
                    obj.endsWith("-S") -> STAR
                    else -> "BALL"
                }
            } else {
                NO_SHAPE
            }
        }

        @JvmStatic
        fun getString(color: String, shape: String) : String {
            return color + getSuffix(shape)
        }

        @JvmStatic
        fun flip(item: String) : String {
            if (item.startsWith(FLIPPED)) {
                return item.replace(FLIPPED, "")
            }

            val shape = getShape(item)
            when (shape) {
                BALL, RECTANGLE, DIAMOND, NO_SHAPE -> throw ShapeCannotFlipException("")
            }

            return FLIPPED + item
        }

        @JvmStatic
        fun triangle(color: String) : String {
            return "$color-T"
        }

        @JvmStatic
        fun rectangle(color: String) : String {
            return "$color-R"
        }

        @JvmStatic
        fun star(color: String) : String {
            return "$color-S"
        }

        @JvmStatic
        fun pentagon(color: String) : String {
            return "$color-P"
        }
    }
}